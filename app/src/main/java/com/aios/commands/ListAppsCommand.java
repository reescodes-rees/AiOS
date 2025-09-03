package com.aios.commands;

import com.aios.AiOSCore;
import com.aios.app_registry.AppInfo;
import com.aios.app_registry.AppRegistry;
import java.util.List;

public class ListAppsCommand implements Command {

    @Override
    public String getName() {
        return "listapps";
    }

    @Override
    public String getDescription() {
        return "Lists all apps that have been generated.";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        core.logEvent("Executing 'listapps' command.");
        AppRegistry appRegistry = core.getAppRegistry();
        List<AppInfo> apps = appRegistry.getAppList();

        if (apps.isEmpty()) {
            return "No generated apps found.";
        }

        StringBuilder sb = new StringBuilder("Generated Applications:\n");
        for (AppInfo app : apps) {
            sb.append("- ").append(app.toString()).append("\n");
        }

        return sb.toString();
    }
}
