package application.util;

import java.io.File;

/**
 * Data structure interface to hold data on quiz questions.
 */
public interface QuestionBankADT {
    boolean readFromJSON(File jsonFile);
    boolean writeToJSON(File destination);
    String[] getAllTopics();
    Question[] getQuestionsOfTopic(String topic);
    void addQuestion(Question question);
}
