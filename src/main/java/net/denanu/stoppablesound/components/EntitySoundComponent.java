package net.denanu.stoppablesound.components;

import net.denanu.stoppablesound.events.ClientStoppableEntitySound;
import net.denanu.stoppablesound.events.StoppableSound;
import net.denanu.stoppablesound.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;

public class EntitySoundComponent extends SoundComponent<Entity, Entity> {

	public EntitySoundComponent(final Entity provider) {
		super(provider);
	}

	@Override
	void sync(final StoppableSound<Entity> sound) {
		EntityComponents.SOUNDS.sync(this.provider, (buf, p) -> SoundComponent.writeSyncPacket(buf, p, sound));
	}

	@Override
	void sync(final long uuid) {
		EntityComponents.SOUNDS.sync(this.provider, (buf, p) -> SoundComponent.writeSyncPacket(buf, p, uuid));
	}

	@Override
	protected void syncAddElement(final PacketByteBuf buf) {
		final ClientStoppableEntitySound sound = new ClientStoppableEntitySound(this.provider.world, buf);
		this.activeSounds.put(sound.getUuid(), sound);
		SoundUtils.playSound(sound);
	}
}
