package com.aios.commands;

import com.aios.AiOSCore;
import com.aios.utils.FileGenerator;
import java.util.List;

public class CreateAppCommand implements Command {

    private static final String GRADLE_TEMPLATE = "apply plugin: 'com.android.application'\\n\\n" +
            "android {\\n" +
            "    compileSdkVersion 33\\n" +
            "    defaultConfig {\\n" +
            "        applicationId \"com.aios.generated.__PACKAGE_NAME__\"\\n" +
            "        minSdkVersion 24\\n" +
            "        targetSdkVersion 33\\n" +
            "        versionCode 1\\n" +
            "        versionName \"1.0\"\\n" +
            "    }\\n" +
            "}\\n";

    private static final String MANIFEST_TEMPLATE = "<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>\\n" +
            "<manifest xmlns:android=\\\"http://schemas.android.com/apk/res/android\\\"\\n" +
            "    package=\\\"com.aios.generated.__PACKAGE_NAME__\\\">\\n\\n" +
            "    <application\\n" +
            "        android:allowBackup=\\\"true\\\"\\n" +
            "        android:label=\\\"__APP_NAME__\\\"\\n" +
            "        android:theme=\\\"@android:style/Theme.Material.Light\\\">\\n" +
            "        <activity android:name=\\\".MainActivity\\\" android:exported=\\\"true\\\">\\n" +
            "            <intent-filter>\\n" +
            "                <action android:name=\\\"android.intent.action.MAIN\\\" />\\n" +
            "                <category android:name=\\\"android.intent.category.LAUNCHER\\\" />\\n" +
            "            </intent-filter>\\n" +
            "        </activity>\\n" +
            "    </application>\\n\\n" +
            "</manifest>";

    private static final String ACTIVITY_TEMPLATE = "package com.aios.generated.__PACKAGE_NAME__;\\n\\n" +
            "import android.app.Activity;\\n" +
            "import android.os.Bundle;\\n" +
            "import android.widget.TextView;\\n\\n" +
            "public class MainActivity extends Activity {\\n" +
            "    @Override\\n" +
            "    protected void onCreate(Bundle savedInstanceState) {\\n" +
            "        super.onCreate(savedInstanceState);\\n" +
            "        TextView textView = new TextView(this);\\n" +
            "        textView.setText(\\\"Hello from __APP_NAME__!\\\");\\n" +
            "        setContentView(textView);\\n" +
            "    }\\n" +
            "}";

    @Override
    public String getName() {
        return "createapp";
    }

    @Override
    public String getDescription() {
        return "Creates a new 'Hello World' app. Usage: createapp <AppName>";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        core.logEvent("Executing 'createapp' command with args: " + args);

        if (args.isEmpty()) {
            return "Error: App name is required. Usage: createapp <AppName>";
        }

        String appName = args.get(0);
        // Basic sanitization for package name
        String packageName = appName.toLowerCase().replaceAll("[^a-z0-9_]", "");
        if (packageName.isEmpty()) {
            return "Error: Invalid app name. Please use alphanumeric characters.";
        }

        String baseDir = "generated_apps/" + appName;
        core.logEvent("Starting app generation for '" + appName + "' in " + baseDir);

        // 1. Create Gradle file
        String gradleContent = GRADLE_TEMPLATE.replace("__PACKAGE_NAME__", packageName);
        boolean gradleSuccess = FileGenerator.writeToFile(baseDir + "/build.gradle", gradleContent);
        if (gradleSuccess) {
            core.logEvent("Successfully generated build.gradle");
        } else {
            core.logEvent("Failed to generate build.gradle");
            return "Error: Could not write build.gradle file.";
        }

        // 2. Create Manifest file
        String manifestContent = MANIFEST_TEMPLATE.replace("__APP_NAME__", appName)
                .replace("__PACKAGE_NAME__", packageName);
        boolean manifestSuccess = FileGenerator.writeToFile(baseDir + "/src/main/AndroidManifest.xml", manifestContent);
        if (manifestSuccess) {
            core.logEvent("Successfully generated AndroidManifest.xml");
        } else {
            core.logEvent("Failed to generate AndroidManifest.xml");
            return "Error: Could not write AndroidManifest.xml file.";
        }

        // 3. Create MainActivity file
        String activityContent = ACTIVITY_TEMPLATE.replace("__APP_NAME__", appName)
                .replace("__PACKAGE_NAME__", packageName);
        String activityPath = baseDir + "/src/main/java/com/aios/generated/" + packageName + "/MainActivity.java";
        boolean activitySuccess = FileGenerator.writeToFile(activityPath, activityContent);
        if (activitySuccess) {
            core.logEvent("Successfully generated MainActivity.java");
        } else {
            core.logEvent("Failed to generate MainActivity.java");
            return "Error: Could not write MainActivity.java file.";
        }

        return "Successfully created app '" + appName + "' in directory '" + baseDir + "'.";
    }
}
