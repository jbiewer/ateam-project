package application.util;

import java.io.File;

/**
 * Data structure interface to hold data on quiz questions.
 */
public interface QuestionBankADT {
    /**
     * Takes in a JSON file and interprets it as a Quiz.
     * It will scan through and look for questions, adding them to the question bank.
     * @param jsonFile JSON file to use.
     * @return True if the file was successfully read, false if it was in the wrong format.
     */
    boolean addJSONQuiz(File... jsonFile);

    /**
     * Takes the current data and writes it to a JSON as as Quiz.
     * @param destination Where to store the JSON file.
     * @return True if the file was successfully written, false if the file location doesn't exist.
     */
    boolean writeQuestionsToJSON(File destination);

    /**
     * Goes through all the questions in the question bank and returns a distinct list of all the topics.
     * @return An array of all the topics.
     */
    String[] getAllTopics();

    /**
     * Goes through all the questions and looks for the topic passed in.
     * @param topic Topic of questions.
     * @return An array of Questions with the respective topic.
     */
    Question[] getQuestionsOfTopic(String topic);

    /**
     * @return All the questions in the question bank.
     */
    Question[] getAllQuestions();

    /**
     * Adds a question to the question bank.
     * @param question Question to add.
     */
    void addQuestion(Question question);
}
