package com.aios;

import com.aios.templates.TemplateManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the core memory and state of the AiOS.
 * This class is intended to be a singleton or a centrally managed instance.
 */
public class AiOSCore {

    private final List<String> eventLog;
    private final TemplateManager templateManager;

    public AiOSCore() {
        this.eventLog = new ArrayList<>();
        this.templateManager = new TemplateManager();
        logEvent("AiOS Core Initialized.");
        logEvent("Template scan complete. Found " + this.templateManager.getAvailableTemplates().size() + " templates.");
    }

    /**
     * Records a new event in the AI's memory log.
     * @param event The description of the event to log.
     */
    public void logEvent(String event) {
        // In a real app, we would likely add a timestamp.
        this.eventLog.add(event);
    }

    /**
     * Retrieves a read-only view of the event log.
     * @return An unmodifiable list of event strings.
     */
    public List<String> getEventLog() {
        return Collections.unmodifiableList(eventLog);
    }

    /**
     * Retrieves the template manager.
     * @return The TemplateManager instance.
     */
    public TemplateManager getTemplateManager() {
        return templateManager;
    }
}
