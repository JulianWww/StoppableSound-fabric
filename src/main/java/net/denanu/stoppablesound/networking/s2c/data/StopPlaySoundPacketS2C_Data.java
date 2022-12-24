package net.denanu.stoppablesound.networking.s2c.data;

import net.minecraft.network.PacketByteBuf;

public class StopPlaySoundPacketS2C_Data {
	private final long id;

	public StopPlaySoundPacketS2C_Data(final PacketByteBuf buf) {
		this.id = buf.readLong();
	}

	public long getId() {
		return this.id;
	}
}
