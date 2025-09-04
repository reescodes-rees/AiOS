package com.aios.commands;

import com.aios.AiOSCore;
import java.util.List;

/**
 * Represents a command that the AI can execute.
 */
public interface Command {
    /**
     * Executes the command.
     * @param args The arguments for the command.
     * @param core The AI's core memory/state, allowing commands to interact with it.
     * @param registry The command registry, allowing commands to access other commands.
     * @return The result or response message from the command.
     */
    String execute(List<String> args, AiOSCore core, CommandRegistry registry);

    /**
     * @return The name of the command (e.g., "help", "create").
     */
    String getName();

    /**
     * @return A brief description of what the command does.
     */
    String getDescription();
}
