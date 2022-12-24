package net.denanu.stoppablesound.components;

import com.google.common.collect.ImmutableMap;

import net.denanu.stoppablesound.events.StoppableSound;
import net.minecraft.world.chunk.Chunk;

public class ChunkSoundComponent extends SoundComponent<Chunk> {

	ChunkSoundComponent(final Chunk provider) {
		super(provider);
	}

	@Override
	void sync(final StoppableSound sound, final boolean add) {
		ChunkComponents.SOUNDS.sync(this.provider, (buf, p) -> SoundComponent.writeSyncPacket(buf, p, ImmutableMap.of(sound.getUuid(), sound).entrySet(), add));
	}

}
