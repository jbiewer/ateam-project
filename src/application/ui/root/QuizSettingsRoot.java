package application.ui.root;

import application.main.Main;
import application.ui.util.GUIScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * The root node class of the scene "New Question".
 * @author Jacob Biewer
 */
public class QuizSettingsRoot extends VBox {

    /**
     * Constructs layout from root node.
     */
    public QuizSettingsRoot() {
        // INITIALIZE NODES //
        Label   title = new Label("Quiz Setup");
        HBox    topicSelectionBox = new TopicSelectionBox(),
                numOfQuestionsBox = new NumOfQuestionsBox(),
                controlBtnBox = new ControlButtonBox();

        // center and space out all hbox elements
        Arrays.stream(new HBox[] { topicSelectionBox, numOfQuestionsBox, controlBtnBox })
                .forEach(box -> {
                    box.setAlignment(Pos.CENTER);
                    box.setSpacing(box instanceof ControlButtonBox ? 100 : 20);
                });

        // LAYOUT EACH ELEMENT /
        ObservableList<Node> children = FXCollections.observableArrayList(
                title, topicSelectionBox, numOfQuestionsBox, controlBtnBox
        );
        this.getChildren().addAll(children);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);

        // SET STYLE //
        title.getStyleClass().add("header");
    }

    /**
     * Custom HBox to hold the topic selection feature.
     */
    private class TopicSelectionBox extends HBox {
        private TopicSelectionBox() {
            // SETUP BOX //
            Label topic = new Label("Select Topic:");
            ComboBox<String> topics = new ComboBox<>();
            // todo implement list of topics integration
            this.getChildren().addAll(topic, topics);

            // SET STYLE //
            topic.getStyleClass().add("main-text");
            topics.getStyleClass().add("text-field");
        }
    }

    /**
     * Custom HBox to hold the number of questions prompt.
     */
    private class NumOfQuestionsBox extends HBox {
        private NumOfQuestionsBox() {
            // SETUP BOX //
            Label numOfQuestions = new Label("Number of Questions:");
            TextField number = new TextField();
            this.getChildren().addAll(numOfQuestions, number);

            // SET STYLE //
            numOfQuestions.getStyleClass().add("main-text");
            number.getStyleClass().add("text-field");
        }
    }

    /**
     * Custom HBox to hold the control buttons.
     */
    private class ControlButtonBox extends HBox{
        private ControlButtonBox() {
            // SETUP BOX //
            Button  cancel = new Button("Cancel"),
                    begin = new Button("Begin Quiz");

            // ADD FUNCTIONALITY //
            cancel.setOnMouseClicked(event -> Main.switchScene(GUIScene.TITLE));
            begin.setOnMouseClicked(event -> {
                // todo implement way to save fields to transfer to next scene
                Main.switchScene(GUIScene.QUESTION);
            });

            this.getChildren().addAll(cancel, begin);

            // SET STYLE //
            cancel.getStyleClass().add("btn-large");
            begin.getStyleClass().add("btn-large");
        }
    }

}
