package net.denanu.stoppablesound.events;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class StoppableEntitySound extends StoppableSound<Entity> {

	public StoppableEntitySound(final Entity placer, final RegistryEntry<SoundEvent> event, final SoundCategory category, final float volume, final float pitch, final long seed) {
		super(placer, event, category, volume, pitch, seed);
	}

	public StoppableEntitySound(final World world, final PacketByteBuf buf) {
		super(buf, world);
	}

	@Override
	protected Entity readPlacer(final PacketByteBuf buf, final World world) {
		return world.getEntityById(buf.readInt());
	}

	@Override
	protected void writePlacer(final PacketByteBuf buf, final Entity entity) {
		buf.writeInt(entity.getId());
	}



}
