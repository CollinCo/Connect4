package ui;
import core.Connect4;
// JavaFX
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;




public class Connect4GUI extends Application {
	
	// Dimensions of the Grid
	final int WIDTH  = 7;
	final int HEIGHT = 6;

	// Indicate which player has a turn, initially it is the X player
    private char whoseTurn = 'X';
    
    // Create and initialize cell
    private Cell[][] cell = new Cell[HEIGHT][WIDTH];
    
    // Create and initialize a status label
    private Label lblStatus = new Label("Red's turn to play");
 
    // main created for testing
	public static void main(String args[]) {
		launch(args);
	}
 
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
    
    	// Pane to hold cell grid
    	GridPane pane = new GridPane();
	
    	for (int i = 0; i < HEIGHT; i++) {
    		for(int j = 0; j < WIDTH; j++) {
     			pane.add(cell[i][j] = new Cell(), j, i);
    		}
		}
    	
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(lblStatus);
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 700, 600);
		primaryStage.setTitle("Connect4"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
    }
    
  //Case Study: Developing a Tic-Tac-Toe Game


    // An inner class for a cell
    public class Cell extends Pane 
    {
        // Token used for this cell
        private char token = ' ';

        public Cell() {
    	 	setStyle("-fx-border-color: black");
    	 	this.setPrefSize(2000, 2000);
    	 	this.setOnMouseClicked(e -> handleMouseClick());
        }

        /** Return token */
        public char getToken() {
        	return token;
        }

        /** Set a new token */
        public void setToken(char c) {
    	 	token = c;
    	
    	 	if (token == 'X') {
    	 	    Ellipse ellipse = new Ellipse(this.getWidth() / 2,
    	 	    this.getHeight() / 2, this.getWidth() / 2 - 10,
    	 	    this.getHeight() / 2 - 10);
    	 	    ellipse.centerXProperty().bind(this.widthProperty().divide(2));
    	 	    ellipse.centerYProperty().bind(this.heightProperty().divide(2));
    		    ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
    	 	    ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
    		    ellipse.setStroke(Color.RED);
    	 	    ellipse.setFill(Color.RED);
    		    getChildren().add(ellipse); // Add the ellipse to the pane
    	 	}
    	 	else if (token == 'O') {
    	 	    Ellipse ellipse = new Ellipse(this.getWidth() / 2,
    	 	    this.getHeight() / 2, this.getWidth() / 2 - 10,
    	 	    this.getHeight() / 2 - 10);
    	 	    ellipse.centerXProperty().bind(this.widthProperty().divide(2));
    	 	    ellipse.centerYProperty().bind(this.heightProperty().divide(2));
    		    ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
    	 	    ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
    		    ellipse.setStroke(Color.GREEN);
    	 	    ellipse.setFill(Color.GREEN);
    		    getChildren().add(ellipse); // Add the ellipse to the pane
    	 	}
        }

        private boolean isWon(char player) {
        	return false;
        }
        
        private boolean isFull() {
        	return false;
        }
        
        private String turnToString(char whoseTurn) {
        	return (whoseTurn == 'X') ? "Red" : "Green";
        }
        
        /* Handle a mouse click event */
        private void handleMouseClick() {
        	
        	
        	
        	// If cell is empty and game is not over
        	if (token == ' ' && whoseTurn != ' ') {
        		
        		
        		setToken(whoseTurn); // Set token in the cell
    	
    	 	    // Check game status
    	 	    if (isWon(whoseTurn)) {
    	 	    	lblStatus.setText(turnToString(whoseTurn) + " won! The game is over");
    	 	    	whoseTurn = ' '; // Game is over
    	 	    }
    	 	    else if (isFull()) {
    	 	    	lblStatus.setText("Draw! The game is over");
    	 	    	whoseTurn = ' '; // Game is over
    	 	    }
    	 	    else {
    	 	    	// Change the turn
    	 	    	whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
    	 	    	// Display whose turn
    	 	    	lblStatus.setText(turnToString(whoseTurn) + "'s turn");
    	  	    }
        	}
        }	
    }
 }
