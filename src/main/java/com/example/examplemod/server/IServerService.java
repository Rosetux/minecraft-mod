package com.example.examplemod.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.function.Consumer;

public interface IServerService {
    void addListener(Consumer<FMLServerStartedEvent> consumer);

    MinecraftServer getServer();
}
