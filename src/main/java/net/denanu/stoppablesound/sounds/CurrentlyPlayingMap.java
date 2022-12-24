package net.denanu.stoppablesound.sounds;

import java.util.HashMap;

public class CurrentlyPlayingMap {
	public static HashMap<Long, ITerminatable> currentlyPlayingSounds = new HashMap<>();

	public static void reset() {
		CurrentlyPlayingMap.currentlyPlayingSounds.clear();
	}
}
