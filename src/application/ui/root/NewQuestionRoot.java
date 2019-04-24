package application.ui.root;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.*;

// Parent to be changed to whatever layout we want to use later.
public class NewQuestionRoot extends GridPane {

    public NewQuestionRoot() {
        // Root property definitions:
//        this.getStylesheets().add("stylesheet-light.css");

        // Node instantiation:
        Label   title = new Label("New Question"),
                text = new Label("Prompt:"),
                image = new Label("Image:"),
                choices = new Label("Choices (separated by commas)");
        Button  imgBrowse = new Button("Browse"),
                addChoice = new Button("+"),
                cancel = new Button("Cancel"),
                save = new Button("Save");
        TextField textField = new TextField();
        VBox    choicesGroup = new VBox();

        // set column indices of each
        new HashMap<Node, Integer>() {{
            put(title, 0);
            put(text, 0);       put(textField, 1);
            put(image, 0);      put(imgBrowse, 1);
            put(choices, 0);    put(choicesGroup, 1);
            put(cancel, 0);     put(save, 1);
        }}.forEach(GridPane::setColumnIndex);
        new HashMap<Node, Integer>() {{     // set row indices of each
            put(title, 0);
            put(text, 1);       put(textField, 1);
            put(image, 2);      put(imgBrowse, 2);
            put(choices, 3);    put(choicesGroup, 3);
            put(cancel, 4);     put(save, 4);
        }}.forEach(GridPane::setRowIndex);
        GridPane.setRowSpan(title, 2); // let column span entire layout

        // Property definitions:
        GridPane.setColumnIndex(title, 0);


        // Frame structure implementation:
        choicesGroup.getChildren().add(addChoice);
    }

}
