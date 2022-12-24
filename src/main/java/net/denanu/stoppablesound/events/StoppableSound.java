package net.denanu.stoppablesound.events;

import net.denanu.stoppablesound.networking.StoppableSoundNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class StoppableSound {
	private final ServerWorld world;
	private final int x,y,z;
	private final SoundEvent event;
	private final SoundCategory category;
	private final float volume, pitch;
	private final long uuid;

	public StoppableSound(final ServerWorld world, final int x, final int y, final int z, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.event = event;
		this.category = category;
		this.volume = volume;
		this.pitch = pitch;
		this.uuid = UUIDProvider.getUUID();
	}

	public static StoppableSound of(final ServerWorld world, final int x, final int y, final int z, final SoundEvent event, final SoundCategory category, final float volume, final float pitch) {
		return new StoppableSound(world, x, y, z, event, category, volume, pitch);
	}

	public ServerWorld getWorld() {
		return this.world;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public SoundEvent getEvent() {
		return this.event;
	}

	public SoundCategory getCategory() {
		return this.category;
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}

	public long getUuid() {
		return this.uuid;
	}

	public PacketByteBuf toStartPacket(final PacketByteBuf buf) {
		buf.writeRegistryValue(Registry.SOUND_EVENT, this.event);
		buf.writeEnumConstant(this.category);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
		buf.writeLong(this.uuid);

		return buf;
	}

	private static PacketByteBuf toStopPacket(final PacketByteBuf buf, final long uuid) {
		buf.writeLong(uuid);
		return buf;
	}

	public StoppableSound playAll() {
		final PacketByteBuf buf = this.toStartPacket(PacketByteBufs.create());
		for (final ServerPlayerEntity player : this.world.getPlayers()) {
			ServerPlayNetworking.send(player, StoppableSoundNetworking.PLAY_SOUND, buf);
		}
		return this;
	}

	public StoppableSound stopAll() {
		StoppableSound.stopAll(this.uuid, this.world);
		return this;
	}

	public static void stopAll(final long key, final ServerWorld world) {
		final PacketByteBuf buf = StoppableSound.toStopPacket(PacketByteBufs.create(), key);
		for (final ServerPlayerEntity player : world.getPlayers()) {
			ServerPlayNetworking.send(player, StoppableSoundNetworking.STOP_SOUND, buf);
		}
	}
}
