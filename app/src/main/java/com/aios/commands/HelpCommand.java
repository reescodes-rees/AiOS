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
    public String execute(List<String> args, AiOSCore core) {
        core.logEvent("Executing 'help' command.");
        // This is a placeholder. The real implementation will be in a future commit.
        return "Available commands:\n- help\n- createapp";
    }
}
