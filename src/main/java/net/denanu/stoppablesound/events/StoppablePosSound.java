package net.denanu.stoppablesound.events;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoppablePosSound extends StoppableSound<BlockPos> {

	public StoppablePosSound(final BlockPos placer, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		super(placer, event, category, volume, pitch);
	}

	public StoppablePosSound(final PacketByteBuf buf) {
		super(buf, null);
	}

	@Override
	protected BlockPos readPlacer(final PacketByteBuf buf, final @Nullable World world) {
		return buf.readBlockPos();
	}

	@Override
	protected void writePlacer(final PacketByteBuf buf, final BlockPos pos) {
		buf.writeBlockPos(pos);
	}

}
