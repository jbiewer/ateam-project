package application.util;

/**
 * Data structure to hold information about a question.
 */
public class QuestionData {

    private String topicText, totalCountText; // text to be assigned to their respective labels
    private Question[] questions; // list of questions to be asked

    /**
     * Initializes the data.
     * @param topicText Topic to be displayed.
     * @param qCountText Total number of questions to be displayed.
     * @param questions The questions to be displayed.
     */
    public QuestionData(String topicText, String qCountText, Question[] questions) {
        this.topicText = topicText;
        this.totalCountText = qCountText;
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
    public String getTotalCountText() {
        return totalCountText;
    }

    /**
     * @return A list of questions.
     */
    public Question[] getQuestions() {
        return questions;
    }
}
