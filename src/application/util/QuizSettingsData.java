package application.util;

/**
 * Data Structure used to store information about a quiz.
 * @author Jacob Biewer
 */
public class QuizSettingsData {

    private String[] topics; // topic for the quiz
    private int totalQuestions; // how many questions the user wants to answer

    /**
     * Constructs the data structure.
     * @param topics Topic of the quiz.
     * @param total Total number of questions to ask.
     */
    public QuizSettingsData(String[] topics, int total) {
        this.topics = topics;
        this.totalQuestions = total;
    }

    /**
     * @return Topic of the quiz.
     */
    public String[] getTopics() {
        return this.topics;
    }

    /**
     * @return Total number of questions.
     */
    public int getTotalQuestions() {
        return totalQuestions;
    }
}
