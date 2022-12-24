package net.denanu.stoppablesound;

import net.denanu.stoppablesound.networking.StoppableSoundNetworking;
import net.denanu.stoppablesound.sounds.CurrentlyPlayingMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class StoppableSoundClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Networking
		StoppableSoundNetworking.registerS2CPackets();

		// Local sound strorage
		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> CurrentlyPlayingMap.reset());
		ClientLifecycleEvents.CLIENT_STARTED. register(client -> CurrentlyPlayingMap.reset());
	}

}
