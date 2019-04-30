package application.ui.util;

import application.ui.alerts.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Enumeration that holds alerts that can be used whenever and wherever.
 */
public enum GUIAlert {
    INPUT_FORMAT(InputFormatAlert.class),
    LAST_QUESTION(LastQuestionAlert.class),
    SAVE_ON_LEAVE(SaveOnLeaveAlert.class),
    NOT_ALL_ANSWERED(NotAllAnsweredAlert.class),
    SAVE_QUIZ(SaveQuizPopupRoot.class);

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
}
