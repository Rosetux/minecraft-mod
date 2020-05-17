package com.example.examplemod.quest.capability;

import com.example.examplemod.quest.IQuest;
import com.example.examplemod.quest.IQuestService;

import java.util.concurrent.Callable;

public class QuestCapabilityFactory implements Callable<IQuestCapability> {
    private IQuestService questService;

    public QuestCapabilityFactory(IQuestService questService) {
        this.questService = questService;
    }

    @Override
    public IQuestCapability call() {
        return new QuestCapability();
    }
}
