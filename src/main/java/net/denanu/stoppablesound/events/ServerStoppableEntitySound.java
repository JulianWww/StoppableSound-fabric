package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.components.EntityComponents;
import net.denanu.stoppablesound.components.EntitySoundComponent;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class ServerStoppableEntitySound extends StoppableEntitySound {
	private EntitySoundComponent chunkSound = null;

	public ServerStoppableEntitySound(final Entity entity, final SoundEvent event, final SoundCategory category, final float volume, final float pitch, final long seed) {
		super(entity, event, category, volume, pitch, seed);
	}

	public ServerStoppableEntitySound play() {
		this.chunkSound = EntityComponents.SOUNDS.get(this.getPlacer());
		this.chunkSound.play(this);
		return this;
	}

	public void stop() {
		if (this.chunkSound != null) {
			this.chunkSound.stop(this);
		}
	}

	public static void stop(final long key, final Entity entity, final ServerWorld world) {
		EntityComponents.SOUNDS.get(entity).stop(key);
	}
}
