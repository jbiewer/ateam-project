package application.ui.root;

import java.util.Arrays;

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

        this.getChildren().addAll(summary, numCorrectBox, percentCorrectBox, decisionBtn);
    }
    
    /**
     * Custom root for number of questions correct
     */
    private class numCorrectBox extends HBox {
    	private TextField numCorrect;
    	//But should this be a textfield because user won't be inputting this so
    	//how should I define this?
    	
    	/**
    	 * Constructor
    	 */
    	private numCorrectBox() {
    		
    		Label num = new Label("Number correct: ");
    		this.numCorrect = new TextField();
    		
    		//Functionality not implemented yet
    		//Get number of correct answers    		

    		//Style
    		num.getStyleClass().add("main-text");
    		
    		//Add children
    		this.getChildren().addAll(num, this.numCorrect);
    	}
    	
    	//numCorrect should be obtained not input by the user so I shouldn't
    	//have to check it right?
    	    	
    }
    
    /**
     * Custom root for percent of questions correct
     */    
    private class percentCorrectBox extends HBox {
    	private TextField percentCorrect;
    	
    	/**
    	 * Constructor
    	 */
    	private percentCorrectBox() {
    		Label percent = new Label("Percent correct: ");
    		this.percentCorrect = new TextField();
    		
    		//Functionality not implemented yet
    		//Get number of correct answers
    		//Get total number of questions
    		//Calculate and display percentage

    		//Style
    		percent.getStyleClass().add("main-text");
    		
    		//Add children
    		this.getChildren().addAll(percent, this.percentCorrect);
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

            // Style
            newQuiz.getStyleClass().add("btn-large");
            tryAgain.getStyleClass().add("btn-large");

            //Add children
            this.getChildren().addAll(newQuiz, tryAgain);
    	}

    }
    
}
