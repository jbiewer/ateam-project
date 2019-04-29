package application.util;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds all information about a specific question.
 *
 * Besides basic label texts like 'topic' and 'prompt', this class holds 'wrong' answers and one 'right' answer.
 * It also can hold 'chosen' answer which is answer being selected. This way, one can call 'isCorrect()' to determine
 * if the chosen answer is the right answer.
 */
public class Question {
    private String topic, prompt; // string literals for the topic and the question

    private String[] wrong; // list of wrong answers
    private String right, chosen; // correct answer and answer chosen

    private File imgFile; // file for the image relevant to the question

    /**
     * Initializes all the values in the question.
     * @param topic Topic of the question.
     * @param prompt Question prompted
     * @param right Correct answer.
     * @param wrong Wrong answer(s).
     */
    public Question(String topic, String prompt, String right, String[] wrong) {
        this(topic, prompt, right, wrong, null);
    }

    /**
     * Initializes all the values in the question.
     * @param topic Topic of the question.
     * @param prompt Question prompted
     * @param right Correct answer.
     * @param wrong Wrong answer(s).
     * @param imgFile File of the image to use.
     */
    public Question(String topic, String prompt, String right, String[] wrong, File imgFile) {
        this.topic = topic;
        this.prompt = prompt;
        this.wrong = wrong;
        this.right = right;
        this.imgFile = imgFile;
        this.chosen = null;
    }

    public void setChosen(String chosen) {
        this.chosen = chosen;
    }

    /**
     * @return The topic of the question.
     */
    public String getTopic() {
        return this.topic;
    }

    /**
     * @return The question prompt.
     */
    public String getPrompt() {
        return this.prompt;
    }

    /**
     * @return An array of choices.
     */
    public String[] getChoices() {
        ArrayList<String> choices = new ArrayList<>(Arrays.asList(this.wrong));
        choices.add(this.right);
        return choices.toArray(new String[0]);
    }

    /**
     * @return The chosen choice.
     */
    public String getChosen() {
        return chosen;
    }

    /**
     * @return True if the question is correct by comparing the answer chose and the correct answer.
     */
    public boolean isCorrect() {
        return this.right.equals(this.chosen);
    }

    /**
     * @return True if an answer was chosen ('chosen' is set).
     */
    public boolean isAnswered() {
        return this.chosen != null;
    }

    /**
     * @return The URI of the image.
     */
    public URI getImageURI() {
        if(this.imgFile == null) return null;
        return this.imgFile.toURI();
    }

    @Override
    public String toString() {
        return this.topic+":"+this.prompt;
    }
}
