package com.example.examplemod.renderer;

import com.example.examplemod.entity.ModEntityType;
import com.example.examplemod.entity.PnjEntity;
import com.example.examplemod.minecraft.IMinecraftService;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class RendererService implements IRendererService {

    private final IMinecraftService minecraftService;
    
    private Set<RendererWrapper<?>> renderers = new HashSet<>();

    public RendererService(IMinecraftService minecraftService) {
        this.minecraftService = minecraftService;
    }
    
    private RendererWrapper<PnjEntity> createPnjRenderer() {
        PnjRenderer renderer = new PnjRenderer(this.minecraftService.getMinecraft().getRenderManager());
        return new RendererWrapper<>(ModEntityType.PNJ, renderer);
    }

    @Override
    public void registerRenderer(RendererWrapper rendererWrapper) {
        EntityRendererManager manager = this.minecraftService.getMinecraft().getRenderManager();
        manager.register(rendererWrapper.getEntityType(), rendererWrapper.getRenderer());
    }

    @Override
    public void registerRenderers() {
        this.renderers.add(createPnjRenderer());
        this.renderers.stream().forEach(this::registerRenderer);
    }
}
