package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;

/**
 * Non-instantiable alert class used for all the custom alerts.
 */
abstract class CustomAlert extends Alert {

    /**
     * Custom alert constructor
     */
    CustomAlert(AlertType type) {
        super(type);
        this.setWidth(350);
        this.setHeight(200);
        this.getDialogPane().getStylesheets().add(Main.alertTheme);
    }
}
