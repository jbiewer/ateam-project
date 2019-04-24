package application.ui.root;

import application.main.Main;
import application.ui.util.GUIScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class NewQuestionRoot extends GridPane {

    private File imgToSave;

    public NewQuestionRoot() {
        this.getStylesheets().add(Main.theme); // style layout to global theme.

        // DEFINE COLUMN WIDTH's //
        Arrays.stream(new ColumnConstraints[2]).forEach(c -> {
            c = new ColumnConstraints();
            c.setHgrow(Priority.ALWAYS);
            this.getColumnConstraints().add(c);
        });
        this.getColumnConstraints().get(0).setHalignment(HPos.RIGHT);
        this.getColumnConstraints().get(0).setPercentWidth(25);
        this.getColumnConstraints().get(1).setHalignment(HPos.LEFT);
        this.getColumnConstraints().get(1).setPercentWidth(75);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(50));

        // INITIALIZE NODES //
        HBox    title = new TitleHBox(),
                options = new CancelSaveHBox(),
                imgBox = new ImageBrowsingHBox();
        Label   text = new Label("Prompt:"),
                image = new Label("Image:"),
                choices = new Label("Choices");
        TextField textField = new TextField();
        VBox    choicesGroup = new ChoicesVBox();

        // IMPLEMENT NODE FUNCTIONALITY //


        // SET ROW AND COLUMN INDICES //
        new HashMap<Node, Integer>() {{
            put(title, 0);
            put(text, 0);       put(textField, 1);
            put(image, 0);      put(imgBox, 1);
            put(choices, 0);    put(choicesGroup, 1);
            put(options, 0);
        }}.forEach(GridPane::setColumnIndex);
        new HashMap<Node, Integer>() {{
            put(title, 0);
            put(text, 1);       put(textField, 1);
            put(image, 2);      put(imgBox, 2);
            put(choices, 3);    put(choicesGroup, 3);
            put(options, 4);
        }}.forEach(GridPane::setRowIndex);
        GridPane.setColumnSpan(title, 2); // let title span entire layout
        GridPane.setColumnSpan(options, 2); // same with options

        // LAYOUT EACH ELEMENT //
        ObservableList<Node> children = FXCollections.observableArrayList(
                title, text, image, choices, imgBox, options, textField, choicesGroup
        );
        children.forEach(n -> GridPane.setMargin(n, new Insets(20)));
        this.getChildren().addAll(children);

        // SET STYLE FOR EACH //
        this.setStyleClasses(
                new Pair<>("main-text", new Node[] { text, image, choices }),
                new Pair<>("text-field", new Node[] { textField })
        );
    }

    /**
     * Custom HBox to center the title label on the screen.
     */
    private class TitleHBox extends HBox {
        private TitleHBox() {
            // SETUP TITLE BOX //
            Label title = new Label("New Question");
            this.getChildren().add(title);
            this.setAlignment(Pos.CENTER);

            // SET STYLE FOR TITLE //
            title.getStyleClass().add("header");
        }
    }

    private class ImageBrowsingHBox extends HBox {
        private ImageBrowsingHBox() {
            // SETUP BOX //
            Button  imgBrowse = new Button("Browse");
            ImageView imgPreview = new ImageView();
            imgBrowse.setOnMouseClicked(event -> {
                imgPreview.setImage(new Image(Main.loadFile(
                        new ExtensionFilter("Image File (png/jpg)", "*.jpg", "*.png"),
                        "Choose Image File For Question"
                ).toURI().toString()));
            });
            imgBrowse.getStyleClass().add("btn-medium");
            imgPreview.setFitWidth(100);
            imgPreview.setFitHeight(100);

            this.getChildren().addAll(imgBrowse, imgPreview);
            this.setAlignment(Pos.CENTER_LEFT);
            this.setSpacing(30);
        }
    }

    /**
     * Custom VBox to handle adding new choices in the layout.
     */
    private class ChoicesVBox extends VBox {
        private ChoicesVBox() {
            Button addChoice = new Button("+");
            addChoice.setOnMouseClicked(e -> {
                TextField toAdd = new TextField();
                toAdd.maxWidth(Double.MAX_VALUE);
                toAdd.getStyleClass().add("text-field");
                this.getChildren().add(this.getChildren().size()-1, toAdd);
            });
            addChoice.getStyleClass().add("btn-medium");
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
                // todo 'save' implementation
                Main.switchScene(GUIScene.TITLE);
            });
            this.setAlignment(Pos.CENTER);
            this.setSpacing(100);
        }
    }

    @SafeVarargs
    private final void setStyleClasses(Pair<String, Node[]>... toStyle) {
        Arrays.stream(toStyle).forEach(pair -> {
            for (Node node : pair.getValue())
                node.getStyleClass().add(pair.getKey());
        });
    }

}
