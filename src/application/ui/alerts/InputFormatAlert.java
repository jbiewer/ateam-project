package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;

/**
 * An alert that prompts the user that they have wrong input in certain fields.
 */
public class InputFormatAlert extends Alert {

    /**
     * Constructs a custom alert.
     */
    public InputFormatAlert() {
        // setup alert
        super(Alert.AlertType.WARNING, "'Number of Questions' must be an integer.");
        this.setTitle("Input format incorrect!");

        // set style
        this.getDialogPane().getStylesheets().add(Main.mainTheme);
    }

}
