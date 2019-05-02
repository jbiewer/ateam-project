package application.ui.alerts;

import application.main.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Optional;

public class SaveQuizPopupRoot extends VBox {

    TextField fileNameEntry;


    /**
     * Constructs a new custom alert that allows the user to save the quiz questions
     */
    public SaveQuizPopupRoot(){
        this.getStylesheets().add(Main.mainTheme); // add stylesheet

        // INITIALIZE NODES //
        Button cancel = new Button("Cancel"),
                done = new Button("Save");
        Label topic = new Label("File Name:");
        this.fileNameEntry = new TextField();

        HBox fieldBox = new HBox(topic, this.fileNameEntry),
                controlBox = new HBox(cancel, done);

        // FUNCTIONALITY //
        done.setDefaultButton(true);
        done.setOnMouseClicked(event -> {
            if(this.fileNameEntry.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Topic field can't be empty.").showAndWait();
                return;
            }
            if(new File("./Questions/" + fileNameEntry.getText()).exists()){ //if the file already exists
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This path already exists. Would you like to overwrite it?");
                ButtonType confirmation = ButtonType.YES;
                ButtonType cancelBtn = ButtonType.CANCEL;

                alert.getButtonTypes().setAll(confirmation, cancelBtn);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == confirmation){
                    Main.questionBank.writeQuestionsToJSON(new File("./Questions/" + fileNameEntry.getText() + ".json"));
                }

            }
            else{ //if the file does not exist, make a new one
                Main.questionBank.writeQuestionsToJSON(new File("./Questions/" + fileNameEntry.getText() + ".json"));
            }

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

//        Main.closeCurrentDialogScene();


//        this.setTitle("Save Quiz Questions");
//        this.setHeaderText("What would you like to name the question file?");
//        TextInputDialog confirm = new TextInputDialog();
//        confirm.setContentText("File name:");
//
//        // change buttons
//        this.getButtonTypes().removeAll(this.getButtonTypes());
//        this.getButtonTypes().addAll(ButtonType.OK);

    }

}
