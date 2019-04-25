package application.ui.root;

import application.main.Main;
import application.util.Question;
import application.util.QuestionData;
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

/**
 * Class that represents the root node for the Question Scene.
 * @author Emily Cebasek
 * @author Jacob Biewer
 */
public class QuestionRoot extends VBox {

    /**
     * Question Stack is used to add questions to stack for later use.
     * It functions in a similar way to undoing/redoing works:
     *
     *   When the user wants to move onto the next question, add it to the "answered" stack, and
     *   remove it from the "unanswered" stack.
     *
     *   Conversely, when the user wants to go back to another question, add it to the "unanswered" stack,
     *   and remove it from the "answered" stack.
     *
     * The stack is implemented using a doubly linked-list.
     */
    private class QuestionStack {
        /**
         * Utility class used with the Question Stack class.
         */
        private class QuestionNode {
            private QuestionNode next;
            private Question data;
            private QuestionNode prev;

            /**
             * QuestionNode constructor.
             * @param data Question
             * @param prev Link to previous node.
             * @param next Link to next node.
             */
            private QuestionNode(Question data, QuestionNode prev, QuestionNode next) {
                this.next = next;
                this.data = data;
                this.prev = prev;
            }
        }

        private QuestionNode head;
        private QuestionNode tail;
        private int size;

        /**
         * Add a question to the top of the stack.
         * @param q Question to add.
         */
        private void add(Question q) {
            QuestionNode node = new QuestionNode(q, tail, null); // reference new node
            if (head == null) head = tail = node; // if emtpy, set head and tail to new node
            else { // otherwise, add on to the top
                tail.next = node;
                tail = node;
            }
            size++;
        }

        /**
         * Remove a question from the top of the stack.
         * @return The question at the top.
         */
        private Question pop() {
            if(head == null) return null; // if empty, return null

            Question toRtn = tail.data; // reference node to return

            // remove from top of stack
            tail = tail.prev;
            if(tail == null) head = null; // empty list if the last element was removed
            size--;

            return toRtn;
        }
    }


    private static QuestionData currQData; // holds information about the current question

    private int qNum; // keeps track of the current question number
    // stacks of questions to ask and those already answered
    private QuestionStack questionsToAsk = new QuestionStack(),
            questionsAnswered = new QuestionStack();

    // class fields to reference labels
    private Label topic, qCount, question;
    private ImageView image;
    private ChoicesBox choiceBox;


    /**
     * Constructs the root node for the QuestionRoot scene.
     */
    public QuestionRoot() {
        // INITIALIZE NODES //
        this.topic = new Label("Questions about: ");
        this.qCount = new Label(this.qNum + " / ?");
        this.question = new Label("Uh oh. Something went wrong!");
        this.image = new ImageView();
        this.choiceBox = new ChoicesBox();
        BackNextBox backNextBox = new BackNextBox();
        String[] choices = {"Choice 1", "Choice 2", "Choice 3"};
        choiceBox.setChoices(choices);

        // FUNCTIONALITY //
        // none here...

        // SETUP LAYOUT //
        this.getChildren().addAll(this.topic, this.qCount, this.question, this.choiceBox, this.image, backNextBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        // load first question and data after setup
        if(currQData != null) this.loadData(currQData);
    }

    /**
     * Custom root node to organize the list of choices for the question.
     */
    private class ChoicesBox extends VBox {
        /**
         * Lists each choice as a radio button onto the screen.
         * @param choices List of choices.
         */
        private void setChoices(String[] choices) {
            this.getChildren().removeAll(this.getChildren()); // update the children by removing all of them
            for (String choice : choices) // add the new choices as children.
                this.getChildren().add(new RadioButton(choice));

            // SETUP LAYOUT AND STYLE //
            this.setSpacing(10);
            this.setAlignment(Pos.CENTER_LEFT);
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
            back.setOnMouseClicked(event -> {
                Question prevQuestion = questionsAnswered.pop();
                if (prevQuestion != null) {
                    setQuestion(prevQuestion);
                    questionsToAsk.add(prevQuestion);
                    qNum--;
                }
            });
            next.setOnMouseClicked(event -> {
                Question nextQuestion = questionsToAsk.pop();
                if (nextQuestion != null) {
                    setQuestion(nextQuestion);
                    questionsAnswered.add(nextQuestion);
                    qNum++;
                }
            });

            // SETUP LAYOUT AND STYLE //
            this.getChildren().addAll(back, next);
            this.getChildren().forEach(child -> child.getStyleClass().add("btn-large"));
            this.setAlignment(Pos.CENTER);
            this.setSpacing(100);
        }
    }


    /**
     * Method to pass in data about the quiz from an external source.
     * @param data Data passed in.
     */
    protected static void saveData(SettingsData data) {
        // get viable questions to ask
        Question[] questions = Arrays.stream(Main.questionBank.getQuestionsOfTopic(data.getTopic()))
                .limit(data.getTotalQuestions())
                .toArray(Question[]::new);

        // setup label texts
        String  topicText = "Questions about: " + data.getTopic(),
                totalCountText = (data.getTotalQuestions() > questions.length ?
                        questions.length : data.getTotalQuestions())+"";

        // reference them as a QuestionData data structure
        currQData = new QuestionData(topicText, totalCountText, questions);
    }

    /**
     * Takes in data about a question and loads it into the scene.
     * @param data Data to load.
     */
    private void loadData(QuestionData data) {
        // queue up questions to ask
        for (Question q : data.getQuestions())
            this.questionsToAsk.add(q);

        // prompt first question
        Question nextQuestion = questionsToAsk.pop();
        if (nextQuestion != null) {
            setQuestion(nextQuestion);
            questionsAnswered.add(nextQuestion);
            qNum++;
        }

        // set labels
        this.topic.setText(data.getTopicText());
        this.qCount.setText(data.getTotalCountText());
    }

    /**
     * Helper method to set a new question up on the window.
     * Accessed by the loadData() method and by event handlers for next/back buttons.
     * @param q Question to prompt the user.
     */
    private void setQuestion(Question q) {
        this.question.setText(q.getPrompt()); // set question prompt text
        // if there's an image, display it
        if (q.getImageURI() != null) this.image.setImage(new Image(q.getImageURI().toString()));
        this.choiceBox.setChoices(q.getChoices()); // setup choices for user
    }
}
