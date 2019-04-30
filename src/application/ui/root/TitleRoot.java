package application.ui.root;

import application.main.Main;
import application.ui.alerts.CustomAlert;
import application.ui.alerts.SaveQuizPopupRoot;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;

public class TitleRoot extends VBox {

  private Label title;
  private Label totalQuestions;
  private HBox middle;
  private HBox bottom;
  private Button load;
  private Button addQuestion;
  private Button save;
  private Button start;
  private Button exit;
  private File questionFile; //indicates if a file was loaded
  private int totalNumQuestions;

  public TitleRoot() {
    this.title = new Label("Quiz Generator");
    this.load = new Button("Load Quiz");
    this.save = new Button("Save Quiz");
    this.start = new Button("Start Quiz");
    this.exit = new Button("Exit");
    this.addQuestion = new Button("Add Question");
    this.totalQuestions = new Label("Total Questions: " + this.totalNumQuestions);
    this.middle = new HBox(10, load, addQuestion, save);
    this.middle.setAlignment(Pos.CENTER);
    this.bottom = new HBox(10, exit, totalQuestions, start);
    this.bottom.setAlignment(Pos.CENTER);
    this.getChildren().addAll(this.title, this.bottom, this.middle);
    this.setSpacing(10);
    this.setAlignment(Pos.CENTER);

    updateNumQuestions(); // update the num of questions if they've changed

    this.load.setOnAction(event -> {
      Main.questionBank.addJSONQuiz(Main.loadFile(new ExtensionFilter("JSON (*.json)", "*.json"),
          "Choose the JSON Quiz File to Load"));
      if (questionFile == null)
        return;
      Main.questionBank.addJSONQuiz(questionFile);
      updateNumQuestions();
    });

    this.start.setOnAction(Event -> {
      Main.switchScene(GUIScene.QUIZ_SETTINGS);
    });

    this.exit.setOnAction(Event -> {
      if (GUIAlert.SAVE_ON_LEAVE.alert().get() == ButtonType.YES)
        Main.questionBank.writeQuestionsToJSON(Main.SAVE_QUESTION_DIR);
      Main.closeApplication();
    });

    this.addQuestion.setOnAction(Event -> {
      Main.switchScene(GUIScene.NEW_QUESTION);
    });

    this.save.setOnAction(Event -> {
      Main.questionBank.writeQuestionsToJSON(new File("." + "newTestJSON"));
      //      Main.initDialogScene(
      //          new Scene(new SaveQuizPopupRoot(), CustomAlert.WIDTH, CustomAlert.HEIGHT));
      //
      //      if (questionFile != null) {
      //        Main.questionBank.writeQuestionsToJSON(questionFile);
      //      } else {
      //        File newQuestionFile = new File("./Quiz Questions/testttt2");
      //        Main.questionBank.writeQuestionsToJSON(newQuestionFile);
      //      }
      //      Main.switchScene(GUIScene.TITLE);
    });

  }

  public void updateNumQuestions() {
    this.totalQuestions.setText("Total Questions: " + Main.questionBank.getAllQuestions().length);
  }

}
