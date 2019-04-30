package application.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.util.stream.Collectors.toList;

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
  public boolean addJSONQuiz(File jsonFile) {
    if (jsonFile == null) return false; // return if no file selected

    JSONParser parser = new JSONParser();

    // get root object from file
    JSONObject jo;
    try {
      jo = (JSONObject) parser.parse(new FileReader(jsonFile));
    } catch (IOException | ParseException e) { return false; }
    if (jo == null) return false; // invalid JSON format

    // JSON array for all of the questions in the JSON file
    JSONArray questions = (JSONArray) jo.get("questionArray");
    if (questions == null) return false; // return if there are no questions

    // iterate through each question
    for (Object o : questions) {
      JSONObject newQ = (JSONObject) o; // json object of the question
//      String metaData = (String) newQ.get("meta-data"); todo should we remove this ??

      // load text for labels
      String text = (String) newQ.get("questionText");
      String topic = (String) newQ.get("topic");
      String image = (String) newQ.get("image");

      // load choices w/ correct and wrong answers
      JSONArray choices = (JSONArray) newQ.get("choiceArray");

      String correct = null;
      List<String> incorrect = new ArrayList<>();

      // for the choices in the question
      for (Object c : choices) {
        JSONObject choice = (JSONObject) c;

        if (choice.get("isCorrect").equals("T")) correct = (String) choice.get("choice");
        else incorrect.add((String) choice.get("choice"));
      }

      // initialize a new question
      Question q = (image.equals("none") ?
              new Question(topic, text, correct, incorrect.toArray(new String[0]))
              : new Question(topic, text, correct, incorrect.toArray(new String[0]), new File(image))
      );

      // check if question is a duplicate
      boolean duplicate = false;
      for (Question question : this.questionBank)
        if (question.equals(q)) duplicate = true;
      if (duplicate) continue;

      this.questionBank.add(q); // finally add it to the question bank
    }

    return true;
  }

  @Override
  public boolean writeQuestionsToJSON(File destination) {
    // create root JSON object
    JSONObject jo = new JSONObject();
    // setup JSON array for questions
    JSONArray questionArray = new JSONArray();

    // go through each question and set properties
    for (Question q : this.getAllQuestions()) {
      JSONObject question = new JSONObject();
      question.put("meta-data", "unused");
      question.put("questionText", q.getPrompt());
      question.put("topic", q.getTopic());
      question.put("image", (q.getImageURI() != null ? q.getImageURI() : "none"));

      // go through and set properties for choices
      JSONArray choices = new JSONArray();
      for (String c : q.getChoices()) {
        JSONObject choice = new JSONObject();

        if (q.getCorrect().equals(c)) choice.put("isCorrect", "T");
        else choice.put("isCorrect", "F");

        choice.put("choice", c);
        choices.add(choice);
      }

      // add to question array
      question.put("choiceArray", choices);
      questionArray.add(question);
    }

    jo.put("questionArray", questionArray);

    // write JSON object to destination
    try (FileWriter file = new FileWriter(destination)) {
      file.write(jo.toJSONString());
      file.flush();
      return true;
    } catch (IOException e) { e.printStackTrace(); }

    return false;
  }

  @Override
  public String[] getAllTopics() {
    return this.questionBank.stream().map(Question::getTopic).distinct().toArray(String[]::new);
  }

  @Override
  public Question[] getQuestionsOfTopic(String topic) {
    return this.questionBank.stream().filter(question -> question.getTopic().equals(topic))
        .toArray(Question[]::new);
  }

  @Override
  public Question[] getAllQuestions() {
    return questionBank.toArray(new Question[0]);
  }

  @Override
  public void addQuestion(Question question) {
    this.questionBank.add(question);
  }

  @Override
  public Question[] getCorrect() {
    return this.questionBank.stream()
            .filter(Question::isCorrect)
            .toArray(Question[]::new);
  }
}

