package com.aios;

public class AiService {

    public Message getResponse(String userInput) {
        String aiResponseText = "AI response to: \"" + userInput + "\"";
        return new Message(aiResponseText, Message.Sender.AI);
    }
}
