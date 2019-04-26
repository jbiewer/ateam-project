package application.ui.root;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SaveOnLeaveAlert extends Alert {

    public SaveOnLeaveAlert() {
        super(AlertType.INFORMATION, "Save results before leaving?");
        this.getButtonTypes().removeAll(this.getButtonTypes());
        this.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
    }

}
