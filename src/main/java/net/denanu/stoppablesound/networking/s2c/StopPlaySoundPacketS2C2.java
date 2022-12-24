package net.denanu.stoppablesound.networking.s2c;

import net.denanu.stoppablesound.StoppableSound;
import net.denanu.stoppablesound.networking.NetworkingUtils;
import net.denanu.stoppablesound.networking.s2c.data.StopPlaySoundPacketS2C_Data;
import net.denanu.stoppablesound.sounds.CurrentlyPlayingMap;
import net.denanu.stoppablesound.sounds.ITerminatable;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class StopPlaySoundPacketS2C2 {
	public static void receive(final MinecraftClient client, final ClientPlayNetworkHandler handler,
			final PacketByteBuf buf, final PacketSender responseSender) {
		final StopPlaySoundPacketS2C_Data data = new StopPlaySoundPacketS2C_Data(buf);
		NetworkingUtils.forceMainThread(StopPlaySoundPacketS2C2::run, client.getNetworkHandler(), client, client, handler, data, responseSender);
	}

	public static void run(final MinecraftClient client, final ClientPlayNetworkHandler handler, final StopPlaySoundPacketS2C_Data data, final PacketSender responseSender) {
		final ITerminatable sound = CurrentlyPlayingMap.currentlyPlayingSounds.remove(data.getId());
		if (sound == null) {
			StoppableSound.LOGGER.error(
					new StringBuilder()
					.append("Sound key: ")
					.append(data.getId())
					.append(" is not available on the client")
					.toString()
					);
		}
		else {
			sound.terminate();
		}
	}
}
