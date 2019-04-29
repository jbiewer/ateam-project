package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;

/**
 * An alert that prompts the user that they have wrong input in certain fields.
 */
public class InputFormatAlert extends CustomAlert {

    /**
     * Constructs a custom alert.
     */
    public InputFormatAlert() {
        // setup alert
        super(Alert.AlertType.WARNING);
        this.setTitle("Wrong Input");
        this.setHeaderText("One of the input fields is either incorrect or not filled in!");
    }

}
