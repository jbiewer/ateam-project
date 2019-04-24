package application.ui.root;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

// Parent to be changed to whatever layout we want to use later.
public class QuestionRoot extends BorderPane {

    /**
     * Empty constructor that creates a QuestionRoot scene
     */
    public QuestionRoot() {
        Button btn = new Button("hi");
        btn.getStyleClass().add("btn-large");
        this.setCenter(btn);
        // create layout here.
    }

}
