package com.example.examplemod.command;

import com.example.examplemod.entity.PnjEntity;
import com.example.examplemod.minecraft.MinecraftService;
import com.example.examplemod.quest.IQuest;
import com.example.examplemod.quest.IQuestService;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Optional;

public class SpawnPnjCommand implements ICommand {
    private IQuestService questService;

    public SpawnPnjCommand(IQuestService questService) {
        this.questService = questService;
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("spawn-pnj").then(
                Commands.argument("questId", LongArgumentType.longArg())
        .executes((args) -> {
            this.executes(args.getSource(), args.getArgument("questId", Long.class));
            return 0;
        })));
    }

    private void executes(CommandSource source, Long questId) throws CommandSyntaxException {
        Optional<IQuest> quest = this.questService.loadQuest(questId);
        if(!quest.isPresent()) {
            source.sendFeedback(new StringTextComponent("Could not find quest with id " + questId), true);
            return;
        }

        ServerPlayerEntity player = source.asPlayer();
        World world = player.getEntityWorld();
        PnjEntity pnj = new PnjEntity(world, quest.get());
        pnj.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
        world.addEntity(pnj);
    }
}
