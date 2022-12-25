package net.denanu.stoppablesound.events;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class StoppablePosSound extends StoppableSound<BlockPos> {

	public StoppablePosSound(final BlockPos placer, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		super(placer, event, category, volume, pitch);
	}

	public StoppablePosSound(final PacketByteBuf buf) {
		super(buf);
	}

	@Override
	protected BlockPos readPlacer(final PacketByteBuf buf) {
		return buf.readBlockPos();
	}

	@Override
	protected void writePlacer(final PacketByteBuf buf, final BlockPos pos) {
		buf.writeBlockPos(pos);
	}

}
