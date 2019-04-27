package application.ui.root;

import java.util.Arrays;

import application.main.Main;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Root node for ResultsRoot scene
 * @author Jack Prazich
 */
public class ResultsRoot extends VBox {
	
	//private fields of HBoxs to be referenced later
	private HBox numCorrectBox;
	private HBox percentCorrectBox;

	/**
	 * Constructor
	 */
    public ResultsRoot() {
        // Initialize nodes
        Label summary = new Label("Results");
        this.numCorrectBox = new numCorrectBox();
        this.percentCorrectBox = new percentCorrectBox();
        HBox decisionBtn = new newDecisionButtonBox();

        summary.getStyleClass().add("header");

        //Stream in all HBoxes and center all of them
        Arrays.stream(new HBox[] { this.numCorrectBox, this.percentCorrectBox, decisionBtn })
        .forEach(box -> {
            box.setAlignment(Pos.CENTER);
        });
        this.setSpacing(50);

        this.getChildren().addAll(summary, numCorrectBox, percentCorrectBox, decisionBtn);
    }
    
    /**
     * Custom root for number of questions correct
     */
    private class numCorrectBox extends HBox {
    	
    	/**
    	 * Constructor
    	 */
    	private numCorrectBox() {
    		
    		Label num = new Label("Number correct: ");
    		Label numCorrect = new Label( "number to be changed");
    		
    		//Functionality not implemented yet
    		//Get number of correct answers    		

    		//Add children
    		this.getChildren().addAll(num, numCorrect);
    	}
    	
    	//numCorrect should be obtained not input by the user so I shouldn't
    	//have to check it right?
    	    	
    }
    
    /**
     * Custom root for percent of questions correct
     */    
    private class percentCorrectBox extends HBox {
    	
    	/**
    	 * Constructor
    	 */
    	private percentCorrectBox() {
    		Label percent = new Label("Percent correct: ");
    		Label percentCorrect = new Label("% to be changed");
    		
    		//Functionality not implemented yet
    		//Get number of correct answers
    		//Get total number of questions
    		//Calculate and display percentage

    		//Add children
    		this.getChildren().addAll(percent, percentCorrect);
    	}
    }
    /**
     * Custom root for button layout at bottom of scene
     */    
    private class newDecisionButtonBox extends HBox {
    	/**
    	 * Constructor
    	 */
    	private newDecisionButtonBox() {
            Button newQuiz = new Button("New Quiz");
            Button tryAgain = new Button("Try again");

            // functions
			newQuiz.setOnMouseClicked(event -> {
				new SaveOnLeaveAlert().showAndWait();
				Main.switchScene(GUIScene.TITLE);
			});
			tryAgain.setOnMouseClicked(event -> {
				new SaveOnLeaveAlert().showAndWait();
				Main.switchScene(GUIScene.QUIZ_SETTINGS);
			});

            //Add children
            this.getChildren().addAll(newQuiz, tryAgain);
    	}

    }
    
}
