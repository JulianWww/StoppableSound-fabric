package net.denanu.stoppablesound.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.denanu.stoppablesound.events.StoppableSound;
import net.denanu.stoppablesound.sounds.DebugSounds;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.annotation.Debug;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Debug
public class PlaySoundCommand {
	private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.playsound.failed"));

	public static void register(final CommandDispatcher<ServerCommandSource> dispatcher) {
		final RequiredArgumentBuilder<ServerCommandSource, Identifier> requiredArgumentBuilder = CommandManager.argument("sound", IdentifierArgumentType.identifier()).suggests(SuggestionProviders.AVAILABLE_SOUNDS);
		for (final SoundCategory soundCategory : SoundCategory.values()) {
			requiredArgumentBuilder.then(PlaySoundCommand.makeArgumentsForCategory(soundCategory));
		}
		dispatcher.register(CommandManager.literal("playstoppablesound").requires(source -> source.hasPermissionLevel(2)).then(requiredArgumentBuilder));
	}

	private static LiteralArgumentBuilder<ServerCommandSource> makeArgumentsForCategory(final SoundCategory category) {
		return CommandManager.literal(
				category.getName())
				.then(CommandManager.argument("targets", EntityArgumentType.players())
						.executes(context -> PlaySoundCommand
								.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, context.getSource().getPosition(), 1.0f, 1.0f, 0.0f))
						.then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
								.executes(context -> PlaySoundCommand
										.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, Vec3ArgumentType.getVec3(context, "pos"), 1.0f, 1.0f, 0.0f))
								.then(CommandManager.argument("volume", FloatArgumentType.floatArg(0.0f))
										.executes(context -> PlaySoundCommand
												.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, Vec3ArgumentType.
														getVec3(context, "pos"), context.getArgument("volume", Float.class), 1.0f, 0.0f))
										.then(CommandManager.argument("pitch", FloatArgumentType.floatArg(0.0f, 2.0f))
												.executes(context -> PlaySoundCommand
														.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, Vec3ArgumentType
																.getVec3(context, "pos"), context.getArgument("volume", Float.class), context.getArgument("pitch", Float.class), 0.0f))
												.then(CommandManager.argument("minVolume", FloatArgumentType.floatArg(0.0f, 1.0f)).executes(context -> PlaySoundCommand.
														execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, Vec3ArgumentType.
																getVec3(context, "pos"), context.getArgument("volume", Float.class), context.getArgument("pitch", Float.class), context.getArgument("minVolume", Float.class)
																)
														)
														)
												)
										)
								)
						.then(CommandManager.argument("entity", EntityArgumentType.entity())
								.executes(context -> PlaySoundCommand
										.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, EntityArgumentType.getEntity(context, "entity"), 1.0f, 1.0f, 0.0f))
								.then(CommandManager.argument("volume", FloatArgumentType.floatArg(0.0f))
										.executes(context -> PlaySoundCommand
												.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, EntityArgumentType
														.getEntity(context, "entity"), context.getArgument("volume", Float.class), 1.0f, 0.0f))
										.then(CommandManager.argument("pitch", FloatArgumentType.floatArg(0.0f, 2.0f))
												.executes(context -> PlaySoundCommand
														.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, EntityArgumentType
																.getEntity(context, "entity"), context.getArgument("volume", Float.class), context.getArgument("pitch", Float.class), 0.0f))
												.then(CommandManager.argument("minVolume", FloatArgumentType.floatArg(0.0f, 1.0f)).executes(context -> PlaySoundCommand
														.execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), IdentifierArgumentType.getIdentifier(context, "sound"), category, EntityArgumentType
																.getEntity(context, "entity"), context.getArgument("volume", Float.class), context.getArgument("pitch", Float.class), context.getArgument("minVolume", Float.class)
																)
														)
														)
												)
										)
								)
						);
	}

	private static int execute(
			final ServerCommandSource source,
			final Collection<ServerPlayerEntity> targets,
			final Identifier sound,
			final SoundCategory category,
			final Vec3d pos,
			final float volume,
			final float pitch,
			final float minVolume) throws CommandSyntaxException {

		//  StoppableSound.of(source.getWorld(), 0, 0, 0, DebugSounds.GATES_OF_GLORY_EVENT, category, 1f, 1f).playAll()

		source.getWorld().playSound(null, null, null, category, pitch, minVolume);

		source.sendFeedback(Text.literal(
				new StringBuilder().append("Stop key is: ")
				.append(StoppableSound.of(source.getWorld(), new BlockPos(pos), DebugSounds.GATES_OF_GLORY_EVENT, category, volume, pitch).play().getUuid())
				.toString()
				), false);

		return 1;
	}

	private static int execute(
			final ServerCommandSource source,
			final Collection<ServerPlayerEntity> targets,
			final Identifier sound,
			final SoundCategory category,
			final Entity entity,
			final float volume,
			final float pitch,
			final float minVolume) throws CommandSyntaxException {

		//  StoppableSound.of(source.getWorld(), 0, 0, 0, DebugSounds.GATES_OF_GLORY_EVENT, category, 1f, 1f).playAll()

		source.sendFeedback(Text.literal(
				new StringBuilder().append("Stop key is: ")
				.append(StoppableSound.of(entity, DebugSounds.GATES_OF_GLORY_EVENT, category, volume, pitch).play().getUuid())
				.toString()
				), false);

		return 1;
	}
}
