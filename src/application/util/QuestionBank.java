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
  public boolean addJSONQuiz(File... jsonFiles) {
    JSONParser parser = new JSONParser();
    JSONObject jo = null;
    for (File f : jsonFiles) { // for each file in jsonFiles

      try {
        jo = (JSONObject) parser.parse(new FileReader(f));
      } catch (IOException | ParseException e) {
        return false;
      }


      JSONArray questions = (JSONArray) jo.get("questionArray"); //json array for all of the questions in the json file
      if (questions == null)
        return false;
      for (int i = 0; i < questions.size(); i++) {
        JSONObject newQ = (JSONObject) questions.get(i); // json object of the question
        String metaData = (String) newQ.get("meta-data");
        String text = (String) newQ.get("questionText");
        String topic = (String) newQ.get("topic");
        String image = (String) newQ.get("image");

        JSONArray choices = (JSONArray) newQ.get("choiceArray");
        String correct = null;
        List<String> incorrect = new ArrayList<>();
        for (int j = 0; j < choices.size(); j++) { // for the choices in the question
          JSONObject choice = (JSONObject) choices.get(j);
          if (((String) (choice.get("isCorrect"))).equals("T")) {
            correct = (String) choice.get("choice");
          } else {
            incorrect.add((String) choice.get("choice"));
          }
        }
        if (image.equals("none")) // if there isn't an image
          questionBank
              .add(new Question(topic, text, correct, incorrect.stream().toArray(String[]::new)));
        else
          questionBank.add(new Question(topic, text, correct,
              incorrect.stream().toArray(String[]::new), new File(image)));
      }
    }
    return true;
  }

  @Override
  public boolean writeQuestionsToJSON(File destination) {
    if (!destination.exists())
      return false; // todo print message to user somehow of invalid destination
    try{
      FileWriter writer = new FileWriter(destination);
      writer.write("Test data");
      writer.close();
    } catch(Exception e){
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public String[] getAllTopics() {
    return this.questionBank.stream()
            .map(Question::getTopic)
            .distinct()
            .toArray(String[]::new);
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
}

