package com.aios;

import android.os.Handler;

/**
 * A service that runs in the background to monitor the system
 * and provide periodic status updates.
 */
public class SystemMonitor {

    // A handler provided by the UI thread to receive messages.
    private final Handler uiHandler;
    private final AiOSCore core;
    private final long updateIntervalMillis;

    private boolean isRunning = false;
    // A handler to manage the timer on whatever thread this object is created on.
    private final Handler monitorHandler = new Handler();

    private final Runnable statusUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            // Stop running if the monitor has been stopped.
            if (!isRunning) {
                return;
            }

            // 1. Generate a simulated status update.
            String status = "System Status: OK. Log size: " + core.getEventLog().size() + " events.";

            // 2. Log this action to the AI's core memory.
            core.logEvent("SystemMonitor: Generated status update.");

            // 3. Create our custom Message object for the UI.
            Message messageForUi = new Message(status, Message.Sender.AI);

            // 4. Send the Message object to the UI thread through its handler.
            // We wrap our custom Message in the `obj` field of Android's Message.
            android.os.Message handlerMessage = uiHandler.obtainMessage(MainActivity.MESSAGE_ID_SYSTEM_MONITOR, messageForUi);
            uiHandler.sendMessage(handlerMessage);

            // 5. Schedule the next execution of this runnable.
            monitorHandler.postDelayed(this, updateIntervalMillis);
        }
    };

    public SystemMonitor(AiOSCore core, Handler uiHandler, long updateIntervalMillis) {
        this.core = core;
        this.uiHandler = uiHandler;
        this.updateIntervalMillis = updateIntervalMillis;
    }

    /**
     * Starts the system monitor. If already running, this does nothing.
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            core.logEvent("SystemMonitor started.");
            // Post the runnable to start the loop.
            monitorHandler.post(statusUpdateRunnable);
        }
    }

    /**
     * Stops the system monitor.
     */
    public void stop() {
        isRunning = false;
        core.logEvent("SystemMonitor stopped.");
        // Remove any pending posts of the runnable to prevent execution after stopping.
        monitorHandler.removeCallbacks(statusUpdateRunnable);
    }
}
