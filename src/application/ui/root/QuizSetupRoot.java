package application.ui.root;

import application.main.Main;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import application.util.QuizSettingsData;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Custom root node for the QuizSetupRoot scene.
 * @author Jacob Biewer
 */
public class QuizSetupRoot extends VBox {

    // references to components in layout
    private TopicSelectionBox topicSelectionBox;
    private NumOfQuestionsBox numOfQuestionsBox;

    /**
     * Constructs the node.
     */
    public QuizSetupRoot() {
        // INITIALIZE NODES //
        Label   title = new Label("Quiz Setup");
        this.topicSelectionBox = new TopicSelectionBox();
        this.numOfQuestionsBox = new NumOfQuestionsBox();
        HBox controlBtnBox = new ControlButtonBox();

        // FUNCTIONALITY //
        // none here...

        // SETUP LAYOUT AND STYLE //
        title.getStyleClass().add("header");
        this.getStyleClass().add("background");

        // center and space out all hbox elements
        Arrays.stream(new HBox[] { this.topicSelectionBox, this.numOfQuestionsBox, controlBtnBox })
                .forEach(box -> {
                    box.setAlignment(Pos.CENTER);
                    box.setSpacing(box instanceof ControlButtonBox ? 100 : 20);
                });

        this.getChildren().addAll(title, this.topicSelectionBox, this.numOfQuestionsBox, controlBtnBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);
    }

    /**
     * Custom root node to organize topic selection layout.
     */
    private class TopicSelectionBox extends HBox {
        private ListView<String> topicsList;

        /**
         * Constructs the node.
         */
        private TopicSelectionBox() {
            // INITIALIZE NODES //
            Label topicLabel = new Label("Select Topic(s):");
            this.topicsList = new ListView<>();

            // allow multiple items to be selected
            this.topicsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            // set the list of topics in alphabetical order
            this.topicsList.setItems(FXCollections.observableArrayList(
                    Arrays.stream(Main.questionBank.getAllTopics())
                            .sorted()
                            .collect(Collectors.toList())
            ));

            // allows the VBox to be scrolled through
            this.getChildren().addAll(topicLabel, this.topicsList);
        }

        /**
         * @return The topic selected in the combo box.
         */
        private String[] getTopics() {
            return this.topicsList.getSelectionModel().getSelectedItems().toArray(new String[0]);
        }
    }

    /**
     * Custom root node to organize the number of questions prompt layout.
     */
    private class NumOfQuestionsBox extends HBox {
        private TextField number; // reference to the field where the user enters the number

        /**
         * Constructs the root node.
         */
        private NumOfQuestionsBox() {
            // INITIALIZE NODES //
            Label numOfQuestions = new Label("Number of Questions:");
            this.number = new TextField();
            this.number.setPrefSize(50, 20);

            // FUNCTIONALITY //
            // none here...

            // SETUP LAYOUT AND STYLE //
            numOfQuestions.getStyleClass().add("main-text");
            this.number.getStyleClass().add("text-field");

            this.getChildren().addAll(numOfQuestions, this.number);

        }

        /**
         * @return The value in the 'number' text field.
         */
        private int getTotal() {
            String txt = this.number.getText();
            try {
                return Integer.parseInt(txt);
            } catch (NumberFormatException e) {

                return -1;
            }
        }
    }

    /**
     * Custom root node to organize the control buttons (cancel, begin) layout.
     */
    private class ControlButtonBox extends HBox {
        /**
         * Constructs the node.
         */
        private ControlButtonBox() {
            // INITIALIZE NODES //
            Button  cancel = new Button("Cancel"),
                    begin = new Button("Begin Quiz");

            // FUNCTIONALITY //
            cancel.setOnMouseClicked(event -> Main.switchScene(GUIScene.TITLE));
            begin.setOnMouseClicked(event -> {
                if (topicSelectionBox.topicsList.getItems().size() == 0) {
                    // when there aren't any questions in question bank
                    GUIAlert.quickAlert(Alert.AlertType.INFORMATION,
                            "No Questions",
                            "There are currently no questions loaded.\n" +
                                    "Either add custom questions or load JSON files in."
                    );
                    return;
                }
                int numOfQs = numOfQuestionsBox.getTotal();
                if (numOfQs == -1) { // check if an error occurred
                    // alert and don't switch scenes yet
                    GUIAlert.quickAlert(Alert.AlertType.WARNING,
                            "Wrong Input",
                            "Must input a valid integer for number of questions."
                    );
                    return;
                }

                // load quiz settings into manager
                Main.quizManager.loadQuiz(new QuizSettingsData(topicSelectionBox.getTopics(), numOfQs));
                Main.switchScene(GUIScene.QUESTION);
            });
            begin.setDefaultButton(true);

            // SETUP LAYOUT AND STYLE //
            this.getChildren().addAll(cancel, begin);
        }
    }
}
