package net.denanu.stoppablesound.networking.s2c;

import net.denanu.stoppablesound.networking.NetworkingUtils;
import net.denanu.stoppablesound.networking.s2c.data.StartPlaySoundPacketS2C_Data;
import net.denanu.stoppablesound.utils.SoundUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class StartPlaySoundPacketS2C {
	public static void receive(final MinecraftClient client, final ClientPlayNetworkHandler handler,
			final PacketByteBuf buf, final PacketSender responseSender) {
		final StartPlaySoundPacketS2C_Data data = new StartPlaySoundPacketS2C_Data(buf);
		NetworkingUtils.forceMainThread(StartPlaySoundPacketS2C::run, client.getNetworkHandler(), client, client, handler, data, responseSender);
	}

	public static void run(final MinecraftClient client, final ClientPlayNetworkHandler handler, final StartPlaySoundPacketS2C_Data data, final PacketSender responseSender) {
		SoundUtils.playSound(
				client,
				data.getFixedX(),
				data.getFixedY(),
				data.getFixedZ(),
				data.getSound(),
				data.getCategory(),
				1f,
				1f,
				data.getSoundId()
				);
	}
}
