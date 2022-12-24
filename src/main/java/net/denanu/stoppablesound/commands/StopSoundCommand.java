package net.denanu.stoppablesound.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class StopSoundCommand {
	public static void register(final CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("stopstopablysound")
				.then(CommandManager.argument("key", IntegerArgumentType.integer())
						.requires(player -> player.hasPermissionLevel(4))
						.executes(StopSoundCommand::stopSound)
						)
				);
	}

	private static int stopSound(final CommandContext<ServerCommandSource> context) {
		//StoppableSound.stopAll(IntegerArgumentType.getInteger(context, "key"), context.getSource().getWorld());
		return 1;
	}
}
