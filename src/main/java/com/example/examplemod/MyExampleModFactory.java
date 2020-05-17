package com.example.examplemod;

import com.example.examplemod.command.CommandServiceFactory;
import com.example.examplemod.command.ICommandService;
import com.example.examplemod.minecraft.IMinecraftService;
import com.example.examplemod.minecraft.MinecraftService;
import com.example.examplemod.minecraft.MinecraftServiceFactory;
import com.example.examplemod.quest.IQuestService;
import com.example.examplemod.quest.QuestServiceFactory;
import com.example.examplemod.renderer.IRendererService;
import com.example.examplemod.renderer.RendererServiceFactory;
import com.example.examplemod.server.IServerService;
import com.example.examplemod.server.ServerServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyExampleModFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public void createMod() {
        IServerService serverService = new ServerServiceFactory().createServerService();
        IQuestService questService = new QuestServiceFactory(serverService).createQuestService();
        ICommandService commandService = new CommandServiceFactory(serverService, questService).createCommandService();
        IMinecraftService minecraftService = new MinecraftServiceFactory().createMinecraftService();
        IRendererService rendererService = new RendererServiceFactory(minecraftService).createRendererService();
        LOGGER.info("Creating mod...");
        new MyExampleMod(commandService, serverService, rendererService, questService);
        LOGGER.info("Mod created");
    }
}
