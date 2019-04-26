package application.ui.root;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class LastQuestionAlert extends Alert {

    public static final ButtonType SEE_RESULTS = new ButtonType("See Results"),
            GO_BACK = new ButtonType("Go Back to Quiz");

    public LastQuestionAlert() {
        super(AlertType.CONFIRMATION);
        this.setHeaderText("Quiz Complete!");
        this.setContentText("That was the last question.");

        // remove current buttons, add custom buttons
        this.getButtonTypes().removeAll(this.getButtonTypes());
        this.getButtonTypes().addAll(SEE_RESULTS, GO_BACK);
    }
}
