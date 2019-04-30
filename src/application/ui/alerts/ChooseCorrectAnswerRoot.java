package application.ui.alerts;

import java.util.ArrayList;
import java.util.List;

import application.main.Main;
import application.ui.root.NewQuestionRoot;
import application.ui.root.NewQuestionRoot.CancelSaveHBox;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class ChooseCorrectAnswerRoot extends VBox {
	
	private String[] choices;
	
	public ChooseCorrectAnswerRoot(String[] choices, CancelSaveHBox root){
		
		this.choices = choices;
        this.getChildren().addAll();

        this.getStylesheets().add(Main.mainTheme); // add stylesheet

        // INITIALIZE NODES //
        Label instructions = new Label("Choose the correct answer: ");
        Button cancel = new Button("Cancel");
        Button done = new Button("Done");
        ChoicesBox choicesBox = new ChoicesBox(choices);


        // FUNCTIONALITY //
        done.setDefaultButton(true);
        done.setOnMouseClicked(event -> {
        	//Where is my chosen answer going to be stored
        	if (choicesBox.getChosen() == null) {
        		GUIAlert.quickAlert(AlertType.WARNING, "Select Right Answer", "You must select a correct answer.");
        	}
        	// get all 'wrong' answers
        	List<String> wrongs = new ArrayList<>();
        	choicesBox.getChildren().forEach(child -> {
        		if (!((RadioButton) child).getText().equals(choicesBox.getChosen()))
        			wrongs.add(((RadioButton) child).getText());
        	});
        	// save the new question!
        	root.saveQuestion(choicesBox.getChosen(), wrongs.toArray(new String[0]));
        	Main.closeCurrentDialogScene();
        	Main.switchScene(GUIScene.TITLE);
        });
        cancel.setOnMouseClicked(event -> Main.closeCurrentDialogScene());

        // SETUP LAYOUT //
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        
        this.getChildren().setAll(instructions, choicesBox, cancel, done);
	}
	
	/**
     * Custom root node to organize the list of choices for the question.
     */
    public class ChoicesBox extends VBox {
        private ToggleGroup group;

        private ChoicesBox(String[] choices) { 
        	this.group = new ToggleGroup();

        	//Add radio buttons
	    	for (String choice : choices) { // add the new choices as children.
	            RadioButton rb = new RadioButton(choice);
	            rb.setWrapText(true);
	            this.group.getToggles().add(rb); // add button to toggle group
	    	}
	    	this.group.getToggles().forEach(toggle -> this.getChildren().add((RadioButton) toggle));
        }

        public String getChosen() {
            if(group.getSelectedToggle() == null) return null;
            else return ((RadioButton) group.getSelectedToggle()).getText();
        }
    }
}
