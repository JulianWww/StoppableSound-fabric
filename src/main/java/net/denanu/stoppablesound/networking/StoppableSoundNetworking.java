package net.denanu.stoppablesound.networking;

import net.denanu.stoppablesound.StoppableSound;
import net.denanu.stoppablesound.networking.s2c.StartPlaySoundPacketS2C;
import net.denanu.stoppablesound.networking.s2c.StopPlaySoundPacketS2C2;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class StoppableSoundNetworking {
	public static Identifier PLAY_SOUND = Identifier.of(StoppableSound.MOD_ID, "play");
	public static Identifier STOP_SOUND = Identifier.of(StoppableSound.MOD_ID, "stop");

	public static void registerC2SPackets() {
	}

	public static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(StoppableSoundNetworking.PLAY_SOUND, StartPlaySoundPacketS2C::receive);
		ClientPlayNetworking.registerGlobalReceiver(StoppableSoundNetworking.STOP_SOUND, StopPlaySoundPacketS2C2::receive);
	}

}
