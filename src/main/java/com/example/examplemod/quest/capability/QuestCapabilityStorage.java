package com.example.examplemod.quest.capability;

import com.example.examplemod.quest.IQuest;
import com.example.examplemod.quest.IQuestService;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;
import java.util.Optional;

public class QuestCapabilityStorage implements Capability.IStorage<IQuestCapability> {
    @CapabilityInject(IQuestCapability.class)
    public static Capability<IQuestCapability> QUEST_CAPABILITY = null;
    private IQuestService questService;

    public QuestCapabilityStorage(IQuestService questService) {
        this.questService = questService;
    }

    @Nullable
    @Override
    public INBT writeNBT(Capability<IQuestCapability> capability, IQuestCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        if(instance.getQuest() != null) {
            Long questId = instance.getQuest().getId();
            compound.put("questId", LongNBT.valueOf(questId));
        }

        return compound;
    }

    @Override
    public void readNBT(Capability<IQuestCapability> capability, IQuestCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        Long questId = ((LongNBT) compound.get("questId")).getLong();

        Optional<IQuest> quest = questService.loadQuest(questId);
        if(quest.isPresent()) {
            instance.setQuest(quest.get());
        }
    }
}
