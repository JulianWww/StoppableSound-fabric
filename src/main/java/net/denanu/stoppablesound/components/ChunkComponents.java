package net.denanu.stoppablesound.components;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.denanu.stoppablesound.StoppableSound;
import net.minecraft.util.Identifier;

public class ChunkComponents implements ChunkComponentInitializer {
	public static final ComponentKey<ChunkSoundComponent> SOUNDS = ComponentRegistry.getOrCreate(Identifier.of(StoppableSound.MOD_ID, "csounds"), ChunkSoundComponent.class);

	@Override
	public void registerChunkComponentFactories(final ChunkComponentFactoryRegistry registry) {
		registry.register(ChunkComponents.SOUNDS, ChunkSoundComponent::new);
	}
}
