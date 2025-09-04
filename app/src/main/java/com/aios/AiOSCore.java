package com.aios;

import android.os.Handler;
import com.aios.app_registry.AppRegistry;
import com.aios.templates.TemplateManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the core memory and state of the AiOS.
 * This class is intended to be a singleton or a centrally managed instance.
 */
public class AiOSCore {

    private final Handler uiHandler;
    private final List<String> eventLog;
    private final AppRegistry appRegistry;
    private final TemplateManager templateManager;

    public AiOSCore(Handler uiHandler) {
        this.uiHandler = uiHandler;
        this.eventLog = new ArrayList<>();
        this.appRegistry = new AppRegistry();
        this.templateManager = new TemplateManager();
        logEvent("AiOS Core Initialized.");
        logEvent("Scanning for existing apps...");
        this.appRegistry.scanForApps();
        logEvent("App scan complete. Found " + this.appRegistry.getAppList().size() + " apps.");
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
     * Retrieves the app registry.
     * @return The AppRegistry instance.
     */
    public AppRegistry getAppRegistry() {
        return appRegistry;
    }

    /**
     * Retrieves the template manager.
     * @return The TemplateManager instance.
     */
    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    /**
     * Sends a message to the UI thread to be displayed in the chat.
     * This is the safe way for background tasks or commands to update the UI.
     * @param message The message to send to the UI.
     */
    public void sendUIMessage(Message message) {
        // In a more complex app, we might use different message IDs for different purposes.
        android.os.Message handlerMessage = uiHandler.obtainMessage(MainActivity.MESSAGE_ID_SYSTEM_MONITOR, message);
        uiHandler.sendMessage(handlerMessage);
    }
}
