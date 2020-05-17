package com.example.examplemod.quest;

import java.util.Optional;
import java.util.Set;

public interface IQuestService {
    Quest createQuest(String title, String description);

    Set<Quest> retrieveQuests();

    void writeQuest(Quest quest);

    Optional<IQuest> loadQuest(Long id);
}
