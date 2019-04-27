package application.ui.root;

import application.main.Main;
import application.ui.util.GUIScene;
import application.util.Question;
import application.util.QuestionData;
import application.util.SettingsData;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

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
    private class QuestionStack implements Iterator<Question>, Iterable<Question> {
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

        private QuestionNode curr = this.head; // used for iterator.

        @Override
        public Question next() {
            Question data = curr.data;
            curr = curr.next;
            return data;
        }

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Iterator<Question> iterator() {
            return this;
        }
    }


    private static QuestionData currQuestionData; // holds information about the current question
    // used in case of errors in loading the first question
    private static final Question EMPTY_QUESTION = new Question("Error!", "Question could not be loaded.",
            "Please let the developers know about this!", new String[0]);

    private int qNum; // keeps track of the current question number
    private final int TOTAL; // how many total questions there are.
    // stacks of questions to ask and those already answered
    private QuestionStack questionsToAsk = new QuestionStack(),
            questionsAnswered = new QuestionStack();
    private Question currentQuestion; // question buffer to avoid having clicking 'back'/'next' twice

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
        this.question = new Label();
        this.image = new ImageView();
        this.choiceBox = new ChoicesBox();
        BackNextBox backNextBox = new BackNextBox();

        // FUNCTIONALITY //
        // none here...

        // SETUP LAYOUT AND STYLE //
        this.topic.getStyleClass().add("label-big");
        this.choiceBox.getStyleClass().add("choice-box");
        this.getChildren().addAll(this.topic, this.qCount, this.question, this.choiceBox, this.image, backNextBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        // load data
        if(currQuestionData != null) this.TOTAL = this.loadData(currQuestionData);
        else this.TOTAL = 0;

        // prompt first question
        Question firstQuestion = this.questionsToAsk.pop();
        if (firstQuestion != null) {
            this.currentQuestion = firstQuestion;
            this.qNum++;
            setQuestion(firstQuestion);
        } else {
            setQuestion(EMPTY_QUESTION);
        }
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
            ToggleGroup group = new ToggleGroup(); // allows only one button to be toggled on

            for (String choice : choices) { // add the new choices as children.
                RadioButton rb = new RadioButton(choice);
                rb.setOnMouseClicked(event -> currentQuestion.setChosen(choice));
                group.getToggles().add(rb); // add button to toggle group
            }

            // SETUP LAYOUT AND STYLE //
            group.getToggles().forEach(toggle -> this.getChildren().add((RadioButton) toggle));
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
            next.setOnMouseClicked(event -> {
                // make the current question the next question to ask if there is any more to ask
                Question nextQuestion = questionsToAsk.pop();
                if (nextQuestion != null) {
                    // change to next question
                    questionsAnswered.add(currentQuestion);
                    qNum++;
                    setQuestion(nextQuestion);

                    this.loadChoice(); // load current choice
                } else { // if there aren't anymore to ask, do this:
                    // check if all questions are answered (required)
                    for (Question q : questionsAnswered) {
                        if (!q.isAnswered()) {
                            new Alert(Alert.AlertType.WARNING, "Not all questions are answered!")
                                    .showAndWait();
                            return; // return to quiz if questions left unanswered
                        }
                    }

                    // ask user what to do after last question
                    Optional<ButtonType> decision = new LastQuestionAlert().showAndWait();
                    if (decision.isPresent() && decision.get() == LastQuestionAlert.SEE_RESULTS)
                        Main.switchScene(GUIScene.RESULTS); // go to results of chosen to
                }
            });

            back.setOnMouseClicked(event -> {
                // make the current question the previous question if there are any previous questions
                Question prevQuestion = questionsAnswered.pop();
                if (prevQuestion != null) {
                    // change to previous question
                    questionsToAsk.add(currentQuestion);
                    qNum--;
                    setQuestion(prevQuestion);

                    this.loadChoice(); // load current choice
                }
            });

            // SETUP LAYOUT AND STYLE //
            this.getChildren().addAll(back, next);
            this.getChildren().forEach(child -> child.getStyleClass().add("btn-large"));
            this.setAlignment(Pos.CENTER);
            this.setSpacing(100);
        }

        /**
         * Loads the current question's chosen answer to the respective choice.
         */
        private void loadChoice() {
            choiceBox.getChildren().forEach(child -> {
                RadioButton currChild = (RadioButton) child;
                if (currChild.getText().equals(currentQuestion.getChosen()))
                    currChild.fire();
            });
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
        String  topicText = "Questions about: " + data.getTopic();
        int totalCount = (data.getTotalQuestions() > questions.length ?
                        questions.length : data.getTotalQuestions());

        // reference them as a QuestionData data structure
        currQuestionData = new QuestionData(topicText, totalCount, questions);
    }

    /**
     * Takes in data about a question and loads it into the scene.
     * @param data Data to load.
     * @return The total number of questions the data has.
     */
    private int loadData(QuestionData data) {
        // queue up questions to ask
        for (Question q : data.getQuestions())
            this.questionsToAsk.add(q);
        this.topic.setText(data.getTopicText());
        return data.getTotalCount();
    }

    /**
     * Helper method to set a new question up on the window.
     * Accessed by the loadData() method and by event handlers for next/back buttons.
     * @param q Question to prompt the user.
     */
    private void setQuestion(Question q) {
        this.question.setText(q.getPrompt()); // set question prompt text
        this.qCount.setText(this.qNum + " / " + this.TOTAL); // set question count text

        // if there's an image, display it
        if (q.getImageURI() != null) this.image.setImage(new Image(q.getImageURI().toString()));
        this.choiceBox.setChoices(q.getChoices()); // setup choices for user.

        this.currentQuestion = q;
    }
}
