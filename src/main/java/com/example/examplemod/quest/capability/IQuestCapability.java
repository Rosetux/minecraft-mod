package com.example.examplemod.quest.capability;

import com.example.examplemod.quest.IQuest;

import java.util.Optional;

public interface IQuestCapability {
    IQuest getQuest();

    void setQuest(IQuest quest);
}
