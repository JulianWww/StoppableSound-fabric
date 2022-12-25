package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.components.ChunkComponents;
import net.denanu.stoppablesound.components.ChunkSoundComponent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class ServerStoppableSound extends StoppableSound {
	private final ServerWorld world;
	private ChunkSoundComponent chunkSound = null;

	public ServerStoppableSound(final ServerWorld world, final BlockPos pos, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		super(pos, event, category, volume, pitch);
		this.world = world;
	}

	public ServerWorld getWorld() {
		return this.world;
	}

	public StoppableSound play() {
		this.chunkSound = ChunkComponents.SOUNDS.get(this.world.getChunk(this.getPos()));
		this.chunkSound.play(this);
		return this;
	}

	public void stop() {
		if (this.chunkSound != null) {
			this.chunkSound.stop(this);
		}
	}

	public static void stop(final long key, final BlockPos pos, final ServerWorld world) {
		ChunkComponents.SOUNDS.get(world.getChunk(pos)).stop(key);
	}
}
