package com.aios;

import com.aios.app_registry.AppRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the core memory and state of the AiOS.
 * This class is intended to be a singleton or a centrally managed instance.
 */
public class AiOSCore {

    private final List<String> eventLog;
    private final AppRegistry appRegistry;

    public AiOSCore() {
        this.eventLog = new ArrayList<>();
        this.appRegistry = new AppRegistry();
        logEvent("AiOS Core Initialized.");
        logEvent("Scanning for existing apps...");
        this.appRegistry.scanForApps();
        logEvent("App scan complete. Found " + this.appRegistry.getAppList().size() + " apps.");
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
     * Retrieves the app registry.
     * @return The AppRegistry instance.
     */
    public AppRegistry getAppRegistry() {
        return appRegistry;
    }
}
