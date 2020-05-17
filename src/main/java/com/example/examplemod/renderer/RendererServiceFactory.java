package com.example.examplemod.renderer;

import com.example.examplemod.minecraft.IMinecraftService;

public class RendererServiceFactory {
    private IMinecraftService minecraftService;

    public RendererServiceFactory(IMinecraftService minecraftService) {
        this.minecraftService = minecraftService;
    }

    public IRendererService createRendererService() {
        return new RendererService(minecraftService);
    }
}
