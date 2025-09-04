package com.aios.templates;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Discovers and manages the available application templates.
 */
public class TemplateManager {

    private static final String TEMPLATES_BASE_DIR = "templates/";
    private final List<String> templateNames;

    public TemplateManager() {
        this.templateNames = new ArrayList<>();
        discoverTemplates();
    }

    /**
     * Scans the base templates directory to find all available template names.
     */
    private void discoverTemplates() {
        templateNames.clear();
        File baseDir = new File(TEMPLATES_BASE_DIR);
        File[] templateDirs = baseDir.listFiles(File::isDirectory);

        if (templateDirs != null) {
            for (File dir : templateDirs) {
                templateNames.add(dir.getName());
            }
        }
    }

    /**
     * Returns a read-only list of available template names.
     * @return An unmodifiable list of template names.
     */
    public List<String> getAvailableTemplates() {
        return Collections.unmodifiableList(templateNames);
    }

    /**
     * Reads the content of a specific file from a specific template.
     * @param templateName The name of the template directory (e.g., "HelloWorldApp").
     * @param fileName The name of the template file (e.g., "build.gradle.template").
     * @return The content of the file as a String.
     * @throws IOException if the file cannot be read.
     */
    public String getTemplateContent(String templateName, String fileName) throws IOException {
        String path = TEMPLATES_BASE_DIR + templateName + "/" + fileName;
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
