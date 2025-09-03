package com.aios;

import com.aios.commands.Command;
import com.aios.commands.CommandRegistry;
import com.aios.commands.CreateAppCommand;
import com.aios.commands.HelpCommand;
import com.aios.commands.ListTemplatesCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AiService {

    private final AiOSCore core;
    private final CommandRegistry registry;

    public AiService(AiOSCore core) {
        this.core = core;
        this.registry = new CommandRegistry();
        registerCommands();
    }

    private void registerCommands() {
        registry.registerCommand(new HelpCommand());
        registry.registerCommand(new CreateAppCommand());
        registry.registerCommand(new ListTemplatesCommand());
    }

    public Message getResponse(String userInput) {
        core.logEvent("User input received: \"" + userInput + "\"");
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            return new Message("Please enter a command.", Message.Sender.AI);
        }

        String[] parts = trimmedInput.split("\\s+");
        String commandName = parts[0].toLowerCase();
        List<String> args = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));

        Command command = registry.getCommand(commandName);
        String responseText;

        if (command != null) {
            responseText = command.execute(args, core);
        } else {
            responseText = "Unknown command: '" + commandName + "'. Type 'help' for a list of commands.";
            core.logEvent("Unknown command received: " + commandName);
        }

        return new Message(responseText, Message.Sender.AI);
    }
}
