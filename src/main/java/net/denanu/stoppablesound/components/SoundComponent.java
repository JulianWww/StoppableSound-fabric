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
	protected static final byte FULL_SYNC 		= 0;
	protected static final byte MINIMAL_ADD 	= 1;
	protected static final byte MINIMAL_REMOVE	= 2;

	final T provider;
	HashMap<Long, StoppableSound> activeSounds = new HashMap<>();

	SoundComponent(final T provider) {
		this.provider = provider;
	}

	public SoundComponent<T> play(final StoppableSound sound) {
		this.activeSounds.put(sound.getUuid(), sound);

		this.sync(sound);

		return this;
	}

	public void stop(final StoppableSound sound) {
		this.activeSounds.remove(sound.getUuid(), sound);

		this.sync(sound.getUuid());
	}

	public void stop(final long key) {
		this.activeSounds.remove(key);

		this.sync(key);
	}

	abstract void sync(StoppableSound sound);
	abstract void sync(long uuid);

	@Override
	public void readFromNbt(final NbtCompound tag) {
	}

	@Override
	public void writeToNbt(final NbtCompound tag) {
	}

	@Override
	public void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player) {
		SoundComponent.writeSyncPacket(buf, player, this.activeSounds.entrySet(), SoundComponent.FULL_SYNC);
	}

	public static void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player, final Set<Entry<Long, StoppableSound>> set, final byte eventType) {
		buf.writeByte(eventType);
		buf.writeCollection(set, (buf2, sound) -> {
			sound.getValue().writeToBuf(buf2);
		});
	}

	public static void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player, final Long key) {
		buf.writeByte(SoundComponent.MINIMAL_REMOVE);
		buf.writeLong(key);
	}

	@Override
	public void applySyncPacket(final PacketByteBuf buf) {
		final byte eventType = buf.readByte();
		if (eventType == SoundComponent.FULL_SYNC) {
			this.activeSounds.clear();
		}

		// mininmal remove
		if (eventType == SoundComponent.MINIMAL_REMOVE) {
			final long uuid = buf.readLong();
			final ClientStoppableSound csound = (ClientStoppableSound)this.activeSounds.remove(uuid);
			csound.terminate();
		}
		// full sync and minimal add add new sounds
		else {
			buf.readList(buf2 -> {
				final ClientStoppableSound sound = new ClientStoppableSound(buf2);
				this.activeSounds.put(sound.getUuid(), sound);
				SoundUtils.playSound(sound);

				return 1;
			});
		}
	}
}
