package application.ui.alerts;

import application.main.Main;
import application.ui.util.GUIAlert;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Optional;

/**
 * Root node that contains UI on entering a file name and then saves all the questions in the
 * question bank (form Main) to a JSON file.
 */
public class SaveQuizPopupRoot extends VBox {

    /**
     * Constructs a new custom alert that allows the user to save the quiz questions
     */
    public SaveQuizPopupRoot() {
        this.getStylesheets().add(Main.MAIN_THEME); // add stylesheet

        // INITIALIZE NODES //
        Button cancel = new Button("Cancel"),
                done = new Button("Save");
        Label topic = new Label("File Name:");
        TextField fileNameEntry = new TextField();

        HBox fieldBox = new HBox(topic, fileNameEntry),
                controlBox = new HBox(cancel, done);

        // FUNCTIONALITY //
        done.setDefaultButton(true);
        done.setOnMouseClicked(event -> {
            if(fileNameEntry.getText().isEmpty()) {
                GUIAlert.quickAlert(Alert.AlertType.WARNING, "Missing Field", "Must specify file name.");
                return;
            }

            // check if user put in extension
            String fileName = fileNameEntry.getText() + ".json";
            if (fileNameEntry.getText().length() > 5) {
                fileName = fileNameEntry.getText().substring(fileNameEntry.getText().length() - 5).equals(".json") ?
                        fileNameEntry.getText() : fileNameEntry.getText() + ".json";
            }
            // write to file
            File quizFile = new File(Main.SAVE_QUESTION_DIR + "/" + fileName);
            if(quizFile.exists()) { // if the file already exists
                Optional<ButtonType> result = GUIAlert.quickAlert(Alert.AlertType.CONFIRMATION,
                        "File Exists",
                        "This path already exists, would you like to overwrite it?",
                        ButtonType.YES, ButtonType.NO);

                // if the user wants to overwrite, then do so
                if(result.get() == ButtonType.YES) Main.questionBank.writeQuestionsToJSON(quizFile);

            } else Main.questionBank.writeQuestionsToJSON(quizFile); // if the file does not exist, make a new one

            Main.closeCurrentDialogScene();
        });
        cancel.setOnMouseClicked(event -> Main.closeCurrentDialogScene());

        // SETUP LAYOUT //
        this.getChildren().addAll(fieldBox, controlBox);
        this.getChildren().forEach(child -> {
            ((HBox) child).setAlignment(Pos.CENTER);
            ((HBox) child).setSpacing(20);
        });
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
    }

}
