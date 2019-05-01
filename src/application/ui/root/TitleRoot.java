package application.ui.root;

import application.main.Main;
import application.ui.alerts.SaveQuizPopupRoot;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser.ExtensionFilter;

public class TitleRoot extends BorderPane {

  private Label totalQuestions;

  public TitleRoot() {
    // INITIALIZE NODES //
    Label title = new Label("Quiz Generator");
    Button  load = new Button("Load Quiz"),
            save = new Button("Save Quiz"),
            start = new Button("Start Quiz"),
            exit = new Button("Exit"),
            addQuestion = new Button("Add Question");
    HBox    buttons = new HBox(exit, load, addQuestion, save, start);
    this.totalQuestions = new Label("Total Questions: ");

    // FUNCTIONALITY /
    load.setOnAction(event -> {
      Main.questionBank.addJSONQuiz(Main.loadFile(new ExtensionFilter("JSON (*.json)", "*.json"),
          "Choose the JSON Quiz File to Load"));
      updateNumQuestions();
    });
    start.setOnAction(Event -> Main.switchScene(GUIScene.QUIZ_SETTINGS));
    exit.setOnAction(Event -> Main.closeApplication());
    addQuestion.setOnAction(Event -> Main.switchScene(GUIScene.NEW_QUESTION));
    save.setOnAction(Event -> Main.initDialogScene(new Scene(new SaveQuizPopupRoot(), 600, 150)));

    // SETUP LAYOUT //
    BorderPane.setAlignment(title, Pos.CENTER);
    BorderPane.setAlignment(buttons, Pos.CENTER);
    buttons.setSpacing(20);
    buttons.setAlignment(Pos.CENTER);

    this.setTop(title);
    this.setCenter(this.totalQuestions);
    this.setBottom(buttons);

    updateNumQuestions(); // update the num of questions if they've changed
  }

  public void updateNumQuestions() {
    this.totalQuestions.setText("Total Questions: " + Main.questionBank.getAllQuestions().length);
  }

}
