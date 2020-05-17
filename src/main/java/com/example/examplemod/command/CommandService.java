package com.example.examplemod.command;

import com.example.examplemod.quest.IQuestService;
import com.example.examplemod.server.IServerService;
import com.example.examplemod.server.ServerService;
import com.example.examplemod.utils.Assert;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class CommandService implements ICommandService {

    private static final Logger LOGGER = LogManager.getLogger();

    private IServerService serverService;
    private IQuestService questService;
    private Set<ICommand> commands = new HashSet<>();

    public CommandService(IServerService serverService, IQuestService questService) {
        this.serverService = serverService;
        this.questService = questService;
        this.commands.add(createSpawnPnjCommand());
        this.commands.add(createCreateQuestCommand());
        this.commands.add(createReadQuestCommand());
    }

    private SpawnPnjCommand createSpawnPnjCommand() {
        return new SpawnPnjCommand(questService);
    }

    private CreateQuestCommand createCreateQuestCommand() {
        return new CreateQuestCommand(questService);
    }

    private ReadQuestCommand createReadQuestCommand() {
        return new ReadQuestCommand(questService);
    }

    @Override
    public void registerCommands() {
        this.commands.stream().forEach(this::registerCommand);
    }

    @Override
    public void registerCommand(ICommand command) {
        LOGGER.info("Registering command of type {}", command.getClass().getName());
        CommandDispatcher<CommandSource> dispatcher = this.serverService.getServer().getCommandManager().getDispatcher();
        Assert.notNull(dispatcher, "Could not retrieve command dispatcher from server");

        command.register(dispatcher);
    }
}
