package com.example.examplemod.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;

public class MinecraftService implements IMinecraftService {
    private Minecraft minecraft = Minecraft.getInstance();

    public MinecraftService() {
    }

    @Override
    public Minecraft getMinecraft() {
        return minecraft;
    }
}
