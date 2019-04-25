package application.ui.root;

import java.io.File;
import application.main.Main;
import application.ui.util.GUIScene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

// Parent to be changed to whatever layout we want to use later.
public class TitleRoot extends Parent {

  private Label title;
  private Label totalQuestions;
  private HBox middle;
  private HBox bottom;
  private VBox vertical;
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
    this.bottom = new HBox(10, exit, totalQuestions, start);
    this.vertical = new VBox(10, title, middle, bottom);


    this.load.setOnAction(Event -> {
      File questions = Main.loadFile(new ExtensionFilter("JSON (*.json)", "*.json"),
          "Choose the JSON Quiz File to Load");
      // parse JSON file and add it to questions database //
    });

    this.start.setOnAction(Event -> {
      Main.switchScene(GUIScene.QUIZ_SETTINGS);
    });

    this.exit.setOnAction(Event -> {
      // show popup dialogue
    });
    this.addQuestion.setOnAction(Event -> {
      Main.switchScene(GUIScene.NEW_QUESTION);
    });
    this.save.setOnAction(Event -> {
      // save unstored values in question database to the file
    });
    this.getChildren().add(vertical);
  }
}
