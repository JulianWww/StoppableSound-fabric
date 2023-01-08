package net.denanu.stoppablesound.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.denanu.stoppablesound.StoppableSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class EntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<EntitySoundComponent> SOUNDS = ComponentRegistry.getOrCreate(Identifier.of(StoppableSound.MOD_ID, "esounds"), EntitySoundComponent.class);

	@Override
	public void registerEntityComponentFactories(final EntityComponentFactoryRegistry registry) {
		registry.registerFor(Entity.class, EntityComponents.SOUNDS, EntitySoundComponent::new);
	}
}
