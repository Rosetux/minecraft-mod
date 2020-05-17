package com.example.examplemod.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class RendererWrapper<T extends Entity> {
    private EntityType<T> entityType;
    private EntityRenderer<T> renderer;

    public RendererWrapper(EntityType<T> entityType, EntityRenderer<T> renderer) {
        this.entityType = entityType;
        this.renderer = renderer;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType<T> entityType) {
        this.entityType = entityType;
    }

    public EntityRenderer<?> getRenderer() {
        return renderer;
    }

    public void setRenderer(EntityRenderer<T> renderer) {
        this.renderer = renderer;
    }
}
