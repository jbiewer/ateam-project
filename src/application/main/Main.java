package application.main;

import application.ui.util.GUIScene;
import application.util.Question;
import application.util.QuestionBank;
import application.util.QuizManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * The main class of the entire program. It's the Main Man.
 */
public class Main extends Application {

  public static final int WIDTH = 800, HEIGHT = 600; // dimensions of each scene

  public static QuizManager quizManager = new QuizManager();
  private static Stage stage, currentPopup; // primary stage (first) and stage of the current popup (second)
  public static String mainTheme = "application/style/style-light.css", // default theme for all layouts
      alertTheme = "application/style/style-light-alert.css";

  public static QuestionBank questionBank = new QuestionBank();

  /**
   * Method run before the application opens.
   * Any initialization is done here.
   * @throws Exception When there's a boo boo in the code.
   */
  @Override
  public void init() throws Exception {
    // todo remove vvvvvvvvv
    super.init();
    questionBank.addQuestion(
            new Question("Math", "What is 2 + 2?", "4",
                    new String[] {"1", "2", "3"})
    );
    questionBank.addQuestion(
            new Question("Math", "What is 4 - 1?", "3",
                    new String[] {"1", "2", "5", "7"})
    );
    questionBank.addQuestion(
            new Question("Math", "What is the curl of the total magetic field of the Earth?", "O_o",
                    new String[] {"curl(lol u thought)", "nope", ":)"})
    );
    questionBank.addQuestion(
            new Question("Math", "Am I too lazy to think of questions?", "yes",
                    new String[] {"y3s, but with a 3", "no", "maybe"})
    );
    // todo remove ^^^^^^^^^^
  }

  /**
   * Method run when the program executes.
   * @param primaryStage Main stage for the application.
   * @throws Exception When there's a boo boo in the code.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Main.stage = primaryStage;
    Main.switchScene(GUIScene.TITLE);
    primaryStage.show();
  }

  /**
   * Good ol' PSVM
   * @param args cmd line args.
   */
  public static void main(String[] args) {
    if (args.length != 0) {
      if (args[0].equals("themes")) {
        System.out.println("Themes: light, dark");
        return;
      }

      switch(args[0]) {
        case "light":
          Main.mainTheme = "application/style/style-light.css";
          break;
        case "dark":
          Main.mainTheme = "application/style/style-dark.css";
          break;
        default:
          System.out.println("Theme '" + args[0] + "' unrecognized. Using default 'light' mainTheme.");
          Main.mainTheme = "application/style/style-light.css";
          break;
      }
    } else {
      Main.mainTheme = "application/style/style-light.css";
    }
    launch(args);
  }

  /**
   * Used to switch between scenes on the primary stage.
   * @param scene Scene to switch to.
   */
  public static void switchScene(GUIScene scene) {
    Main.stage.setScene(scene.getScene());
  }

  /**
   * Safe-close of the application to avoid any corrupt save files, etc.
   */
  public static void closeApplication() {
    stage.close();
  }

  /**
   * Pops up a dialog window with the given scene.
   * @param scene Scene to use in the popup.
   */
  public static void initDialogScene(Scene scene) {
    Stage popup = new Stage();
    popup.setScene(scene);
    popup.initOwner(stage);
    popup.initModality(Modality.WINDOW_MODAL);
    popup.show();
    currentPopup = popup;
  }

  /**
   * Closes a popup window if one is currently open.
   * @return True if the window was successfully closed, false if a window wasn't open.
   */
  public static boolean closeCurrentDialogScene() {
    if (currentPopup == null) return false;
    currentPopup.close();
    currentPopup = null;
    return true;
  }

  /**
   * Loads a file and returns it.
   *
   * @param filter type of file to select
   * @param title title of the FileChooser
   * @return file that is chosen by user
   */
  public static File loadFile(ExtensionFilter filter, String title) {
    FileChooser choose = new FileChooser();
    choose.getExtensionFilters().add(filter);
    choose.setTitle(title);
    return choose.showOpenDialog(Main.stage);
  }

}
