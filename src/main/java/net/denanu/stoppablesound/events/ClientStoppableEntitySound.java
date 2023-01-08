package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.sounds.ITerminatable;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;

public class ClientStoppableEntitySound extends StoppableEntitySound {
	private ITerminatable soundPlayer;

	public ClientStoppableEntitySound(final World world, final PacketByteBuf buf) {
		super(world, buf);
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
