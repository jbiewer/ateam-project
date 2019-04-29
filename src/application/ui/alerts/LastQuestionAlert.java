package application.ui.alerts;

import javafx.scene.control.ButtonType;

/**
 * An alert that prompts the user if they wish to see quiz results or go back to the questions.
 */
public class LastQuestionAlert extends CustomAlert {

    // static constants to refer to button types from external classes.
    public static final ButtonType SEE_RESULTS = new ButtonType("See Results"),
            GO_BACK = new ButtonType("Back to Quiz");

    /**
     * Constructs a custom alert.
     */
    public LastQuestionAlert() {
        // setup alert
        super(AlertType.CONFIRMATION);
        this.setTitle("Last Question");
        this.setHeaderText("Quiz complete!");

        // change buttons
        this.getButtonTypes().removeAll(this.getButtonTypes());
        this.getButtonTypes().addAll(SEE_RESULTS, GO_BACK);
    }
}
