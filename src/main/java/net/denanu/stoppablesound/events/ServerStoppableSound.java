package net.denanu.stoppablesound.events;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.chunk.Chunk;

public class ServerStoppableSound extends StoppableSound {
	private final ServerWorld world;

	public ServerStoppableSound(final ServerWorld world, final int x, final int y, final int z, final SoundEvent event, final SoundCategory category,
			final float volume, final float pitch) {
		super(world, x, y, z, event, category, volume, pitch);
		this.world = world;
	}

	public ServerWorld getWorld() {
		return this.world;
	}

	public StoppableSound play() {
		final Chunk chunk = this.world.getChunk((int)this.getX(), (int)this.getY());
		return this;
	}
}
