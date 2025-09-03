package com.aios;

public class AiService {

    public Message getResponse(String userInput) {
        String command = userInput.toLowerCase().trim();
        String aiResponseText;

        if (command.equals("help")) {
            aiResponseText = "Available commands:\n- help: Show this message\n- create app: (Coming soon) Start the app creation process";
        } else if (command.equals("create app")) {
            aiResponseText = "I understand you want to create an app. This feature is coming soon!";
        } else {
            aiResponseText = "I don't understand that command. Type 'help' for a list of commands.";
        }

        return new Message(aiResponseText, Message.Sender.AI);
    }
}
