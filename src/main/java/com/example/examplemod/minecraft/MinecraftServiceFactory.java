package com.example.examplemod.minecraft;

import net.minecraft.client.Minecraft;

public class MinecraftServiceFactory {
    public IMinecraftService createMinecraftService() {
        return new MinecraftService();
    }
}
