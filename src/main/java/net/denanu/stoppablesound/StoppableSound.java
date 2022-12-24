package net.denanu.stoppablesound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.denanu.stoppablesound.commands.StoppableSoundCommands;
import net.denanu.stoppablesound.sounds.DebugSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class StoppableSound implements ModInitializer {
	public static final String MOD_ID = "stoppablesound";
	public static final Logger LOGGER = LoggerFactory.getLogger(StoppableSound.MOD_ID);

	@Override
	public void onInitialize() {
		DebugSounds.setup();

		CommandRegistrationCallback.EVENT.register(StoppableSoundCommands::register);
	}
}
