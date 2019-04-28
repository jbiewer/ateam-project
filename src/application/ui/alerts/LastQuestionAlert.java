package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * An alert that prompts the user if they wish to see quiz results or go back to the questions.
 */
public class LastQuestionAlert extends Alert {

    // static constants to refer to button types from external classes.
    public static final ButtonType SEE_RESULTS = new ButtonType("See Results"),
            GO_BACK = new ButtonType("Go Back to Quiz");

    /**
     * Constructs a custom alert.
     */
    public LastQuestionAlert() {
        // setup alert
        super(AlertType.CONFIRMATION, "That was the last question.");
        this.setHeaderText("Quiz Complete!");

        // change buttons
        this.getButtonTypes().removeAll(this.getButtonTypes());
        this.getButtonTypes().addAll(SEE_RESULTS, GO_BACK);

        // set style
        this.getDialogPane().getStylesheets().add(Main.mainTheme);
    }
}
