package net.denanu.stoppablesound.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundContainer;
import net.minecraft.client.sound.WeightedSoundSet;

@Mixin(WeightedSoundSet.class)
public interface WeightedSoundSetAccessor {
	@Accessor
	List<SoundContainer<Sound>> getSounds();
}
