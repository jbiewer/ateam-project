package application.ui.root;

import application.main.Main;
import application.ui.util.GUIScene;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.HashMap;

public class NewQuestionRoot extends GridPane {

    public NewQuestionRoot() {
        // Root property definitions:
        this.getStylesheets().add("stylesheet.css");
        Arrays.stream(new ColumnConstraints[2]).forEach(c -> {
            c = new ColumnConstraints();
            c.setHgrow(Priority.ALWAYS);
            this.getColumnConstraints().add(c);
        });
        this.getColumnConstraints().get(0).setHalignment(HPos.RIGHT);
        this.getColumnConstraints().get(0).setPercentWidth(25);
        this.getColumnConstraints().get(1).setHalignment(HPos.LEFT);
        this.getColumnConstraints().get(1).setPercentWidth(75);
        this.getRowConstraints().add(new RowConstraints());
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(50));

        // set row width:
//        Arrays.stream(new RowConstraints[5]).forEach(r -> {
//            r = new RowConstraints();
//            r.setVgrow(Priority.ALWAYS);
//            this.getRowConstraints().add(r);
//        });

        // Node instantiation:
        HBox    title = new TitleHBox(),
                options = new CancelSaveHBox();
        Label   text = new Label("Prompt:"),
                image = new Label("Image:"),
                choices = new Label("Choices");
        Button  imgBrowse = new Button("Browse");
        TextField textField = new TextField();
        VBox    choicesGroup = new ChoicesVBox();


        // set column indices of each
        new HashMap<Node, Integer>() {{
            put(title, 0);
            put(text, 0);       put(textField, 1);
            put(image, 0);      put(imgBrowse, 1);
            put(choices, 0);    put(choicesGroup, 1);
            put(options, 0);
        }}.forEach(GridPane::setColumnIndex);
        new HashMap<Node, Integer>() {{     // set row indices of each
            put(title, 0);
            put(text, 1);       put(textField, 1);
            put(image, 2);      put(imgBrowse, 2);
            put(choices, 3);    put(choicesGroup, 3);
            put(options, 4);
        }}.forEach(GridPane::setRowIndex);
        GridPane.setColumnSpan(title, 2); // let title span entire layout
        GridPane.setColumnSpan(options, 2); // same with options

        // Property definitions:
        GridPane.setColumnIndex(title, 0);

        // Frame structure implementation:
        this.getChildren().addAll(FXCollections.observableArrayList(
                title, text, image, choices, imgBrowse, options, textField, choicesGroup
        ));
        this.getChildren().forEach(n -> {
            GridPane.setMargin(n, new Insets(20));
            n.setStyle("-fx-border-color: black;");
        });
        this.setStyle("-fx-border-color: black;");
    }

    /**
     * Custom HBox to center the title label on the screen.
     */
    private class TitleHBox extends HBox {
        private TitleHBox() {
            Label title = new Label("New Question");
            this.getChildren().add(title);
            this.setAlignment(Pos.CENTER);
        }
    }

    /**
     * Custom VBox to handle adding new choices in the layout.
     */
    private class ChoicesVBox extends VBox {
        private ChoicesVBox() {
            Button addChoice = new Button("+");
            addChoice.setOnMouseClicked(e -> {
                int index = this.getChildren().size()-1;
                this.getChildren().add(index, new TextField());
                this.getChildren().get(index).maxWidth(Double.MAX_VALUE);
            });
            this.getChildren().add(addChoice);
            this.setSpacing(10);
        }
    }

    /**
     * Custom HBox to handle organizing the layout of the 'cancel' and 'save' buttons.
     */
    private class CancelSaveHBox extends HBox {
        private CancelSaveHBox() {
            Arrays.stream(new Button[] { new Button("Cancel"), new Button("Save") })
                    .forEach(btn -> {
                        btn.getStyleClass().add("btn-large");
                        HBox.setHgrow(btn, Priority.ALWAYS);
                        this.getChildren().add(btn);
                    });
            this.getChildren().get(0).setOnMouseClicked(e -> Main.switchScene(GUIScene.TITLE));
            this.getChildren().get(1).setOnMouseClicked(e -> {
                // add 'save' implementation
                Main.switchScene(GUIScene.TITLE);
            });
            this.setAlignment(Pos.CENTER);
            this.setSpacing(100);
        }
    }

}
