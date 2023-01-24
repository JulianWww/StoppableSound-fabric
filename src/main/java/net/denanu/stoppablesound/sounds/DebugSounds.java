package net.denanu.stoppablesound.sounds;

import net.denanu.stoppablesound.Debugger;
import net.denanu.stoppablesound.StoppableSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DebugSounds {
	public static Identifier GATES_OF_GLORY_ID		= Identifier.of(StoppableSound.MOD_ID, "test");


	public static SoundEvent GATES_OF_GLORY_EVENT	= DebugSounds.debugRegister(DebugSounds.GATES_OF_GLORY_ID);

	public static SoundEvent debugRegister(final Identifier id) {
		if (Debugger.isDebug()) {
			return DebugSounds.register(id);
		}
		return null;
	}

	private static SoundEvent register(final Identifier id) {
		return DebugSounds.register(new SoundEvent(id), id);
	}

	private static SoundEvent register(final SoundEvent event, final Identifier id) {
		Registry.register(Registry.SOUND_EVENT, id, event);
		return event;
	}

	public static void setup() {}
}
