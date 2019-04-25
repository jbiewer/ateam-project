package application.main;

import application.ui.util.GUIScene;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

/**
 * The main class of the entire program. It's the Main Man.
 */
public class Main extends Application {

  public static final int WIDTH = 800, HEIGHT = 600; // dimensions of each scene

  private static Stage stage; // the primary stage of the application
  public static String theme = "application/style/style-light.css"; // default theme for all layouts

  /**
   * Method run before the application opens.
   * Any initialization is done here.
   * @throws Exception When there's a boo boo in the code.
   */
  @Override
  public void init() throws Exception {
    super.init();
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
          Main.theme = "application/style/style-light.css";
          break;
        case "dark":
          Main.theme = "application/style/style-dark.css";
          break;
        default:
          System.out.println("Theme '" + args[0] + "' unrecognized. Using default 'light' theme.");
          Main.theme = "application/style/style-light.css";
          break;
      }
    } else {
      Main.theme = "application/style/style-light.css";
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

  // may not need to use this:
//  /**
//   * Getter method for the main stage.
//   * @return the main stage.
//   */
//  public static Stage getStage() {
//    return Main.stage;
//  }

}
