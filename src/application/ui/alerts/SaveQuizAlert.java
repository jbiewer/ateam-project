package application.ui.alerts;

import javafx.scene.control.ButtonType;

public class SaveQuizAlert extends CustomAlert {

    /**
     * Constructs a new custom alert that allows the user to save the quiz questions
     */
    public SaveQuizAlert(){
        super(AlertType.INFORMATION);
        this.setTitle("Save Quiz Questions");
        this.setHeaderText("Save quiz questions to 'Questions' directory?");

        // change buttons
        this.getButtonTypes().removeAll(this.getButtonTypes());
        this.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
    }
}
