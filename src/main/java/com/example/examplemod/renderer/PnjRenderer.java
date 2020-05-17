package com.example.examplemod.renderer;

import com.example.examplemod.entity.PnjEntity;
import com.example.examplemod.model.PnjModel;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class PnjRenderer extends BipedRenderer<PnjEntity, PnjModel> {
    public PnjRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PnjModel(1.0F), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(PnjEntity entity) {
        return new ResourceLocation("examplemod:entity/slime-in-suit.png");
    }
}
