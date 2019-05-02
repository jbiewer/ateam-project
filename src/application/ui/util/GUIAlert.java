package application.ui.util;

import application.main.Main;
import application.ui.alerts.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Enumeration that holds alerts that can be used whenever and wherever.
 */
public enum GUIAlert {
    LAST_QUESTION(LastQuestionAlert.class),
    SAVE_ON_LEAVE(SaveOnLeaveAlert.class),
    NOT_ALL_ANSWERED(NotAllAnsweredAlert.class);

    private Class alert; // alert's class

    /**
     * Stores the alert class.
     * @param alert Class of the alert.
     */
    GUIAlert(Class alert) { this.alert = alert; }

    /**
     * Prompt with the alert.
     */
    public Optional<ButtonType> alert() {
        try {
            return ((Alert) this.alert.newInstance()).showAndWait();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * For quick alerts with little formatting, this method sets one of those up and shows it to the user.
     * @param type Alert type.
     * @param title Title of the alert.
     * @param header Header text of the alert.
     * @return The ButtonType clicked by the user.
     */
    public static Optional<ButtonType> quickAlert(Alert.AlertType type, String title, String header) {
        Alert a = new Alert(type);
        a.setWidth(CustomAlert.WIDTH);
        a.setHeight(CustomAlert.HEIGHT);
        a.setTitle(title);
        a.setHeaderText(header);
        a.getDialogPane().getStylesheets().add(Main.alertTheme);
        return a.showAndWait();
    }
}
