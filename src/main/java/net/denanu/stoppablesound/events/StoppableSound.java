package net.denanu.stoppablesound.events;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public abstract class StoppableSound {
	private final int x,y,z;
	private final SoundEvent event;
	private final SoundCategory category;
	private final float volume, pitch;
	private final long uuid;

	public StoppableSound(final ServerWorld world, final int x, final int y, final int z, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.event = event;
		this.category = category;
		this.volume = volume;
		this.pitch = pitch;
		this.uuid = UUIDProvider.getUUID();
	}

	public StoppableSound(final PacketByteBuf buf) {
		this.x 			= buf.readInt();
		this.y 			= buf.readInt();
		this.z			= buf.readInt();
		this.event 		= buf.readRegistryValue(Registry.SOUND_EVENT);
		this.category 	= buf.readEnumConstant(SoundCategory.class);
		this.volume 	= buf.readFloat();
		this.pitch 		= buf.readFloat();
		this.uuid 		= buf.readLong();

	}

	public static ServerStoppableSound of(final ServerWorld world, final int x, final int y, final int z, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		return new ServerStoppableSound(world, x, y, z, event, category, volume, pitch);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
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
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
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
		return false;
	}
}
