package net.denanu.stoppablesound.components;

import java.util.Collection;
import java.util.HashSet;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.denanu.stoppablesound.events.ClientStoppableSound;
import net.denanu.stoppablesound.events.StoppableSound;
import net.denanu.stoppablesound.utils.SoundUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class SoundComponent<T> implements AutoSyncedComponent {
	final T provider;
	HashSet<StoppableSound> activeSounds = new HashSet<>();

	SoundComponent(final T provider) {
		this.provider = provider;
	}

	public SoundComponent<T> play(final StoppableSound sound) {
		this.activeSounds.add(sound);

		this.sync(sound, true);

		return this;
	}

	abstract void sync(StoppableSound sound, boolean add);

	public void stop(final StoppableSound sound) {
		this.activeSounds.remove(sound);
	}

	@Override
	public void readFromNbt(final NbtCompound tag) {
	}

	@Override
	public void writeToNbt(final NbtCompound tag) {
	}

	@Override
	public void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player) {
		SoundComponent.writeSyncPacket(buf, player, this.activeSounds, true);
	}

	public static void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player, final Collection<StoppableSound> sounds, final boolean add) {
		buf.writeBoolean(add);
		buf.writeCollection(sounds, (buf2, sound) -> {
			sound.writeToBuf(buf2);
		});
	}

	@Override
	public void applySyncPacket(final PacketByteBuf buf) {
		final boolean add = buf.readBoolean();
		buf.readList(buf2 -> {
			final ClientStoppableSound sound = new ClientStoppableSound(buf2);

			if (add) {
				this.activeSounds.add(sound);
				SoundUtils.playSound(sound);
			}
			else {
				this.activeSounds.remove(sound);
			}

			return 1;
		});
	}
}
