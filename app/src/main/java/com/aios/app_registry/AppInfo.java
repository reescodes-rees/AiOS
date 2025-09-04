package com.aios.app_registry;

import java.util.Date;

/**
 * A simple data model to hold information about a generated application.
 */
public class AppInfo {

    private final String appName;
    private final long creationDate;

    public AppInfo(String appName, long creationDate) {
        this.appName = appName;
        this.creationDate = creationDate;
    }

    public String getAppName() {
        return appName;
    }

    public long getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return appName + " (created on: " + new Date(creationDate) + ")";
    }
}
