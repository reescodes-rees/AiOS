package com.aios.commands;

import com.aios.AiOSCore;
import java.io.File;
import java.util.List;

public class DeleteAppCommand implements Command {

    @Override
    public String getName() {
        return "deleteapp";
    }

    @Override
    public String getDescription() {
        return "Deletes a generated app. Usage: deleteapp <AppName>";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        core.logEvent("Executing 'deleteapp' command with args: " + args);

        if (args.isEmpty()) {
            return "Error: App name is required. Usage: deleteapp <AppName>";
        }
        String appName = args.get(0);

        // Check if the app exists in the registry before attempting deletion
        boolean appExists = core.getAppRegistry().getAppList().stream()
                .anyMatch(app -> app.getAppName().equalsIgnoreCase(appName));

        if (!appExists) {
            return "Error: App '" + appName + "' not found.";
        }

        File appDir = new File("generated_apps/" + appName);
        if (deleteRecursively(appDir)) {
            core.logEvent("Successfully deleted app directory: " + appDir.getPath());
            core.logEvent("Refreshing app registry...");
            core.getAppRegistry().scanForApps();
            core.logEvent("App registry refreshed.");
            return "Successfully deleted app '" + appName + "'.";
        } else {
            core.logEvent("Failed to delete app directory: " + appDir.getPath());
            return "Error: Failed to delete app '" + appName + "'.";
        }
    }

    /**
     * Recursively deletes a file or directory.
     * @param fileOrDirectory The file or directory to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    private boolean deleteRecursively(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            File[] children = fileOrDirectory.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        return fileOrDirectory.delete();
    }
}
