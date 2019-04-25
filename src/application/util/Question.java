package application.util;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URI;

public class Question {
    private String topic, prompt;
    private String[] choices;
    private File imgFile;

    public Question(String topic, String prompt, String[] choices) {
        this(topic, prompt, choices, null);
    }

    public Question(String topic, String prompt, String[] choices, File imgFile) {
        this.topic = topic;
        this.prompt = prompt;
        this.choices = choices;
        this.imgFile = imgFile;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public String[] getChoices() {
        return this.choices;
    }

    public URI getImageURI() {
        if(this.imgFile == null) return null;
        return this.imgFile.toURI();
    }
}
