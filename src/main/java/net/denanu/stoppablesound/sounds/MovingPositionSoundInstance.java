package net.denanu.stoppablesound.sounds;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class MovingPositionSoundInstance extends PositionedSoundInstance implements TickableSoundInstance, ITerminatable {
	protected boolean done = false;
	protected float defaultVolume;
	protected final ClientPlayerEntity player;

	public MovingPositionSoundInstance(final SoundEvent sound, final SoundCategory category, final float volume, final float pitch,
			final Random random, final double x, final double y, final double z, final ClientPlayerEntity player) {
		super(sound, category, volume, pitch, random, x, y, z);
		this.defaultVolume = volume;
		this.player = player;
	}

	public MovingPositionSoundInstance(final SoundEvent sound, final SoundCategory category, final float volume, final float pitch,
			final Random random, final BlockPos pos, final ClientPlayerEntity player) {
		super(sound, category, volume, pitch, random, pos);
		this.defaultVolume = volume;
		this.player = player;
	}

	public MovingPositionSoundInstance(final Identifier id, final SoundCategory category, final float volume, final float pitch, final Random random,
			final boolean repeat, final int repeatDelay, final AttenuationType attenuationType, final double x, final double y, final double z,
			final boolean relative, final ClientPlayerEntity player) {
		super(id, category, volume, pitch, random, repeat, repeatDelay, attenuationType, x, y, z, relative);
		this.defaultVolume = volume;
		this.player = player;
	}


	@Override
	public boolean isDone() {
		return this.done;
	}

	@Override
	public void tick() {
		this.volume = (float) Math.min(this.defaultVolume, this.defaultVolume * 16f / this.player.squaredDistanceTo(this.x, this.y, this.z));
	}

	@Override
	public void terminate() {
		this.done = true;
	}

}
