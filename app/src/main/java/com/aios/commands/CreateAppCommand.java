package com.aios.commands;

import com.aios.AiOSCore;
import com.aios.templates.TemplateManager;
import com.aios.utils.FileGenerator;
import java.io.IOException;
import java.util.List;

public class CreateAppCommand implements Command {

    @Override
    public String getName() {
        return "createapp";
    }

    @Override
    public String getDescription() {
        return "Creates a new app from a template. Usage: createapp <AppName> <TemplateName>";
    }

    @Override
    public String execute(List<String> args, AiOSCore core) {
        core.logEvent("Executing 'createapp' command with args: " + args);

        if (args.size() < 2) {
            return "Error: App name and template name are required. Usage: createapp <AppName> <TemplateName>";
        }

        String appName = args.get(0);
        String templateName = args.get(1);
        TemplateManager templateManager = core.getTemplateManager();

        // Check if template exists
        if (!templateManager.getAvailableTemplates().contains(templateName)) {
            return "Error: Template '" + templateName + "' not found. Use 'listtemplates' to see available templates.";
        }

        // Basic sanitization for package name
        String packageName = appName.toLowerCase().replaceAll("[^a-z0-9_]", "");
        if (packageName.isEmpty()) {
            return "Error: Invalid app name. Please use alphanumeric characters.";
        }

        String baseDir = "generated_apps/" + appName;
        core.logEvent("Starting app generation for '" + appName + "' from template '" + templateName + "' in " + baseDir);

        try {
            // 1. Create Gradle file
            String gradleTemplate = templateManager.getTemplateContent(templateName, "build.gradle.template");
            String gradleContent = gradleTemplate.replace("__PACKAGE_NAME__", packageName);
            if (!FileGenerator.writeToFile(baseDir + "/build.gradle", gradleContent)) {
                throw new IOException("Failed to write build.gradle");
            }
            core.logEvent("Successfully generated build.gradle");

            // 2. Create Manifest file
            String manifestTemplate = templateManager.getTemplateContent(templateName, "AndroidManifest.xml.template");
            String manifestContent = manifestTemplate.replace("__APP_NAME__", appName)
                    .replace("__PACKAGE_NAME__", packageName);
            if (!FileGenerator.writeToFile(baseDir + "/src/main/AndroidManifest.xml", manifestContent)) {
                throw new IOException("Failed to write AndroidManifest.xml");
            }
            core.logEvent("Successfully generated AndroidManifest.xml");

            // 3. Create MainActivity file
            String activityTemplate = templateManager.getTemplateContent(templateName, "MainActivity.java.template");
            String activityContent = activityTemplate.replace("__APP_NAME__", appName)
                    .replace("__PACKAGE_NAME__", packageName);
            String activityPath = baseDir + "/src/main/java/com/aios/generated/" + packageName + "/MainActivity.java";
            if (!FileGenerator.writeToFile(activityPath, activityContent)) {
                throw new IOException("Failed to write MainActivity.java");
            }
            core.logEvent("Successfully generated MainActivity.java");

        } catch (IOException e) {
            core.logEvent("App generation failed: " + e.getMessage());
            return "Error: App generation failed. Could not write a template file. " + e.getMessage();
        }

        return "Successfully created app '" + appName + "' from template '" + templateName + "'.";
    }
}
