package net.denanu.stoppablesound.networking.s2c.data;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class StartPlaySoundPacketS2C_Data {
	public static final float COORDINATE_SCALE = 8.0f;
	private final SoundEvent sound;
	private final SoundCategory category;
	private final int fixedX;
	private final int fixedY;
	private final int fixedZ;
	private final float volume;
	private final float pitch;
	private final long soundId;

	public StartPlaySoundPacketS2C_Data(final PacketByteBuf buf) {
		this.sound 		= buf.readRegistryValue(Registry.SOUND_EVENT);
		this.category 	= buf.readEnumConstant(SoundCategory.class);
		this.fixedX 	= buf.readInt();
		this.fixedY 	= buf.readInt();
		this.fixedZ 	= buf.readInt();
		this.volume 	= buf.readFloat();
		this.pitch  	= buf.readFloat();
		this.soundId 	= buf.readLong();
	}

	public SoundEvent getSound() {
		return this.sound;
	}

	public SoundCategory getCategory() {
		return this.category;
	}

	public int getFixedX() {
		return this.fixedX;
	}

	public int getFixedY() {
		return this.fixedY;
	}

	public int getFixedZ() {
		return this.fixedZ;
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}

	public long getSoundId() {
		return this.soundId;
	}
}
