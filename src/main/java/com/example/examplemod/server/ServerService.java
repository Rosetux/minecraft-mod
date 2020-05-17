package com.example.examplemod.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ServerService implements IServerService {

    private MinecraftServer server;

    public ServerService() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerServer);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void addListener(Consumer<FMLServerStartedEvent> consumer) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(consumer);
    }

    @Override
    public MinecraftServer getServer() {
        return this.server;
    }

    @SubscribeEvent
    public void registerServer(FMLServerStartedEvent event) {
        this.server = event.getServer();
    }

    @SubscribeEvent
    public void unregisterServer(FMLServerStoppingEvent event) {
        this.server = null;
    }
}
