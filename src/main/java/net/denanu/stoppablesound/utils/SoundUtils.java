package net.denanu.stoppablesound.utils;

import net.denanu.stoppablesound.events.ClientStoppableEntitySound;
import net.denanu.stoppablesound.events.ClientStoppablePosSound;
import net.denanu.stoppablesound.sounds.ITerminatable;
import net.denanu.stoppablesound.sounds.MovingPositionSoundInstance;
import net.denanu.stoppablesound.sounds.TerminatableEntityTrackingSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class SoundUtils {
	public static ITerminatable playSound(final MinecraftClient client, final BlockPos pos, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch) {
		return SoundUtils.playSound(client, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, category, volume, pitch);
	}

	public static ITerminatable playSound(final MinecraftClient client, final double x, final double y, final double z, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch) {
		return SoundUtils.playSound(client, x, y, z, sound, category, volume, pitch, false, client.world.getRandom().nextLong());
	}

	public static ITerminatable playSound(final MinecraftClient client, final double x, final double y, final double z, final SoundEvent event, final SoundCategory category, final float volume, final float pitch, final boolean useDistance, final long seed) {

		final MovingPositionSoundInstance positionedSoundInstance = new MovingPositionSoundInstance(event, category, volume, pitch, Random.create(seed), x, y, z, client.player);
		SoundUtils.start(positionedSoundInstance, client, x, y, z, useDistance);
		return positionedSoundInstance;
	}

	public static ITerminatable playSound(final MinecraftClient client, final Entity entity, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		return SoundUtils.playSound(client, event, category, volume, pitch, entity, false, client.world.getRandom().nextLong());
	}

	public static ITerminatable playSound(final MinecraftClient client, final SoundEvent event, final SoundCategory category, final float volume, final float pitch, final Entity entity, final boolean useDistance, final long seed) {
		final TerminatableEntityTrackingSoundInstance sound = new TerminatableEntityTrackingSoundInstance(event, category, volume, pitch, entity, seed, client.player);
		SoundUtils.start(sound, client, entity.getX(), entity.getY(), entity.getZ(), useDistance);
		return sound;
	}

	private static void start(final SoundInstance sound, final MinecraftClient client, final double x, final double y, final double z, final boolean useDistance) {
		final double d = client.gameRenderer.getCamera().getPos().squaredDistanceTo(x, y, z);
		if (useDistance && d > 100.0) {
			final double e = Math.sqrt(d) / 40.0;
			client.getSoundManager().play(sound, (int)(e * 20.0));
		} else {
			client.getSoundManager().play(sound);
		}
	}

	public static void playSound(final ClientStoppablePosSound sound) {
		sound.setSoundPlayer(
				SoundUtils.playSound(
						MinecraftClient.getInstance(),
						sound.getPlacer(),
						sound.getEvent(),
						sound.getCategory(),
						sound.getVolume(),
						sound.getPitch()
						)
				);
	}

	public static void playSound(final ClientStoppableEntitySound sound) {
		sound.setSoundPlayer(
				SoundUtils.playSound(
						MinecraftClient.getInstance(),
						sound.getPlacer(),
						sound.getEvent(),
						sound.getCategory(),
						sound.getVolume(),
						sound.getPitch()
						)
				);
	}


	public static float computeVolumeFade(final float defaultVolume, final PlayerEntity player, final double x, final double y, final double z) {
		return  (float) Math.min(defaultVolume, defaultVolume * 32f / player.squaredDistanceTo(x, y, z));
	}
}
