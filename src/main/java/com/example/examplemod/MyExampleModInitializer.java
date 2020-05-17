package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("examplemod")
public class MyExampleModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();

    public MyExampleModInitializer() {
        LOGGER.info("Initialization of mod");
        new MyExampleModFactory().createMod();
    }
}
