package com.example.examplemod.command;

import com.example.examplemod.quest.IQuestService;
import com.example.examplemod.quest.Quest;
import com.example.examplemod.quest.QuestService;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class CreateQuestCommand implements ICommand {
    private IQuestService questService;

    public CreateQuestCommand(IQuestService questService) {
        this.questService = questService;
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("create-quest").then(
                Commands.argument("questTitle", StringArgumentType.string()).executes((args)->{
                    Quest quest = questService.createQuest(args.getArgument("questTitle", String.class), "");
                    questService.writeQuest(quest);
                    args.getSource().sendFeedback(new StringTextComponent("Your quest ID is " + quest.getId()), true);
                    return 0;
                })
        );

        dispatcher.register(commandBuilder);
    }
}
