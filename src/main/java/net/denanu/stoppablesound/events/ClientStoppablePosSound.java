package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.sounds.ITerminatable;
import net.minecraft.network.PacketByteBuf;

public class ClientStoppablePosSound extends StoppablePosSound {
	private ITerminatable soundPlayer;

	public ClientStoppablePosSound(final PacketByteBuf buf) {
		super(buf);
	}

	public void setSoundPlayer(final ITerminatable soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	public ITerminatable getSoundPlayer() {
		return this.soundPlayer;
	}

	public void terminate() {
		this.soundPlayer.terminate();
	}
}
