package com.example.examplemod.quest;

import com.example.examplemod.server.IServerService;

public class QuestServiceFactory {

    private IServerService serverService;

    public QuestServiceFactory(IServerService serverService) {
        this.serverService = serverService;
    }

    public IQuestService createQuestService() {
        return new QuestService(serverService);
    }
}
