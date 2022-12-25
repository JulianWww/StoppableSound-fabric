package net.denanu.stoppablesound.events;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class StoppableEntitySound<W extends World> extends StoppableSound<Entity> {
	protected final W world;

	public StoppableEntitySound(final W world, final Entity placer, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		super(placer, event, category, volume, pitch);
		this.world = world;
	}

	public StoppableEntitySound(final W world, final PacketByteBuf buf) {
		super(buf);
		this.world = world;
	}

	public W getWorld() {
		return this.world;
	}

	@Override
	protected Entity readPlacer(final PacketByteBuf buf) {
		return this.world.getEntityById(buf.readInt());
	}

	@Override
	protected void writePlacer(final PacketByteBuf buf, final Entity entity) {
		buf.writeInt(entity.getId());
	}



}
