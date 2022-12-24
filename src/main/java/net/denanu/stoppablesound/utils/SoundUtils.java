package net.denanu.stoppablesound.utils;

import net.denanu.stoppablesound.sounds.CurrentlyPlayingMap;
import net.denanu.stoppablesound.sounds.MovingPositionSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class SoundUtils {
	public static void playSound(final MinecraftClient client, final BlockPos pos, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch, final long key) {
		SoundUtils.playSound(client, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, category, volume, pitch, key);
	}

	public static void playSound(final MinecraftClient client, final double x, final double y, final double z, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch, final long key) {
		SoundUtils.playSound(client, x, y, z, sound, category, volume, pitch, false, client.world.getRandom().nextLong(), key);
	}

	public static void playSound(final MinecraftClient client, final double x, final double y, final double z, final SoundEvent event, final SoundCategory category, final float volume, final float pitch, final boolean useDistance, final long seed, final long key) {
		final double d = client.gameRenderer.getCamera().getPos().squaredDistanceTo(x, y, z);
		final MovingPositionSoundInstance positionedSoundInstance = new MovingPositionSoundInstance(event, category, volume, pitch, Random.create(seed), x, y, z, client.player);
		if (useDistance && d > 100.0) {
			final double e = Math.sqrt(d) / 40.0;
			client.getSoundManager().play(positionedSoundInstance, (int)(e * 20.0));
		} else {
			client.getSoundManager().play(positionedSoundInstance);
		}

		CurrentlyPlayingMap.currentlyPlayingSounds.put(key, positionedSoundInstance);
	}
}
