package application.ui.root;

import application.util.Question;
import application.util.SettingsData;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Map;

public class QuestionRoot extends BorderPane {

    private int qNum; // keeps track of the current question number
    private Label topic, qCount; // class fields to reference labels
    private Button next;
    private Label question;
    private ImageView image;
    private ChoicesBox choiceBox;

    /**
     * Empty constructor that creates a QuestionRoot scene
     */
    public QuestionRoot(){
        // Initializes Nodes
        this.topic = new Label("Questions about: ");
        this.qCount = new Label(this.qNum + " / ?");
        this.next = new Button();
        this.question = new Label("< question >");
        this.image = new ImageView();
        this.choiceBox = new ChoicesBox();
        this.getChildren().addAll(this.topic, this.qCount, this.question, this.image);



        //Add styling
        this.getStyleClass().add("background");
        this.topic.getStyleClass().add("header");
        this.qCount.getStyleClass().add("sub-header");
        this.next.getStyleClass().add("btn-large");

        // Creates mock data
        SettingsData sample = new SettingsData("Harry Potter Trivia", 10);
        setData(sample);

        //Add items to scene
        this.setTop(topic);
        this.setCenter(qCount);
        this.setBottom(next);
        //this.getChildren().forEach(node -> this.setAlignment(node, Pos.CENTER));



        // SETUP LAYOUT //
        // this.getChildren().addAll(this.topic, this.qCount, this.question, this.image);
        //this.getChildren().forEach(node -> this.setAlignment(Pos.CENTER));

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
