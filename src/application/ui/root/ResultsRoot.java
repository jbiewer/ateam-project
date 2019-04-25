package application.ui.root;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// Parent to be changed to whatever layout we want to use later.
public class ResultsRoot extends VBox {
	
	private HBox middle;
	
    public ResultsRoot() {
        // Initialize nodes
    Label summary = new Label("Your results: ");
    HBox percentageBox = new percentageBox();
    HBox results = new resultsBox();
    }
    
    private class percentageBox extends HBox {
    	private percentageBox() {
    		//Get number of correct answers
    		
    		//int numQuestions = this.totalNumQuestions;
    		
    		//this.middle = new HBox(numCorrect, numQuestions, percentage);
    		
    		// Set style?
    		
    	}
    }
    
    private class resultsBox extends HBox {
    	private resultsBox() {
    	// 
    	//
    	}
    }
    
}
