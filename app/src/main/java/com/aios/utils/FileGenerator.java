package com.aios.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility class for file-related operations.
 */
public final class FileGenerator {

    // Private constructor to prevent instantiation of this utility class.
    private FileGenerator() {}

    /**
     * Writes the given content to a file at the specified path.
     * This method will create parent directories if they do not exist.
     *
     * @param filePath The full path of the file to write to.
     * @param content  The string content to write to the file.
     * @return true if the file was written successfully, false otherwise.
     */
    public static boolean writeToFile(String filePath, String content) {
        try {
            File file = new File(filePath);

            // Ensure parent directories exist.
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    // In a real app, use Log.e for error logging.
                    System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
                    return false;
                }
            }

            // Write the file content using try-with-resources for automatic closing.
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
            return true;
        } catch (IOException e) {
            // In a real app, use Log.e for error logging.
            e.printStackTrace();
            return false;
        }
    }
}
