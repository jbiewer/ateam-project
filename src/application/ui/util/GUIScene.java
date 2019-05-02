package application.ui.util;

import application.main.Main;
import application.ui.root.*;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Enumeration that holds scenes that can be used whenever and wherever.
 */
public enum GUIScene {
    TITLE(TitleRoot.class),
    QUESTION(QuestionRoot.class),
    RESULTS(ResultsRoot.class),
    QUIZ_SETTINGS(QuizSetupRoot.class),
    NEW_QUESTION(NewQuestionRoot.class);

    private Class root; // root node's class

    /**
     * Stores the root node's class.
     * @param root Class of the root node.
     */
    GUIScene(Class root) {
        this.root = root;
    }

    /**
     * @return The scene with the root node initialized.
     */
    public Scene getScene() {
        try {
            Scene s = new Scene((Parent) this.root.newInstance(), Main.WIDTH, Main.HEIGHT);
            s.getStylesheets().add(Main.MAIN_THEME);
            return s;
        } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
        return null;
    }
}
