package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.components.ChunkComponents;
import net.denanu.stoppablesound.components.ChunkSoundComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class ServerStoppablePosSound extends StoppablePosSound {
	private final ServerWorld world;
	private ChunkSoundComponent chunkSound = null;

	public ServerStoppablePosSound(final ServerWorld world, final BlockPos pos, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch, final long seed) {
		super(pos, event, category, volume, pitch, seed);
		this.world = world;
	}

	public ServerWorld getWorld() {
		return this.world;
	}

	public ServerStoppablePosSound play() {
		this.chunkSound = ChunkComponents.SOUNDS.get(this.world.getChunk(this.getPlacer()));
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
