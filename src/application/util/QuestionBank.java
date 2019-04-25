package application.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Data structure to manage all the questions.
 *
 */
public class QuestionBank implements QuestionBankADT {

    private List<Question> questionBank;

    public QuestionBank() {
        this(null);
    }

    public QuestionBank(File jsonFile) {
        this.questionBank = new ArrayList<>();
        this.readFromJSON(jsonFile);
    }

    @Override
    public boolean readFromJSON(File jsonFile) {
        if (jsonFile == null) return false;
        // todo implement
        return false;
    }

    @Override
    public boolean writeToJSON(File destination) {
        if (!destination.exists()) return false; // todo maybe print message to user?
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

