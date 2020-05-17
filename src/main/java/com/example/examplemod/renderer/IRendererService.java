package com.example.examplemod.renderer;

import net.minecraft.entity.Entity;

public interface IRendererService {
    void registerRenderer(RendererWrapper rendererWrapper);

    void registerRenderers();
}
