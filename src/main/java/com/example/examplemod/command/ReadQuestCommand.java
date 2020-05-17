package com.example.examplemod.command;

import com.example.examplemod.quest.IQuest;
import com.example.examplemod.quest.IQuestService;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

import java.util.Optional;

public class ReadQuestCommand implements ICommand {

    private IQuestService questService;

    public ReadQuestCommand(IQuestService questService) {
        this.questService = questService;
    }

    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("read-quest").then(
                Commands.argument("questId", LongArgumentType.longArg()).executes((args) -> {
                    Optional<IQuest> quest = this.questService.loadQuest(args.getArgument("questId", Long.class));
                    if(quest.isPresent()) {
                        args.getSource().sendFeedback(new StringTextComponent("Your quest title is " +
                                quest.get().getTitle()), true);
                    } else {
                        args.getSource().sendFeedback(new StringTextComponent("Cannot find quest with id " +
                                args.getArgument("questId", Long.class)), true);
                    }
                    return 1;
                })
        );

        dispatcher.register(commandBuilder);
    }
}
