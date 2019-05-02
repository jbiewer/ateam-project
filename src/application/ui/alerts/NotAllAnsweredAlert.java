package application.ui.alerts;

/**
 * An alert that prompts the user that they haven't answered all their questions.
 */
public class NotAllAnsweredAlert extends CustomAlert {

    /**
     * Constructs a custom alert.
     */
    public NotAllAnsweredAlert() {
        // setup alert
        super(AlertType.WARNING);
        this.setTitle("Not All Questions Answered");
        this.setHeaderText("Not all the questions in the quiz are answered.");
    }

}
