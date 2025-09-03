package com.aios.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages and provides access to all available AI commands.
 */
public class CommandRegistry {

    private final Map<String, Command> commands;

    public CommandRegistry() {
        this.commands = new HashMap<>();
    }

    /**
     * Registers a new command.
     * @param command The command to register.
     */
    public void registerCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    /**
     * Retrieves a command by its name.
     * @param commandName The name of the command to retrieve.
     * @return The Command object, or null if not found.
     */
    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }

    /**
     * Retrieves a read-only collection of all registered commands.
     * @return A collection of all commands.
     */
    public Collection<Command> getAllCommands() {
        return Collections.unmodifiableCollection(commands.values());
    }
}
