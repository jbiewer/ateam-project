package application.ui.util;

import application.main.Main;
import application.ui.root.*;
import application.util.Question;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

public enum GUIScene {
    TITLE(TitleRoot.class),
    QUESTION(QuestionRoot.class),
    RESULTS(ResultsRoot.class),
    QUIZ_SETTINGS(QuizSettingsRoot.class),
    NEW_QUESTION(NewQuestionRoot.class);

    private Class root;

    GUIScene(Class root) {
        this.root = root;
    }

    public Scene getScene() {
        try {
            Scene s = new Scene((Parent) this.root.newInstance(), Main.WIDTH, Main.HEIGHT);
            s.getStylesheets().add(Main.theme);
            return s;
        } catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
        return null;
    }
}
