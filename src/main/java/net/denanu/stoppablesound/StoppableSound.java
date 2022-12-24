package net.denanu.stoppablesound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class StoppableSound implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("stoppablesound");

	@Override
	public void onInitialize() {
		StoppableSound.LOGGER.info("Hello Fabric world!");
	}
}
