package com.example.seedbuy;

public class Notification {
    private String title;
    private String message;
    private long timestamp;

    public Notification(String title, String message, long timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTimeAgo() {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - timestamp;
        long minutes = timeDiff / (1000 * 60);

        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + " min ago";
        } else {
            long hours = minutes / 60;
            return hours + " hours ago";
        }
    }
}
