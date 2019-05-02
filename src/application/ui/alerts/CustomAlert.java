package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;

/**
 * Non-instantiable alert class used for all the custom alerts.
 */
public abstract class CustomAlert extends Alert {
    public static final int WIDTH = 350, // std dimensions for alerts
            HEIGHT = 200;

    /**
     * Custom alert constructor.
     */
    CustomAlert(AlertType type) {
        super(type);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.getDialogPane().getStylesheets().add(Main.ALERT_THEME);
    }
}
