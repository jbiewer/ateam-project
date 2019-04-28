package application.ui.root;

import application.main.Main;
import application.ui.alerts.LastQuestionAlert;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Class that represents the root node for the Question Scene.
 * @author Emily Cebasek
 * @author Jacob Biewer
 */
public class QuestionRoot extends VBox {
    private QuestionRoot rootReference = this; // used as a reference when in inner classes

    // dynamic nodes that need to be referenced:
    private Label topicLabel, numLabel, questionLabel;
    private ImageView image;
    private ChoicesBox choiceBox;

    /**
     * Constructs the root node for the QuestionRoot scene.
     */
    public QuestionRoot() {
        // INITIALIZE NODES //
        this.topicLabel = new Label();
        this.numLabel = new Label();
        this.questionLabel = new Label();
        this.image = new ImageView();
        this.choiceBox = new ChoicesBox();
        BackNextBox backNextBox = new BackNextBox();

        // FUNCTIONALITY //
        // none here...

        // SETUP LAYOUT AND STYLE //
        this.topicLabel.getStyleClass().add("label-big");
        this.choiceBox.getStyleClass().add("choice-box");
        this.questionLabel.setWrapText(true);
        this.getChildren().addAll(
                this.topicLabel, this.numLabel, this.questionLabel,
                this.choiceBox, this.image, backNextBox
        );
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        // load data
        Main.quizManager.next(this);
    }

    /**
     * Custom root node to organize the list of choices for the question.
     */
    public class ChoicesBox extends VBox {
        private ToggleGroup group;

        private ChoicesBox() { this.group = new ToggleGroup(); }

        public String getChosen() {
            if(group.getSelectedToggle() == null) return null;
            else return ((RadioButton) group.getSelectedToggle()).getText();
        }

        /**
         * Lists each choice as a radio button onto the screen.
         * @param choices List of choices.
         */
        public void setChoices(String[] choices) {
            this.getChildren().removeAll(this.getChildren()); // update the children by removing all of them
            this.group = new ToggleGroup(); // allows only one button to be toggled on

            for (String choice : choices) { // add the new choices as children.
                RadioButton rb = new RadioButton(choice);
                rb.setWrapText(true);
                this.group.getToggles().add(rb); // add button to toggle group
            }

            // SETUP LAYOUT AND STYLE //
            // add toggles as VBox children
            this.group.getToggles().forEach(toggle -> this.getChildren().add((RadioButton) toggle));
            this.setSpacing(10);
            this.setAlignment(Pos.CENTER_LEFT);
        }

        /**
         * Loads the current question's chosen answer to the respective choice.
         */
        public void loadChoice(String choice) {
            for (Node child : choiceBox.getChildren()) {
                RadioButton rb = (RadioButton) child;
                if (rb.getText().equals(choice)) { // toggle answered choice
                    rb.fire();
                    return;
                }
            }
        }

    }

    /**
     * Custom root node to organize the 'back' and 'next' buttons.
     */
    private class BackNextBox extends HBox {
        /**
         * Constructs the node.
         */
        private BackNextBox() {
            // INITIALIZE NODES //
            Button  back = new Button("Back"),
                    next = new Button("Next");

            // FUNCTIONALITY //
            next.setOnMouseClicked(event -> {
                // check if current question was last question (next() returns false)
                if (!Main.quizManager.next(rootReference)) {
                    // check if all questions are answered
                    if (!Main.quizManager.allAnswered()) GUIAlert.NOT_ALL_ANSWERED.alert();

                    // ask user what to do after last question
                    Optional<ButtonType> decision = GUIAlert.LAST_QUESTION.alert();
                    if (decision.isPresent() && decision.get() == LastQuestionAlert.SEE_RESULTS)
                        Main.switchScene(GUIScene.RESULTS);
                }
            });
            back.setOnMouseClicked(event -> Main.quizManager.prev(rootReference));

            // SETUP LAYOUT AND STYLE //
            this.getChildren().addAll(back, next);
            this.getChildren().forEach(child -> child.getStyleClass().add("btn-large"));
            this.setAlignment(Pos.CENTER);
            this.setSpacing(100);
        }
    }

    /**
     * @return Reference to the 'topic' label.
     */
    public Label getTopicLabel() {
        return topicLabel;
    }

    /**
     * @return Reference to the 'num' label.
     */
    public Label getNumLabel() {
        return numLabel;
    }

    /**
     * @return Reference to the 'question' text area.
     */
    public Label getQuestionLabel() {
        return questionLabel;
    }

    /**
     * @return Reference to the image view.
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * @return Reference to the choice box.
     */
    public ChoicesBox getChoiceBox() {
        return choiceBox;
    }
}
