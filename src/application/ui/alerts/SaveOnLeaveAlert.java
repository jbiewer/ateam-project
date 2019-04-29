package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * An alert that prompts the user if they wish to save before leaving a scene.
 */
public class SaveOnLeaveAlert extends CustomAlert {

    /**
     * Constructs a custom alert.
     */
    public SaveOnLeaveAlert() {
        // setup alert
        super(AlertType.INFORMATION);
        this.setTitle("Save Results");
        this.setHeaderText("Save results before leaving?");

        // change buttons
        this.getButtonTypes().removeAll(this.getButtonTypes());
        this.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
    }

}
