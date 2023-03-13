package net.denanu.stoppablesound;

import net.denanu.stoppablesound.commands.StoppableSoundCommands;
import net.denanu.stoppablesound.sounds.DebugSounds;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Debugger {
	public static boolean isDebug() {
		return true;
	}
	public static void serverSetup() {
		if (Debugger.isDebug()) {
			DebugSounds.setup();
			CommandRegistrationCallback.EVENT.register(StoppableSoundCommands::register);
		}
	}

	public static void clientSetup() {
		if (Debugger.isDebug()) {

		}
	}
}
