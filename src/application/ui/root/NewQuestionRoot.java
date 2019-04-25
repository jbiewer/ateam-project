package application.ui.root;

import application.main.Main;
import application.ui.util.GUIScene;
import application.util.Question;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.net.URI;
import java.util.*;

/**
 * The root node class of the scene "New Question".
 * @author Jacob Biewer
 */
public class NewQuestionRoot extends GridPane {

    // references to labels used to save data
    private TextField promptField;
    private ChoicesVBox choicesVBox;
    private ComboBox<String> topicsList;
    private File imgToSave;

    /**
     * Constructs layout from root node.
     */
    public NewQuestionRoot() {
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
                topic = new Label("Topic: "),
                image = new Label("Image:"),
                choices = new Label("Choices");
        this.topicsList = new ComboBox<>();
        this.promptField = new TextField();
        this.choicesVBox = new ChoicesVBox();

        // SETUP topicsList COMBO BOX //
        this.topicsList.getItems().add("+ add topic");
        this.topicsList.getItems().addAll(0, Arrays.asList(Main.questionBank.getAllTopics()));
        this.topicsList.setOnAction(event -> {
            ComboBox src = (ComboBox) event.getSource();
            if (src.getValue().equals("+ add topic"))
                Main.initDialogScene(new Scene(new NewTopicDialog(), 300, 150));

        });

        // SET ROW AND COLUMN INDICES //
        new HashMap<Node, Integer>() {{
            put(title, 0);
            put(topic, 0);      put(topicsList, 1);
            put(text, 0);       put(promptField, 1);
            put(image, 0);      put(imgBox, 1);
            put(choices, 0);    put(choicesVBox, 1);
            put(options, 0);
        }}.forEach(GridPane::setColumnIndex);
        new HashMap<Node, Integer>() {{
            put(title, 0);
            put(topic, 1);      put(topicsList, 1);
            put(text, 2);       put(promptField, 2);
            put(image, 3);      put(imgBox, 3);
            put(choices, 4);    put(choicesVBox, 4);
            put(options, 5);
        }}.forEach(GridPane::setRowIndex);
        GridPane.setColumnSpan(title, 2); // let title span entire layout
        GridPane.setColumnSpan(options, 2); // same with options

        // LAYOUT EACH ELEMENT //
        this.getChildren().addAll(
                title, text, image, choices, imgBox, options, this.promptField, this.choicesVBox, topic, this.topicsList
        );
        this.getChildren().forEach(n -> GridPane.setMargin(n, new Insets(20)));

        // SET STYLE //
        Arrays.stream(new Node[] { text, image, choices }).forEach(n -> n.getStyleClass().add("main-text"));
        this.promptField.getStyleClass().add("text-field");
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

            // SET STYLE //
            title.getStyleClass().add("header");
        }
    }

    /**
     * Custom HBox to organize the image browsing feature.
     */
    private class ImageBrowsingHBox extends HBox {

        private ImageBrowsingHBox() {
            // INITIALIZE NODES //
            Button  imgBrowse = new Button("Browse");
            ImageView imgPreview = new ImageView();

            // FUNCTIONALITY //
            imgBrowse.setOnMouseClicked(event -> {
                File imgFile = Main.loadFile(
                        new ExtensionFilter("Image File (png/jpg)", "*.jpg", "*.png"),
                        "Choose Image File For Question"
                );
                if (imgFile == null) return;
                imgPreview.setImage(new Image(imgFile.toURI().toString()));
            });

            // SETUP LAYOUT //
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

        private String[] getChoices() {
            List<String> choices = new ArrayList<>();
            this.getChildren().forEach(child -> {
                if(child instanceof TextField) {
                    choices.add(((TextField) child).getText());
                }
            });
            return choices.toArray(new String[0]);
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
                saveQuestion();
                Main.switchScene(GUIScene.TITLE);
            });
            this.setAlignment(Pos.CENTER);
            this.setSpacing(100);
        }

        private void saveQuestion() {
            Main.questionBank.addQuestion(new Question(
                    topicsList.getValue(), promptField.getText(), choicesVBox.getChoices(), imgToSave
            ));
        }
    }

    /**
     * Custom VBox used in the popup window when entering a new topic.
     */
    private class NewTopicDialog extends VBox {

        private TextField topicEntry;

        private NewTopicDialog() {
            this.getStylesheets().add(Main.theme); // add stylesheet

            // INITIALIZE NODES //
            Button  cancel = new Button("Cancel"),
                    done = new Button("Done");
            Label   topic = new Label("Topic:");
            this.topicEntry = new TextField();

            // FUNCTIONALITY //
            done.setDefaultButton(true);
            done.setOnMouseClicked(event -> {
                if(this.topicEntry.getText().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Topic field can't be empty.").showAndWait();
                    return;
                }
                topicsList.getItems().add(0, this.topicEntry.getText());
                Main.closeCurrentDialogScene();
            });
            cancel.setOnMouseClicked(event -> Main.closeCurrentDialogScene());

            // SETUP LAYOUT //
            cancel.getStyleClass().add("btn-large");
            done.getStyleClass().add("btn-large");
            topic.getStyleClass().add("main-text");
            this.topicEntry.getStyleClass().add("text-field");

            HBox    fieldBox = new HBox(topic, this.topicEntry),
                    controlBox = new HBox(cancel, done);
            this.getChildren().addAll(fieldBox, controlBox);
        }
    }

}
