package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.sounds.ITerminatable;
import net.minecraft.network.PacketByteBuf;

public class ClientStoppableSound extends StoppableSound {
	private ITerminatable soundPlayer;

	public void setSoundPlayer(final ITerminatable soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	public ITerminatable getSoundPlayer() {
		return this.soundPlayer;
	}

	public ClientStoppableSound(final PacketByteBuf buf) {
		super(buf);
	}
}
