package com.aios;

public class Message {

    public enum Sender {
        USER, AI
    }

    private final String text;
    private final Sender sender;

    public Message(String text, Sender sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public Sender getSender() {
        return sender;
    }
}
