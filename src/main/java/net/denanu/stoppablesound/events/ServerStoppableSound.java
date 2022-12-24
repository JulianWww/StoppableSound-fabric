package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.components.ChunkComponents;
import net.denanu.stoppablesound.components.ChunkSoundComponent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class ServerStoppableSound extends StoppableSound {
	private final ServerWorld world;
	private ChunkSoundComponent chunkSound = null;

	public ServerStoppableSound(final ServerWorld world, final int x, final int y, final int z, final SoundEvent event, final SoundCategory category,
			final float volume, final float pitch) {
		super(world, x, y, z, event, category, volume, pitch);
		this.world = world;
	}

	public ServerWorld getWorld() {
		return this.world;
	}

	public StoppableSound play() {
		this.chunkSound = ChunkComponents.SOUNDS.get(this.world.getChunk((int)this.getX(), (int)this.getY()));
		this.chunkSound.play(this);
		return this;
	}

	public void stop() {
		if (this.chunkSound != null) {
			this.chunkSound.stop(this);
		}
	}
}
