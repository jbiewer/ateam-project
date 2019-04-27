package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;

/**
 * An alert that prompts the user that they haven't answered all their questions.
 */
public class NotAllAnsweredAlert extends Alert {

    /**
     * Constructs a custom alert.
     */
    public NotAllAnsweredAlert() {
        // setup alert
        super(AlertType.WARNING, "Not all the questions have been answered!");
        this.setTitle("Questions Not All Answered");

        // set style
        this.getDialogPane().getStylesheets().add(Main.alertTheme);
    }

}
