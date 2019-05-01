package application.ui.root;

import java.util.Arrays;

import application.main.Main;
import application.ui.alerts.SaveOnLeaveAlert;
import application.ui.util.GUIAlert;
import application.ui.util.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Root node for ResultsRoot scene.
 * @author Jack Prazich
 */
public class ResultsRoot extends VBox {
	private NumCorrectBox numCorrectBox;
	private PercentCorrectBox percentCorrectBox;

	/**
	 * Constructor
	 */
    public ResultsRoot() {
        // Initialize nodes
        Label summary = new Label("Results");
        this.numCorrectBox = new NumCorrectBox();
        this.percentCorrectBox = new PercentCorrectBox();
        HBox decisionBtn = new NewDecisionButtonBox();

        //Stream in all HBoxes and center all of them
        Arrays.stream(new HBox[] { this.numCorrectBox, this.percentCorrectBox, decisionBtn })
        .forEach(box -> box.setAlignment(Pos.CENTER));
        this.setSpacing(50);

        this.getChildren().addAll(summary, numCorrectBox, percentCorrectBox, decisionBtn);

        Main.quizManager.loadResults(this);
    }
    
    /**
     * Custom root for number of questions correct
     */
    public class NumCorrectBox extends HBox {
    	private Label numCorrect;
    	
    	/**
    	 * Constructor
    	 */
    	private NumCorrectBox() {
    		
    		Label num = new Label("Number correct: ");
    		this.numCorrect = new Label( "< x >");
    		
    		//Functionality not implemented yet
    		//Get number of correct answers    		

    		//Add children
    		this.getChildren().addAll(num, numCorrect);
    	}

		/**
		 * Sets the text to display of the numCorrect label.
		 * @param correct Number of questions correct.
		 * @param total The total number of questions.
		 */
		public void setNumCorrect(int correct, int total) {
    		this.numCorrect.setText(correct + " / " + total);
		}
    	
    	//numCorrect should be obtained not input by the user so I shouldn't
    	//have to check it right?
    	    	
    }
    
    /**
     * Custom root for percent of questions correct
     */    
    public class PercentCorrectBox extends HBox {
    	private Label percentCorrect;

    	/**
    	 * Constructor
    	 */
    	private PercentCorrectBox() {
    		Label percent = new Label("Percent correct: ");
    		this.percentCorrect = new Label("< % >");
    		
    		//Functionality not implemented yet
    		//Get number of correct answers
    		//Get total number of questions
    		//Calculate and display percentage

    		//Add children
    		this.getChildren().addAll(percent, percentCorrect);
    	}

		/**
		 * Sets the text to display to the percentCorrect label.
		 * @param percent Percent to display.
		 */
		public void setPercentCorrect(float percent) {
    		this.percentCorrect.setText(String.valueOf(
    				Math.round(percent*10000f)/100f
			) + "%");
		}
    }

    /**
     * Custom root for button layout at bottom of scene
     */    
    private class NewDecisionButtonBox extends HBox {
    	/**
    	 * Constructor
    	 */
    	private NewDecisionButtonBox() {
            Button newQuiz = new Button("New Quiz");
            Button tryAgain = new Button("Try again");

            // functions
			newQuiz.setOnMouseClicked(event -> {
				if (GUIAlert.SAVE_ON_LEAVE.alert().get() == ButtonType.YES)
					Main.quizManager.saveResults();
				Arrays.stream(Main.questionBank.getAllQuestions()).forEach(q -> q.setChosen(null)); // clear all answers
				Main.switchScene(GUIScene.TITLE);
			});
			tryAgain.setOnMouseClicked(event -> {
				if (GUIAlert.SAVE_ON_LEAVE.alert().get() == ButtonType.YES)
					Main.quizManager.saveResults();
				Arrays.stream(Main.questionBank.getAllQuestions()).forEach(q -> q.setChosen(null)); // clear all answers
				Main.switchScene(GUIScene.QUIZ_SETTINGS);
			});

            //Add children
            this.getChildren().addAll(newQuiz, tryAgain);
    	}

    }

	/**
	 * @return HBox of the number of correct questions.
	 */
	public NumCorrectBox getNumCorrectBox() {
		return numCorrectBox;

	}

	/**
	 * @return HBox of the percent of correct questions.
	 */
	public PercentCorrectBox getPercentCorrectBox() {
		return percentCorrectBox;
	}
}
