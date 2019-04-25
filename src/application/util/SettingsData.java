package application.util;

/**
 * Data Structure used to store information about a quiz.
 * @author Jacob Biewer
 */
public class SettingsData {

    private String topic; // topic for the quiz
    private int totalQuestions; // how many questions the user wants to answer

    /**
     * Constructs the data structure.
     * @param topic Topic of the quiz.
     * @param total Total number of questions to ask.
     */
    public SettingsData(String topic, int total) {
        this.topic = topic;
        this.totalQuestions = total;
    }

    /**
     * @return Topic of the quiz.
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @return Total number of questions.
     */
    public int getTotalQuestions() {
        return totalQuestions;
    }
}
