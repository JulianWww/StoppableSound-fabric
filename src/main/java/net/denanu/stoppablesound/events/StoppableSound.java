package net.denanu.stoppablesound.events;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public abstract class StoppableSound {
	private final BlockPos pos;
	private final SoundEvent event;
	private final SoundCategory category;
	private final float volume, pitch;
	private final long uuid;

	public StoppableSound(final BlockPos pos, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		this.pos = pos;
		this.event = event;
		this.category = category;
		this.volume = volume;
		this.pitch = pitch;
		this.uuid = UUIDProvider.getUUID();
	}

	public StoppableSound(final PacketByteBuf buf) {
		this.pos 		= buf.readBlockPos();
		this.event 		= buf.readRegistryValue(Registry.SOUND_EVENT);
		this.category 	= buf.readEnumConstant(SoundCategory.class);
		this.volume 	= buf.readFloat();
		this.pitch 		= buf.readFloat();
		this.uuid 		= buf.readLong();

	}

	public static ServerStoppableSound of(final ServerWorld world, final BlockPos pos, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		return new ServerStoppableSound(world, pos, event, category, volume, pitch);
	}

	public int getX() {
		return this.pos.getX();
	}

	public int getY() {
		return this.pos.getY();
	}

	public int getZ() {
		return this.pos.getZ();
	}

	public BlockPos getPos() {
		return this.pos;
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
		buf.writeBlockPos(this.pos);
		buf.writeRegistryValue(Registry.SOUND_EVENT, this.event);
		buf.writeEnumConstant(this.category);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
		buf.writeLong(this.uuid);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof final StoppableSound sound) {
			return this.uuid == sound.uuid;
		}
		if (other instanceof final Long uuid) {
			return this.uuid == uuid;
		}
		return false;
	}
}
