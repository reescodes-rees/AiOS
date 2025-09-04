package com.aios.commands;

import com.aios.AiOSCore;
import com.aios.Message;
import com.aios.network.NetworkManager;
import java.util.List;

public class FetchCommand implements Command {

    @Override
    public String getName() {
        return "fetch";
    }

    @Override
    public String getDescription() {
        return "Fetches the content of a URL. Usage: fetch <url>";
    }

    @Override
    public String execute(List<String> args, AiOSCore core, CommandRegistry registry) {
        if (args.isEmpty()) {
            return "Error: URL is required. Usage: fetch <url>";
        }
        String urlString = args.get(0);

        core.logEvent("Executing 'fetch' command for URL: " + urlString);

        NetworkManager networkManager = new NetworkManager();
        networkManager.fetchUrlContent(urlString, new NetworkManager.NetworkCallback() {
            @Override
            public void onSuccess(String content) {
                // Limit the content length to avoid flooding the UI
                String truncatedContent = content.length() > 500 ? content.substring(0, 500) + "..." : content;
                Message resultMessage = new Message("Content from " + urlString + ":\n" + truncatedContent, Message.Sender.AI);
                core.sendUIMessage(resultMessage);
                core.logEvent("Fetch successful for " + urlString);
            }

            @Override
            public void onFailure(Exception e) {
                Message resultMessage = new Message("Failed to fetch " + urlString + ": " + e.getMessage(), Message.Sender.AI);
                core.sendUIMessage(resultMessage);
                core.logEvent("Fetch failed for " + urlString + ": " + e.getMessage());
            }
        });

        // Return an immediate message to the user
        return "Fetching content from " + urlString + "...";
    }
}
