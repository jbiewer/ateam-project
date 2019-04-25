package application.ui.root;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewTopicDialog extends VBox {

    public NewTopicDialog() {
        Button  cancel = new Button("Cancel"),
                done = new Button("Done");
        done.setOnMouseClicked(event -> {

        });

        HBox    fieldBox = new HBox(new Label("Topic:"), new TextField()),
                controlBox = new HBox(new Button("Cancel"), new Button("Done"));
        this.getChildren().addAll(fieldBox, controlBox);
    }
}
