package net.denanu.stoppablesound.components;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.denanu.stoppablesound.events.ClientStoppableSound;
import net.denanu.stoppablesound.events.StoppableSound;
import net.denanu.stoppablesound.utils.SoundUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class SoundComponent<T> implements AutoSyncedComponent {
	final T provider;
	HashMap<Long, StoppableSound> activeSounds = new HashMap<>();

	SoundComponent(final T provider) {
		this.provider = provider;
	}

	public SoundComponent<T> play(final StoppableSound sound) {
		this.activeSounds.put(sound.getUuid(), sound);

		this.sync(sound, true);

		return this;
	}

	abstract void sync(StoppableSound sound, boolean add);

	public void stop(final StoppableSound sound) {
		this.activeSounds.remove(sound.getUuid(), sound);
	}

	@Override
	public void readFromNbt(final NbtCompound tag) {
	}

	@Override
	public void writeToNbt(final NbtCompound tag) {
	}

	@Override
	public void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player) {
		SoundComponent.writeSyncPacket(buf, player, this.activeSounds.entrySet(), true);
	}

	public static void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player, final Set<Entry<Long, StoppableSound>> set, final boolean add) {
		buf.writeBoolean(add);
		buf.writeCollection(set, (buf2, sound) -> {
			sound.getValue().writeToBuf(buf2);
		});
	}

	@Override
	public void applySyncPacket(final PacketByteBuf buf) {
		final boolean add = buf.readBoolean();
		buf.readList(buf2 -> {
			final ClientStoppableSound sound = new ClientStoppableSound(buf2);

			if (add) {
				this.activeSounds.put(sound.getUuid(), sound);
				SoundUtils.playSound(sound);
			}
			else {
				final ClientStoppableSound csound = (ClientStoppableSound)this.activeSounds.remove(sound.getUuid());
				csound.terminate();
			}

			return 1;
		});
	}
}
