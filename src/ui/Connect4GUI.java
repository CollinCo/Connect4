package ui;
import core.Connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// JavaFX
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;

/**
 * Handles the UI for the Connect 4 game.
 * Created using JavaFX
 * @author collin
 */
public class Connect4GUI extends Application {
	
	static final int TILE_SIZE = 80;
	static final int COLUMNS = 7;
	static final int ROWS    = 6;
	private boolean  redMove  = true;
	private boolean pvp;
	private int totalTokens = 0;
	
	private Scene intro;
	private Scene exit;
	private Stage window;
	
	private Connect4 board;
	private Disc[][] grid = new Disc[COLUMNS][ROWS]; 
	private Pane discRoot = new Pane();
    private Label lblStatus = new Label("Red's turn to play");
	
    /**
     * Starts up game with new window that prompts users to either play
     * player versus player or player versus computer.
     */
	public void start(Stage stage) throws Exception {
		
		window = stage;
		Label label1 = new Label("Welcome to connect 4");
		Button pvp = new Button("Player Versus Player");
		Button pve = new Button("Player Versus Computer");
		
		pvp.setOnAction(e -> window.setScene(new Scene(createContent(true))));
		pve.setOnAction(e -> window.setScene(new Scene(createContent(false))));

		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1, pvp, pve);
		intro = new Scene(layout1, 200, 200);
		
		stage.show();
		window.setScene(intro);
		window.show();
	}
	
	/**
	 * Creates board with pane
	 * @param isPvp - true = player versus player; false = player versus computer
	 * @return the completed pane.
	 */
	private Parent createContent(boolean isPvp) {
		board = isPvp ? new Connect4(0) : new Connect4(1);
		pvp = isPvp;
		
		Pane root = new Pane();
		root.getChildren().add(discRoot);
		Shape gridShape = buildGrid();
		root.getChildren().add(gridShape);
		root.getChildren().addAll(makeColumns());
		 
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(root);
		
		borderPane.setBottom(lblStatus);
		
		return borderPane;
	}
	/**
	 * Creates the background shape for the discs to fit into
	 * @return the completed grid
	 */
	private Shape buildGrid() {
		Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);
		
		for(int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Circle circle = new Circle(TILE_SIZE / 2);
				circle.setCenterX(TILE_SIZE / 2);
				circle.setCenterY(TILE_SIZE / 2);
				circle.setTranslateX(j * (TILE_SIZE + 5) + TILE_SIZE / 4);
				circle.setTranslateY(i * (TILE_SIZE + 5) + TILE_SIZE / 4);
				shape = Shape.subtract(shape, circle);
			}
		}
		shape.setFill(Color.BLUE);
		return shape;
	}
	
	/**
	 * builds the grid of disc objects. These discs are the game peices
	 * @return
	 */
	private List<Rectangle> makeColumns(){
		List<Rectangle> list = new ArrayList<>();
		for(int i = 0; i < COLUMNS; i++) {
			Rectangle rect = new Rectangle((TILE_SIZE), (ROWS + 1) * TILE_SIZE);
			rect.setTranslateX(i * (TILE_SIZE + 5) + TILE_SIZE / 4);
			rect.setFill(Color.TRANSPARENT);
			
			rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(50, 50, 200, .3)));
			rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));
			
			final int column = i;
			rect.setOnMouseClicked(e -> placeDisc(new Disc(redMove), column));
			list.add(rect);
		}
		return list;
	}
	/**
	 * Handles players taking turns placing discs in the columns.
	 * @param disc - disc to be placed
	 * @param column - column that the disc should be placed in
	 */
	private void placeDisc(Disc disc, int column) {
		int row = ROWS - 1;
		int compTurn = -1;
		if(totalTokens == 41) {
			gameOver();
		}
		
		// if comp turn
		if(!redMove && !pvp) {
			compTurn = board.takeTurnGUI(redMove, column, ROWS - row - 1);
			column = compTurn;
		} else {
			board.takeTurnGUI(redMove, column, ROWS - row);
		}
		
		do {
			if(!getDisc(column, row).isPresent()) {
				break;
			}
			row--;
			
 		} while(row >= 0);
		
		if(row < 0) {
			return;
		}
		
		boolean win = Connect4.checkWin(redMove ? 1 : 0);
		
		if(compTurn != -1) 
			column = compTurn;
		
		grid[column][row] = disc;
		discRoot.getChildren().add(disc);
		disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);
		disc.setTranslateY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
		

		redMove = !redMove;
		
		if(pvp) {
			if(!redMove) {
				lblStatus.setText("Yellow's turn to play!");
			} else {
				lblStatus.setText("Red's turn to play!");
			}
		} else {
			if(!redMove) {
				lblStatus.setText("Computer's turn, click to continue");
			} else {
				lblStatus.setText("Your turn to play!");
			}
		}
		totalTokens++;
	}
	
	/**
	 * Game over screen - Displays winner and exit button
	 */
	private void gameOver() {
		Label endLabel = new Label();
		
		if(totalTokens == 41) 
			endLabel.setText("Draw: board full");
		else if(pvp)
			endLabel.setText("Winner: " + (redMove ? "Yellow" : "Red"));
		else
			endLabel.setText("Winner: " + (redMove ? "Computer" : "You"));
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> window.close());
		
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(endLabel, exitButton);
		exit = new Scene(layout1, 200, 200);
		window.setScene(exit);		
	}

	
	/**
	 * Returns whether a disc object in the slot at the passed in row and column
	 * @param column - x coord of disc
	 * @param row - y cood of disc
	 * @return - returns the disc object at the point if it exists
	 */
	private Optional<Disc> getDisc(int column, int row){
		
		if(column < 0 || column >= COLUMNS || row < 0 || row >= ROWS) {
			return Optional.empty();
		}
		
		return Optional.ofNullable( grid[column][row]);
	}
	
	private static class Disc extends Circle {
		
		private final boolean red;
		
		public Disc(boolean red) {
			super((TILE_SIZE / 2), red ? Color.RED : Color.YELLOW);
			this.red = red;
			
			setCenterX(TILE_SIZE / 2);
			setCenterY(TILE_SIZE / 2);		
		}
	}
	
	/**
	 * Runs startup
	 */
	public static void main(String args[]) {
		launch(args);
	}

}