package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the entire program. It's the Main Man.
 */
public class Main extends Application {

    private static Stage stage; // the primary stage of the application

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
        // hello, world!
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
    public static void switchScene(Scene scene) {
        Main.stage.setScene(scene);
    }

}
