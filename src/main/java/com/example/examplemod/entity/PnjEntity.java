package com.example.examplemod.entity;

import com.example.examplemod.quest.IQuest;
import com.example.examplemod.quest.capability.IQuestCapability;
import com.example.examplemod.quest.capability.QuestCapabilityStorage;
import com.example.examplemod.screen.QuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class PnjEntity extends CreatureEntity {
    private IQuest quest;

    public PnjEntity(World worldIn, IQuest questHolding) {
        super(ModEntityType.PNJ, worldIn);
        this.quest = questHolding;

        LazyOptional<IQuestCapability> oQuestCapability = this.getCapability(QuestCapabilityStorage.QUEST_CAPABILITY, null);
        if(oQuestCapability.isPresent()) {
            IQuestCapability questCapability = oQuestCapability.orElseThrow(() -> {
                return new IllegalArgumentException("No quest capability class found");
            });

            questCapability.setQuest(this.quest);
        }
    }

    public PnjEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);

        LazyOptional<IQuestCapability> oQuestCapability = this.getCapability(QuestCapabilityStorage.QUEST_CAPABILITY, null);
        if(oQuestCapability.isPresent()) {
            IQuestCapability questCapability = oQuestCapability.orElseThrow(() -> {
                return new IllegalArgumentException("No quest capability class found");
            });

            this.quest = questCapability.getQuest();
        }
    }

    public PnjEntity(World worldIn) {
        super(ModEntityType.PNJ, worldIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(FMLEnvironment.dist.isClient()) {
            if (source.getTrueSource() instanceof PlayerEntity) {
                if (quest != null) {
                    Minecraft.getInstance().displayGuiScreen(new QuestScreen(quest));
                }
            }
        }

        return false;
    }
}
