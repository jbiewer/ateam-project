package application.ui.root;

import application.main.Main;
import application.ui.alerts.SaveOnLeaveAlert;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.util.Optional;


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

    this.load.setOnAction(event -> {
      File questionFile = Main.loadFile(new ExtensionFilter("JSON (*.json)", "*.json"),
          "Choose the JSON Quiz File to Load");
      if (questionFile == null)
        return;
      Main.questionBank.addJSONQuestion(questionFile);
      this.updateNumQuestions(Main.questionBank.getAllQuestions().length);
    });

    this.start.setOnAction(Event -> {
      Main.switchScene(GUIScene.QUIZ_SETTINGS);
    });

    this.exit.setOnAction(Event -> {
      Alert leave = new SaveOnLeaveAlert();
      Optional<ButtonType> option = leave.showAndWait();
      if (option.get() == ButtonType.YES) {
        //save
      } else
        Main.closeApplication();
    });

    this.addQuestion.setOnAction(Event -> {
      Main.switchScene(GUIScene.NEW_QUESTION);
    });

    this.save.setOnAction(Event -> {
      GUIAlert.SAVE_QUIZ.alert();
      Main.switchScene(GUIScene.TITLE);
    });

  }

  private void updateNumQuestions(int num) {
    this.totalNumQuestions += num;
    this.totalQuestions.setText("Total Questions: " + totalNumQuestions);
  }


}
