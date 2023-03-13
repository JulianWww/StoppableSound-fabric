package net.denanu.stoppablesound.sounds;

import net.denanu.stoppablesound.Debugger;
import net.denanu.stoppablesound.StoppableSound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class DebugSounds {
	public static Identifier GATES_OF_GLORY_ID		= Identifier.of(StoppableSound.MOD_ID, "test");


	public static RegistryEntry<SoundEvent> GATES_OF_GLORY_EVENT	= DebugSounds.debugRegister(DebugSounds.GATES_OF_GLORY_ID);

	public static RegistryEntry<SoundEvent> debugRegister(final Identifier id) {
		if (Debugger.isDebug()) {
			return DebugSounds.register(id);
		}
		return null;
	}

	private static RegistryEntry<SoundEvent> register(final Identifier id) {
		return DebugSounds.register(SoundEvent.of(id), id);
	}

	private static RegistryEntry<SoundEvent> register(final SoundEvent event, final Identifier id) {
		return Registry.registerReference(Registries.SOUND_EVENT, id, event);
	}

	public static void setup() {}
}
