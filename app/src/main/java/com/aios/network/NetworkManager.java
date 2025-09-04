package com.aios.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages network operations for the AiOS.
 * All operations are performed on a background thread.
 */
public class NetworkManager {

    /**
     * A callback interface to handle the results of a network request.
     */
    public interface NetworkCallback {
        void onSuccess(String content);
        void onFailure(Exception e);
    }

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Fetches the string content of a given URL.
     * The result is delivered asynchronously to the provided callback.
     * @param urlString The URL to fetch.
     * @param callback The callback to handle success or failure.
     */
    public void fetchUrlContent(String urlString, NetworkCallback callback) {
        executorService.submit(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // Report success
                callback.onSuccess(content.toString());

            } catch (Exception e) {
                // Report failure
                callback.onFailure(e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }
}
