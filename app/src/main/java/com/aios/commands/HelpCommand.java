package com.aios.commands;

import com.aios.AiOSCore;
import java.util.List;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows a list of all available commands.";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        core.logEvent("Executing 'help' command.");
        StringBuilder helpText = new StringBuilder("Available commands:\n");
        for (Command cmd : registry.getAllCommands()) {
            helpText.append("- ")
                    .append(cmd.getName())
                    .append(": ")
                    .append(cmd.getDescription())
                    .append("\n");
        }
        return helpText.toString();
    }
}
