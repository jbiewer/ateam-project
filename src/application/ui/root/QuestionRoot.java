package application.ui.root;

import application.util.Question;
import application.util.SettingsData;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Map;

// Parent to be changed to whatever layout we want to use later.
public class QuestionRoot extends VBox {

    private int qNum; // keeps track of the current question number

    // class fields to reference labels
    private Label topic, qCount, question;
    private ImageView image;
    private ChoicesBox choiceBox;

    /**
     * Empty constructor that creates a QuestionRoot scene
     */
    public QuestionRoot() {
        // INITIALIZE NODES //
        this.topic = new Label("Questions about: ");
        this.qCount = new Label(this.qNum + " / ?");
        this.question = new Label("< question >");
        this.image = new ImageView();
        this.choiceBox = new ChoicesBox();

        // SETUP LAYOUT //
        this.getChildren().addAll(this.topic, this.qCount, this.question, this.image);
        this.getChildren().forEach(node -> this.setAlignment(Pos.CENTER));

        // ADD STYLE //
        this.topic.getStyleClass().add("main-text");
        this.qCount.getStyleClass().add("main-text");
    }

    /**
     * VBox to organize choices into radio buttons
     */
    private class ChoicesBox extends VBox {
        /**
         * Lists each choice as a radio button onto the screen.
         * @param choices List of choices.
         */
        private void setChoices(String[] choices) {
            this.getChildren().removeAll(this.getChildren());
            for (String choice : choices)
                this.getChildren().add(new RadioButton(choice));
        }
    }

    protected void setData(SettingsData data) {
        this.topic.setText("Questions about: " + data.getTopic());
        this.qCount.setText(this.qNum + " / " + data.getTotalQuestions());
    }

    public void setQuestion(Question q) {
        this.question.setText(q.getPrompt());
        this.image.setImage(new Image(q.getImageURI().toString()));
        this.choiceBox.setChoices(q.getChoices());
    }
}
