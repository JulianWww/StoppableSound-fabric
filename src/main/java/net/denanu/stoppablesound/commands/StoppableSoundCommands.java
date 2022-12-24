package net.denanu.stoppablesound.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class StoppableSoundCommands {
	public static void register(final CommandDispatcher<ServerCommandSource> dispatcher, final CommandRegistryAccess access, final CommandManager.RegistrationEnvironment env) {
		PlaySoundCommand.register(dispatcher);
		StopSoundCommand.register(dispatcher);
	}
}
