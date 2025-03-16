package com.example.demochat2.models;

public class Message {
    private String id;
    private String type;
    private String from;
    private Text text;
    private long timestamp;
    // Constructor, getters y setters

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public Text getText() {
        return text;
    }

    public long getTimestamp() {  // Getter para timestamp
        return timestamp;
    }

    public static class Text {
        private String body;

        // Constructor, getters y setters

        public String getBody() {
            return body;
        }
    }
}