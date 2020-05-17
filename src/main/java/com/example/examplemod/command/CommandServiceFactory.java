package com.example.examplemod.command;

import com.example.examplemod.quest.IQuestService;
import com.example.examplemod.server.IServerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandServiceFactory {

    private static final Logger LOGGER = LogManager.getLogger();

    private IServerService serverService;
    private IQuestService questService;

    public CommandServiceFactory(IServerService serverService, IQuestService questService) {
        this.serverService = serverService;
        this.questService = questService;
    }

    public ICommandService createCommandService() {
        LOGGER.info("Creating command service");
        return new CommandService(serverService, questService);
    }
}
