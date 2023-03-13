package net.denanu.stoppablesound.events;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class StoppableSound<T> {
	private final T placer;
	private final SoundEvent event;
	private final SoundCategory category;
	private final float volume, pitch;
	private final long uuid, seed;

	public StoppableSound(final T placer, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch, final long seed) {
		this.placer = placer;
		this.event = event.value();
		this.category = category;
		this.volume = volume;
		this.pitch = pitch;
		this.uuid = UUIDProvider.getUUID();
		this.seed = seed;
	}

	public StoppableSound(final PacketByteBuf buf, final World world) {
		this.placer 	= this.readPlacer(buf, world);
		this.event 		= buf.readRegistryValue(Registries.SOUND_EVENT);
		this.category 	= buf.readEnumConstant(SoundCategory.class);
		this.volume 	= buf.readFloat();
		this.pitch 		= buf.readFloat();
		this.uuid 		= buf.readLong();
		this.seed 		= buf.readLong();
	}

	protected abstract T readPlacer(PacketByteBuf buf, World world);
	protected abstract void writePlacer(PacketByteBuf buf, T placer);

	public static ServerStoppablePosSound of(final ServerWorld world, final BlockPos pos, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch, final long seed) {
		return new ServerStoppablePosSound(world, pos, event, category, volume, pitch, seed);
	}

	public static ServerStoppableEntitySound of(final Entity target, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch, final long seed) {
		return new ServerStoppableEntitySound(target, event, category, volume, pitch, seed);
	}

	public static ServerStoppablePosSound of(final ServerWorld world, final BlockPos pos, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch) {
		return StoppableSound.of(world, pos, event, category, volume, pitch, world.getRandom().nextLong());
	}

	public static ServerStoppableEntitySound of(final Entity target, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch) {
		return StoppableSound.of(target, event, category, volume, pitch, target.world.getRandom().nextLong());
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

	public long getSeed() {
		return this.seed;
	}

	public int hash() {
		return Long.hashCode(this.uuid);
	}

	public void writeToBuf(final PacketByteBuf buf) {
		this.writePlacer(buf, this.placer);
		buf.writeRegistryValue(Registries.SOUND_EVENT, this.event);
		buf.writeEnumConstant(this.category);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
		buf.writeLong(this.uuid);
		buf.writeLong(this.seed);
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
