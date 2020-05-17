package com.example.examplemod.quest.capability;

import com.example.examplemod.quest.IQuest;
import net.minecraft.util.ResourceLocation;

public class QuestCapability implements IQuestCapability {
    public static final ResourceLocation CAPABILITY_LOCATION = new ResourceLocation("examplemod", "quests");

    @Override
    public IQuest getQuest() {
        return null;
    }

    @Override
    public void setQuest(IQuest quest) {

    }
}
