package net.denanu.stoppablesound.events;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public abstract class StoppableSound<T> {
	private final T placer;
	private final SoundEvent event;
	private final SoundCategory category;
	private final float volume, pitch;
	private final long uuid;

	public StoppableSound(final T placer, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		this.placer = placer;
		this.event = event;
		this.category = category;
		this.volume = volume;
		this.pitch = pitch;
		this.uuid = UUIDProvider.getUUID();
	}

	public StoppableSound(final PacketByteBuf buf) {
		this.placer		= this.readPlacer(buf);
		this.event 		= buf.readRegistryValue(Registry.SOUND_EVENT);
		this.category 	= buf.readEnumConstant(SoundCategory.class);
		this.volume 	= buf.readFloat();
		this.pitch 		= buf.readFloat();
		this.uuid 		= buf.readLong();

	}

	protected abstract T readPlacer(PacketByteBuf buf);
	protected abstract void writePlacer(PacketByteBuf buf, T placer);

	public static ServerStoppablePosSound of(final ServerWorld world, final BlockPos pos, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		return new ServerStoppablePosSound(world, pos, event, category, volume, pitch);
	}

	public static ServerStoppableEntitySound of(final ServerWorld world, final Entity target, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		return new ServerStoppableEntitySound(world, target, event, category, volume, pitch);
	}

	public T getPlacer() {
		return this.placer;
	}

	public SoundEvent getEvent() {
		return this.event;
	}

	public SoundCategory getCategory() {
		return this.category;
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}

	public long getUuid() {
		return this.uuid;
	}

	public int hash() {
		return Long.hashCode(this.uuid);
	}

	public void writeToBuf(final PacketByteBuf buf) {
		this.writePlacer(buf, this.placer);
		buf.writeRegistryValue(Registry.SOUND_EVENT, this.event);
		buf.writeEnumConstant(this.category);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
		buf.writeLong(this.uuid);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof final StoppableSound<?> sound) {
			return this.uuid == sound.uuid;
		}
		if (other instanceof final Long uuid) {
			return this.uuid == uuid;
		}
		return false;
	}
}
