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
    private class QuestionStack implements Iterable<Question> {
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
            if (tail == head) head = tail = null; // emtpy stack if last element is being popped
            else {
                // remove from top of stack
                tail = tail.prev;
                tail.next = null;
            }

            return toRtn;
        }

        /**
         * Gets the Question at the top of the stack w/out removing it.
         * @return Question at the top of the stack.
         */
        private Question peek() {
            if (tail == null) return null;
            return tail.data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (Question question : this) {
                sb.append(question).append(" ");
            }
            sb.append("]");
            return sb.toString();
        }

        @Override
        public Iterator<Question> iterator() {
            return new Iterator<Question>() {
                QuestionNode current = head;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public Question next() {
                    Question data = current.data;
                    current = current.next;
                    return data;
                }
            };
        }
    }

    private QuestionStack nextQuestions, prevQuestions; // stacks to keep track of questions
    private Question firstQuestion; // reference to first question

    private int questionNum; // count of current question
    private int questionTotal; // total number of questions

    /**
     * Initializes stacks.
     */
    public QuizManager() {
        this.nextQuestions = new QuestionStack();
        this.prevQuestions = new QuestionStack();
        this.questionNum = 0;
    }

    /**
     * Loads a new quiz into the QuizManager.
     * @param data Settings of the quiz to load.
     */
    public void loadQuiz(QuizSettingsData data) {
        // read from question bank based on quiz settings

        // start by getting questions to quiz on
        Question[] questions = (data.getTopic() == null ?
                Main.questionBank.getAllQuestions() : Main.questionBank.getQuestionsOfTopic(data.getTopic())
        );

        // initialize total questions variable
        this.questionTotal = (data.getTotalQuestions() > questions.length ?
                questions.length : data.getTotalQuestions());

        // queue the questions up in the 'next' stack
        int limit = this.questionTotal;
        for (Question question : questions) {
            if (--limit < 0) break; // limit questions to the num requested by user
            this.nextQuestions.add(question);
        }
    }

    /**
     * Displays the next question to the QuestionRoot node.
     * @param root QuestionRoot to display to.
     * @return True if there is a next question to display, false if there aren't anymore.
     */
    public boolean next(QuestionRoot root) {
        // setup reference to first question
        if (this.firstQuestion == null) this.firstQuestion = this.nextQuestions.peek();

        // save chosen answer
        if (this.prevQuestions.peek() != null)
                this.prevQuestions.peek().setChosen(root.getChoiceBox().getChosen());

        // move 'next' questions into 'prev' questions
        if (this.nextQuestions.peek() != null) {
            // move next to prev
            Question prev = this.nextQuestions.pop();
            this.prevQuestions.add(prev);
            this.questionNum++;
            // setup UI
            this.nextPrevUISetup(root, this.prevQuestions.peek());
            return true;
        }
        return false;
    }

    /**
     * Displays the previous question to the QuestionRoot node.
     * @param root QuestionRoot to display to.
     */
    public void prev(QuestionRoot root) {
        // save chosen answer
        if (this.prevQuestions.peek() != null)
            this.prevQuestions.peek().setChosen(root.getChoiceBox().getChosen());

        // move 'prev' questions into 'next' questions
        if (this.prevQuestions.peek() != this.firstQuestion) {
            // move prev to next
            Question next = this.prevQuestions.pop();
            this.nextQuestions.add(next);
            this.questionNum--;
            // setup UI
            this.nextPrevUISetup(root, this.prevQuestions.peek());
        }
    }

    /**
     * Helper method to setup the UI elements with the current question's data.
     * @param root QuestionRoot to display to.
     */
    private void nextPrevUISetup(QuestionRoot root, Question curr) {
        root.getTopicLabel().setText(curr.getTopic());
        root.getNumLabel().setText(this.questionNum + " / " + this.questionTotal);
        root.getQuestionLabel().setText(curr.getPrompt());
        if(curr.getImageURI() != null)
            root.getImage().setImage(new Image(curr.getImageURI().toString()));
        root.getChoiceBox().setChoices(curr.getChoices());
        root.getChoiceBox().loadChoice(curr.getChosen());
    }

    /**
     * @return True if all the questions in the quiz are answered.
     */
    public boolean allAnswered() {
        for (Question prevQuestion : this.prevQuestions) { // check previous
            if (prevQuestion == null) break;
            if (!prevQuestion.isAnswered()) return false;
        }

        for (Question nextQuestion : this.nextQuestions) { // check next
            if (nextQuestion == null) break;
            if (!nextQuestion.isAnswered()) return false;
        }

        return true;
    }

}
