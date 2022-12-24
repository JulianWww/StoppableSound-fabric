package net.denanu.stoppablesound.events;

public class UUIDProvider {
	private static long uid = 0;

	public static long getUUID() {
		return UUIDProvider.uid++;
	}
}
