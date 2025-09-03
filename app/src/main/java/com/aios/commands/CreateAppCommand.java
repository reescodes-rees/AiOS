package com.aios.commands;

import com.aios.AiOSCore;
import java.util.List;

public class CreateAppCommand implements Command {

    @Override
    public String getName() {
        // Using a single word for simplicity, will support arguments later.
        return "createapp";
    }

    @Override
    public String getDescription() {
        return "Starts the app creation process. Usage: createapp <AppName>";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        core.logEvent("Executing 'createapp' command with args: " + args);
        // In the future, this would take arguments from the `args` list,
        // like the app name, and use them to scaffold a new app.
        if (args.isEmpty()) {
            return "Please provide an app name. Usage: createapp <AppName>";
        }
        return "I understand you want to create an app named '" + args.get(0) + "'. This feature is coming soon!";
    }
}
