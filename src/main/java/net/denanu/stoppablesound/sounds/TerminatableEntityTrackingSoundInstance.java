package net.denanu.stoppablesound.sounds;

import net.denanu.stoppablesound.utils.SoundUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class TerminatableEntityTrackingSoundInstance extends EntityTrackingSoundInstance implements ITerminatable {
	protected float defaultVolume;
	protected final ClientPlayerEntity player;

	public TerminatableEntityTrackingSoundInstance(final SoundEvent sound, final SoundCategory category, final float volume, final float pitch,
			final Entity entity, final long seed, final ClientPlayerEntity player) {
		super(sound, category, volume, pitch, entity, seed);
		this.player = player;
		this.defaultVolume = volume;
		this.attenuationType = SoundInstance.AttenuationType.NONE;
	}

	@Override
	public void tick() {
		super.tick();
		this.volume = SoundUtils.computeVolumeFade(this.defaultVolume, this.player, this.x, this.y, this.z);
	}

	@Override
	public void terminate() {
		this.setDone();
	}
}
