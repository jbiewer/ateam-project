package application.util;

import application.main.Main;
import application.ui.root.QuestionRoot;
import javafx.scene.image.Image;

import java.util.Iterator;

/**
 * Holds all the data for a quiz. The "backend" part of the QuestionRoot scene.
 * This is basically a representation of a quiz. It holds all the following:
 *      - What questions will be asked
 *      - What questions are answered and haven't been answered
 *      - Functionality to change a QuestionRoot's display to a certain question
 */
public class QuizManager {

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

    private QuestionStack nextQuestions, prevQuestions; // stacks to keep track of questions
    private Question currQuestion; // current question being asked

    private int questionNum; // count of current question
    private int questionTotal; // total number of questions

    /**
     * Initializes stacks.
     */
    public QuizManager() {
        this.nextQuestions = new QuestionStack();
        this.prevQuestions = new QuestionStack();
    }

    /**
     * Loads a new quiz into the QuizManager.
     * @param data Settings of the quiz to load.
     */
    public void loadQuiz(QuizSettingsData data) {
        // read from question bank based on quiz settings

        // start by getting questions to quiz on
        Question[] questions = (data.getTopic().isEmpty() ?
                Main.questionBank.getAllQuestions() : Main.questionBank.getQuestionsOfTopic(data.getTopic())
        );

        // queue the questions up in the 'next' stack
        for (Question question : questions)
            this.nextQuestions.add(question);

        // initialize total questions variable
        this.questionTotal = data.getTotalQuestions();
    }

    /**
     * Displays the next question to the QuestionRoot node.
     * @param root QuestionRoot to display to.
     * @return True if there is a next question to display, false if there aren't anymore.
     */
    public boolean next(QuestionRoot root) {
        if (this.currQuestion != null)
            this.currQuestion.setChosen(root.getChoiceBox().getChosen()); // set chosen answer to question

        // change questions
        this.prevQuestions.add(this.currQuestion);
        this.currQuestion = this.nextQuestions.pop();
        this.questionNum++;

        if (this.currQuestion == null) return false; // when there are no more questions
        this.nextPrevUISetup(root);
        return true;
    }

    /**
     * Displays the previous question to the QuestionRoot node.
     * @param root QuestionRoot to display to.
     */
    public void prev(QuestionRoot root) {
        if (this.currQuestion != null)
            this.currQuestion.setChosen(root.getChoiceBox().getChosen()); // set chosen answer to question

        // change question
        this.nextQuestions.add(this.currQuestion);
        this.currQuestion = this.prevQuestions.pop();
        this.questionNum--;

        if (this.currQuestion != null)
            this.nextPrevUISetup(root);
    }

    /**
     * Helper method to setup the UI elements with the current question's data.
     * @param root QuestionRoot to display to.
     */
    private void nextPrevUISetup(QuestionRoot root) {
        root.getTopicLabel().setText(currQuestion.getTopic());
        root.getNumLabel().setText(this.questionNum + " / " + this.questionTotal);
        root.getQuestionLabel().setText(this.currQuestion.getPrompt());
        if(this.currQuestion.getImageURI() != null)
            root.getImage().setImage(new Image(this.currQuestion.getImageURI().toString()));
        root.getChoiceBox().setChoices(currQuestion.getChoices());
        root.getChoiceBox().loadChoice(this.currQuestion.getChosen());
    }

    /**
     * @return True if all the questions in the quiz are answered.
     */
    public boolean allAnswered() {
        for (Question prevQuestion : this.prevQuestions) { // check previous
            if (prevQuestion == null) break;
            if (!prevQuestion.isAnswered()) return false;
        }

        if (this.currQuestion != null && !this.currQuestion.isAnswered()) return false; // check current

        for (Question nextQuestion : this.nextQuestions) { // check next
            if (nextQuestion == null) break;
            if (!nextQuestion.isAnswered()) return false;
        }

        return true;
    }

}
