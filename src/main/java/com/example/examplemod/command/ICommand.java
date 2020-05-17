package com.example.examplemod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public interface ICommand {
    void register(CommandDispatcher<CommandSource> dispatcher);
}
