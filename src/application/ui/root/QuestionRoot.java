package application.ui.root;

import application.util.SettingsData;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


import java.util.Map;

// Parent to be changed to whatever layout we want to use later.
public class QuestionRoot extends BorderPane {

    private int qNum; // keeps track of the current question number
    private Label topic, qCount; // class fields to reference labels
    private Button next;

    /**
     * Empty constructor that creates a QuestionRoot scene
     */
    public QuestionRoot() {
        this.topic = new Label("Questions about: ");
        this.qCount = new Label(this.qNum + " / ?");

        //Add styling
        this.getStyleClass().add("background");
        this.topic.getStyleClass().add("header");
        this.qCount.getStyleClass().add("sub-header");

        // Creates mock data
        SettingsData sample = new SettingsData("Harry Potter Trivia", 10);
        setData(sample);

        //Add items to scene
        this.setTop(topic);
        this.setCenter(qCount);
        this.getChildren().forEach(node -> BorderPane.setAlignment(node, Pos.CENTER));
    }

    public void setData(SettingsData data) {
        this.topic.setText("Questions about: " + data.getTopic());
        this.qCount.setText(this.qNum + " / " + data.getTotalQuestions());
    }
}
