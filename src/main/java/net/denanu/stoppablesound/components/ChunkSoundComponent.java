package net.denanu.stoppablesound.components;

import net.denanu.stoppablesound.events.ClientStoppablePosSound;
import net.denanu.stoppablesound.events.StoppableSound;
import net.denanu.stoppablesound.utils.SoundUtils;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class ChunkSoundComponent extends SoundComponent<Chunk, BlockPos> {

	ChunkSoundComponent(final Chunk provider) {
		super(provider);
	}

	@Override
	void sync(final StoppableSound<BlockPos> sound) {
		ChunkComponents.SOUNDS.sync(this.provider, (buf, p) -> SoundComponent.writeSyncPacket(buf, p, sound));
	}

	@Override
	void sync(final long uuid) {
		ChunkComponents.SOUNDS.sync(this.provider, (buf, p) -> SoundComponent.writeSyncPacket(buf, p, uuid));
	}

	@Override
	protected void syncAddElement(final PacketByteBuf buf) {
		final ClientStoppablePosSound sound = new ClientStoppablePosSound(buf);
		this.activeSounds.put(sound.getUuid(), sound);
		SoundUtils.playSound(sound);
	}
}
