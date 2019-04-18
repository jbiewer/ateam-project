package application.util;

import application.main.Main;
import application.ui.QuestionRoot;
import application.ui.TitleRoot;
import javafx.scene.Scene;

public enum GUIScene {
    TITLE_SCENE(new Scene(new TitleRoot(), Main.WIDTH, Main.HEIGHT)),
    QUESTION_SCENE(new Scene(new QuestionRoot(), Main.WIDTH, Main.HEIGHT));

    private Scene scene;
    GUIScene(Scene scene) {
        this.scene = scene;
    }
    public Scene getScene() {
        return scene;
    }
}
