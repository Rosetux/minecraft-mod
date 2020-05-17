package com.example.examplemod.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class ModEntityType {
    public static EntityType<PnjEntity> PNJ;

    public static void init() {
        EntityType.Builder<PnjEntity> build = EntityType.Builder.<PnjEntity>create(PnjEntity::new, EntityClassification.CREATURE);
        PNJ = Registry.register(Registry.ENTITY_TYPE, "pnj", build.build("pnj"));
    }
}
