package com.aios.commands;

import com.aios.AiOSCore;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFileCommand implements Command {

    @Override
    public String getName() {
        return "readfile";
    }

    @Override
    public String getDescription() {
        return "Reads the content of a file from a generated app. Usage: readfile <AppName>/<path_to_file>";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        core.logEvent("Executing 'readfile' command with args: " + args);

        if (args.isEmpty()) {
            return "Error: File path is required. Usage: readfile <AppName>/<path_to_file>";
        }
        String relativePath = args.get(0);
        String fullPath = "generated_apps/" + relativePath;

        File file = new File(fullPath);
        if (!file.exists() || file.isDirectory()) {
            core.logEvent("File not found at: " + fullPath);
            return "Error: File not found at '" + relativePath + "'.";
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(fullPath)));
            core.logEvent("Successfully read file: " + fullPath);
            return "Content of " + relativePath + ":\n" + content;
        } catch (IOException e) {
            core.logEvent("Failed to read file: " + fullPath + " - " + e.getMessage());
            return "Error: Could not read file. " + e.getMessage();
        }
    }
}
