package net.denanu.stoppablesound.components;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.denanu.stoppablesound.events.ClientStoppablePosSound;
import net.denanu.stoppablesound.events.StoppableSound;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class SoundComponent<T, P> implements AutoSyncedComponent {
	protected static final byte FULL_SYNC 		= 0;
	protected static final byte MINIMAL_ADD 	= 1;
	protected static final byte MINIMAL_REMOVE	= 2;

	protected final T provider;
	protected HashMap<Long, StoppableSound<P>> activeSounds = new HashMap<>();

	SoundComponent(final T provider) {
		this.provider = provider;
	}

	public SoundComponent<T, P> play(final StoppableSound<P> sound) {
		this.activeSounds.put(sound.getUuid(), sound);

		this.sync(sound);

		return this;
	}

	public void stop(final StoppableSound<P> sound) {
		this.activeSounds.remove(sound.getUuid(), sound);

		this.sync(sound.getUuid());
	}

	public void stop(final long key) {
		this.activeSounds.remove(key);

		this.sync(key);
	}

	abstract void sync(StoppableSound<P> sound);
	abstract void sync(long uuid);

	@Override
	public void readFromNbt(final NbtCompound tag) {
	}

	@Override
	public void writeToNbt(final NbtCompound tag) {
	}

	@Override
	public void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player) {
		SoundComponent.writeSyncPacket(buf, player, this.activeSounds.entrySet());
	}

	public static <P> void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity player, final Set<Entry<Long, StoppableSound<P>>> set) {
		buf.writeByte(SoundComponent.FULL_SYNC);
		buf.writeCollection(set, (buf2, sound) -> {
			sound.getValue().writeToBuf(buf2);
		});
	}

	public static <P> void writeSyncPacket(final PacketByteBuf buf, final ServerPlayerEntity p, final StoppableSound<P> sound) {
		buf.writeByte(SoundComponent.MINIMAL_ADD);
		buf.writeCollection(ImmutableList.of(sound), (buf2, sound2) -> {
			sound2.writeToBuf(buf2);
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
			final ClientStoppablePosSound csound = (ClientStoppablePosSound)this.activeSounds.remove(uuid);
			csound.terminate();
		}
		// full sync and minimal add add new sounds
		else {
			buf.readList(buf2 -> {
				this.syncAddElement(buf);

				return 1;
			});
		}
	}

	protected abstract void syncAddElement(final PacketByteBuf buf);
}
