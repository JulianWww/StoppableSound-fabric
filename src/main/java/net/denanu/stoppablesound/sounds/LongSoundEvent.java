package net.denanu.stoppablesound.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

// provides play time data to sound Event. Only works with a single sound being played.
public class LongSoundEvent extends SoundEvent {
	private final int length;

	public LongSoundEvent(final Identifier id, final float distanceToTravel, final int length) {
		super(id, distanceToTravel);
		this.length = length;
	}

	public LongSoundEvent(final Identifier id, final int length) {
		super(id);
		this.length = length;
	}


	public int getLength() {
		return this.length;
	}
}
