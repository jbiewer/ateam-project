package application.ui.util;

import application.main.Main;
import application.ui.root.*;
import javafx.scene.Scene;

public enum GUIScene {
    TITLE(new Scene(new TitleRoot(), Main.WIDTH, Main.HEIGHT)),
    QUESTION(new Scene(new QuestionRoot(), Main.WIDTH, Main.HEIGHT)),
    RESULTS(new Scene(new ResultsRoot(), Main.WIDTH, Main.HEIGHT)),
    QUIZ_SETTINGS(new Scene(new QuizSettingsRoot(), Main.WIDTH, Main.HEIGHT)),
    NEW_QUESTION(new Scene(new NewQuestionRoot(), Main.WIDTH, Main.HEIGHT)),
    SAVE_ON_LEAVE(new Scene(new SaveOnLeaveRoot(), Main.WIDTH, Main.HEIGHT));

    private Scene scene;
    GUIScene(Scene scene) {
        this.scene = scene;
    }
    public Scene getScene() {
        return scene;
    }
}
