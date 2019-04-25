package application.ui.root;

import application.main.Main;
import application.util.Question;
import application.util.SettingsData;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

// Parent to be changed to whatever layout we want to use later.
public class QuestionRoot extends VBox {

    private class QuestionStack {
        private QuestionNode head;
        private QuestionNode tail;

        private void add(Question q) {
            QuestionNode node = new QuestionNode(q, tail, null); // reference new node
            if (head == null) head = tail = node; // if emtpy, set head and tail to new node
            else { // otherwise, add on to the top
                tail.next = node;
                tail = node;
            }
        }

        private Question pop() {
            if(head == null) return null; // if empty, return null

            Question toRtn = tail.data; // reference node to return

            // remove from top of stack
            tail = tail.prev;
            if(tail == null) head = null; // empty list if the last element was removed

            return toRtn;
        }
    }

    private class QuestionNode {
        private QuestionNode next;
        private Question data;
        private QuestionNode prev;

        private QuestionNode(Question data, QuestionNode prev, QuestionNode next) {
            this.next = next;
            this.data = data;
            this.prev = prev;
        }
    }

    private int qNum; // keeps track of the current question number
    private QuestionStack questionsToAsk, questionsAnswered; // queue of questions to ask and those already answered
    private Question curr;

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
        this.question = new Label("Uh oh. Something went wrong!");
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

    private class BackNextBox extends HBox {
        private BackNextBox() {
            // INITIALIZE NODES //
            Button  back = new Button("Back"),
                    next = new Button("Next");

            // FUNCTIONALITY //
            back.setOnMouseClicked(event -> {
                Question prevQuestion = questionsAnswered.pop();
                if (prevQuestion != null) {
                    setQuestion(prevQuestion);
                    questionsToAsk.add(prevQuestion);
                }
            });
            next.setOnMouseClicked(event -> {
                Question nextQuestion = questionsToAsk.pop();
                if (nextQuestion != null) {
                    setQuestion(nextQuestion);
                    questionsAnswered.add(nextQuestion);
                }
            });

            // SETUP LAYOUT //
            this.getChildren().addAll(back, next);
            this.getChildren().forEach(child -> child.getStyleClass().add("btn-large"));
            this.setSpacing(100);
        }

    }

    /**
     * Helper method to set a new question up on the window.
     * @param q Question to prompt the user.
     */
    private void setQuestion(Question q) {
        this.question.setText(q.getPrompt());
        this.image.setImage(new Image(q.getImageURI().toString()));
        this.choiceBox.setChoices(q.getChoices());
    }

    /**
     * Method to pass in data about the quiz from an external source.
     * @param data Data passed in.
     */
    protected void setData(SettingsData data) {
        Arrays.stream(Main.questionBank.getQuestionsOfTopic(data.getTopic()))
                .limit(data.getTotalQuestions())
                .forEach(q -> this.questionsToAsk.add(q));

        this.topic.setText("Questions about: " + data.getTopic());
        this.qCount.setText(this.qNum + " / " + data.getTotalQuestions());
    }
}
