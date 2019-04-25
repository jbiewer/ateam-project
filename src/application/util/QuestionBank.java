package application.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Data structure to manage all the questions.
 *
 */
public class QuestionBank implements QuestionBankADT {

    List<Question> questionBank;

    public QuestionBank() {
        this(null);
    }

    public QuestionBank(File jsonFile) {
        this.questionBank = new ArrayList<>();
        this.readFromJSON(jsonFile);
    }

    @Override
    public boolean readFromJSON(File jsonFile) {
        // todo implement
        return false;
    }

    @Override
    public boolean writeToJSON(File destination) {
        // todo implement
        return false;
    }

    @Override
    public String[] getAllTopics() {
        // todo implement
        return new String[0];
    }

    @Override
    public Question[] getQuestionsOfTopic(String topic) {
        // todo implement
        return null;
    }

    @Override
    public void addQuestion(Question question) {
        // todo implement
    }
}

