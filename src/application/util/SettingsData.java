package application.util;

import javafx.scene.image.Image;

public class SettingsData {

    private String topic; // topic for the quiz
    private int totalQuestions; // how many questions the user wants to answer

    public SettingsData(String topic, int total) {
        this.topic = topic;
        this.totalQuestions = total;
    }

    public String getTopic() {
        return topic;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
}
