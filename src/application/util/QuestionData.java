package application.util;

/**
 * Data structure to hold information about a question.
 */
public class QuestionData {

    private String topicText; // text to be assigned to their respective labels
    private int totalCount; // total number of questions
    private Question[] questions; // list of questions to be asked

    /**
     * Initializes the data.
     * @param topicText Topic to be displayed.
     * @param totalCount Total number of questions to be displayed.
     * @param questions The questions to be displayed.
     */
    public QuestionData(String topicText, int totalCount, Question[] questions) {
        this.topicText = topicText;
        this.totalCount = totalCount;
        this.questions = questions;
    }

    /**
     * @return The text of the topic.
     */
    public String getTopicText() {
        return topicText;
    }

    /**
     * @return The text for the total number of questions.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return A list of questions.
     */
    public Question[] getQuestions() {
        return questions;
    }
}
