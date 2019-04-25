package application.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Data structure to manage all the questions.
 */
public class QuestionBank implements QuestionBankADT {

    private List<Question> questionBank;

    /**
     * Initializes field members
     */
    public QuestionBank() {
        this.questionBank = new ArrayList<>();
    }

    @Override
    public boolean addJSONQuestion(File... jsonFiles) {
//        try {
            for (File jsonFile : jsonFiles) {
                // todo implement
            }
//        } catch (IOException e) { e.printStackTrace(); }
//        finally {
//            return false;
//        }
        return false;
    }

    @Override
    public boolean writeQuestionsToJSON(File destination) {
        if (!destination.exists()) return false; // todo print message to user somehow of invalid destination
        // todo implement
        return false;
    }

    @Override
    public String[] getAllTopics() {
        return this.questionBank.stream()
                .map(Question::getTopic)
                .toArray(String[]::new);
    }

    @Override
    public Question[] getQuestionsOfTopic(String topic) {
        return this.questionBank.stream()
                .filter(question -> question.getTopic().equals(topic))
                .toArray(Question[]::new);
    }

    @Override
    public void addQuestion(Question question) {
        this.questionBank.add(question);
    }
}

