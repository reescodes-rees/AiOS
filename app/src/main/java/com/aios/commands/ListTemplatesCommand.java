package com.aios.commands;

import com.aios.AiOSCore;
import com.aios.templates.TemplateManager;
import java.util.List;

public class ListTemplatesCommand implements Command {

    @Override
    public String getName() {
        return "listtemplates";
    }

    @Override
    public String getDescription() {
        return "Lists all available app templates.";
    }

    @Override
    public String execute(List<String> args, AiOSCore core) {
        core.logEvent("Executing 'listtemplates' command.");
        TemplateManager templateManager = core.getTemplateManager();
        List<String> templates = templateManager.getAvailableTemplates();

        if (templates.isEmpty()) {
            return "No app templates found.";
        }

        StringBuilder sb = new StringBuilder("Available App Templates:\n");
        for (String templateName : templates) {
            sb.append("- ").append(templateName).append("\n");
        }

        return sb.toString();
    }
}
