package application.ui.root;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

// Parent to be changed to whatever layout we want to use later.
public class QuestionRoot extends BorderPane {

    public QuestionRoot() {
        this.setCenter(new Button("hello!"));
        // create layout here.
    }

}
