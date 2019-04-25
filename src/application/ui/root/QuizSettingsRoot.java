package application.ui.root;

import application.main.Main;
import application.ui.util.GUIScene;
import application.util.SettingsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * The root node class of the scene "New Question".
 * @author Jacob Biewer
 */
public class QuizSettingsRoot extends VBox {

    private TopicSelectionBox topicSelectionBox;
    private NumOfQuestionsBox numOfQuestionsBox;

    /**
     * Constructs layout from root node.
     */
    public QuizSettingsRoot() {
        // INITIALIZE NODES //
        Label   title = new Label("Quiz Setup");
        this.topicSelectionBox = new TopicSelectionBox();
        this.numOfQuestionsBox = new NumOfQuestionsBox();
        HBox controlBtnBox = new ControlButtonBox();

        // center and space out all hbox elements
        Arrays.stream(new HBox[] { this.topicSelectionBox, this.numOfQuestionsBox, controlBtnBox })
                .forEach(box -> {
                    box.setAlignment(Pos.CENTER);
                    box.setSpacing(box instanceof ControlButtonBox ? 100 : 20);
                });

        // LAYOUT EACH ELEMENT /
        ObservableList<Node> children = FXCollections.observableArrayList(
                title, this.topicSelectionBox, this.numOfQuestionsBox, controlBtnBox
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
        private ComboBox<String> topics;

        private TopicSelectionBox() {
            // SETUP BOX //
            Label topic = new Label("Select Topic:");
            this.topics = new ComboBox<>();
            this.topics.getItems().addAll(Main.questionBank.getAllTopics());
            this.getChildren().addAll(topic, this.topics);

            // SET STYLE //
            topic.getStyleClass().add("main-text");
            this.topics.getStyleClass().add("text-field");
        }

        private String getTopic() {
            return this.topics.getValue();
        }
    }

    /**
     * Custom HBox to hold the number of questions prompt.
     */
    private class NumOfQuestionsBox extends HBox {
        private TextField number;

        private NumOfQuestionsBox() {
            // SETUP BOX //
            Label numOfQuestions = new Label("Number of Questions:");
            this.number= new TextField();
            this.getChildren().addAll(numOfQuestions, this.number);

            // SET STYLE //
            numOfQuestions.getStyleClass().add("main-text");
            this.number.getStyleClass().add("text-field");
        }

        private int getTotal() {
            String txt = this.number.getText();
            try {
                return Integer.parseInt(txt);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input format incorrect!");
                alert.setHeaderText("'Number of Questions' must be an integer.");
                alert.showAndWait();
                return -1;
            }
        }
    }

    /**
     * Custom HBox to hold the control buttons.
     */
    private class ControlButtonBox extends HBox {
        private ControlButtonBox() {
            // SETUP BOX //
            Button  cancel = new Button("Cancel"),
                    begin = new Button("Begin Quiz");

            // ADD FUNCTIONALITY //
            cancel.setOnMouseClicked(event -> Main.switchScene(GUIScene.TITLE));
            begin.setOnMouseClicked(event -> {
                int numOfQs = numOfQuestionsBox.getTotal();
                if (numOfQs == -1) return; // don't switch scenes if an error occurs
                ((QuestionRoot) GUIScene.QUESTION.getScene().getRoot()).setData(
                        new SettingsData(topicSelectionBox.getTopic(), numOfQs)
                );

                Main.switchScene(GUIScene.QUESTION);
            });

            this.getChildren().addAll(cancel, begin);

            // SET STYLE //
            cancel.getStyleClass().add("btn-large");
            begin.getStyleClass().add("btn-large");
        }
    }

    /**
     * Reloads topics in case some have been added.
     */
    protected void reloadTopics() {
        this.topicSelectionBox.topics.setItems(FXCollections.observableArrayList(
                Main.questionBank.getAllTopics()
        ));
    }

}
