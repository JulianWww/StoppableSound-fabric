package net.denanu.stoppablesound.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.denanu.stoppablesound.events.ServerStoppableSound;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

public class StopSoundCommand {
	public static void register(final CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("stopstopablysound")
				.then(CommandManager.argument("key", LongArgumentType.longArg())
						.then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
								.requires(player -> player.hasPermissionLevel(4))
								.executes(StopSoundCommand::stopSound)
								)
						)
				);
	}

	private static int stopSound(final CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		final long key = LongArgumentType.getLong(context, "key");
		final BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
		ServerStoppableSound.stop(key, pos, context.getSource().getWorld());
		return 1;
	}
}
