package application.ui.alerts;

import application.main.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Non-instantiable alert class used for all the custom alerts.
 */
abstract class CustomAlert extends Alert {
    private static final int WIDTH = 350,
            HEIGHT = 200;

    /**
     * Custom alert constructor
     */
    CustomAlert(AlertType type) {
        super(type);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.getDialogPane().getStylesheets().add(Main.alertTheme);
    }

    /**
     * For quick alerts with little formatting, this method sets one of those up and shows it to the user.
     * @param type Alert type.
     * @param title Title of the alert.
     * @param header Header text of the alert.
     * @return The ButtonType clicked by the user.
     */
    public static Optional<ButtonType> quickAlert(AlertType type, String title, String header) {
        Alert a = new Alert(type);
        a.setWidth(WIDTH);
        a.setHeight(HEIGHT);
        a.setTitle(title);
        a.setHeaderText(header);
        a.getDialogPane().getStylesheets().add(Main.alertTheme);
        return a.showAndWait();
    }


}
