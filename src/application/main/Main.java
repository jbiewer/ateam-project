package application.main;

import application.util.GUIScene;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class of the entire program. It's the Main Man.
 */
public class Main extends Application {

    private static Stage stage; // the primary stage of the application

    public static final int WIDTH = 1200, HEIGHT = 800; // dimensions of each scene

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
        Main.switchScene(GUIScene.TITLE_SCENE);
        primaryStage.show();
    }

    /**
     * Good ol' PSVM
     * @param args cmd line args.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Used to switch between scenes on the primary stage.
     * @param scene Scene to switch to.
     */
    public static void switchScene(GUIScene scene) {
        Main.stage.setScene(scene.getScene());
    }

}
