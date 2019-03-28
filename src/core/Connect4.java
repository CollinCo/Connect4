// Collin Connolly
// Project 2
package core;
import java.util.Scanner;

/**
 * Handles the logic of the game "Connect 4"
 * contains methods to handle turn taking and returns whether a win is detected
 * contains a method to return the current layout of the board as a string
 * @author Collin Connolly
 * @version 4
 */
public class Connect4 {

    private final static int WIDTH = 7;
    private final static int HEIGHT = 6;
    private static Scanner scan = new Scanner(System.in);
    private static char[][] board = new char[WIDTH + 1][HEIGHT + 1];
    private static Connect4ComputerPlayer computer = new Connect4ComputerPlayer();
    private static int pvp; // 0 = pvp   1 = computer

    /**
     * Creates new empty connect4 board
     * @param mode - vs. player == 0 | vs. computer == 1
     */
    public Connect4(int mode) {

        pvp = mode;

        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                board[i][j] = ' ';
            }
        }
    }
    /***
     * Player takes a turn to place either an X or an O tile
     * After placing, the function checkWin is called to check if a player wins
     * @param turn turn num gets calculated to playerNum - determines to place an X or O if odd or even turn
     * @return whether or not a player won - won = true, no winner = false
     */
    public static boolean takeTurn(int turn) {

        int row;

        // Taking in input from player
        if(pvp == 1 && turn % 2 == 0 || pvp == 0) {
            System.out.print("Enter Row (1 - 7): ");
            row = 0;
            try {
                row = scan.nextInt() - 1;
            } catch (Exception e) {
                System.out.println("\nPlease enter an integer!\n");
                scan.nextLine();
                return takeTurn(turn);
            }
        }
        // Taking in input from computer
        else {
            row = computer.takeTurn(board);
        }

        // placing tile
        if(row < WIDTH && row >= 0) {
            if (board[HEIGHT - 1][row] != ' ') {
                System.out.println("\nRow Full, input valid row\n");
                return takeTurn(turn);
            }

            // testing for full column
            for (int i = 0; i < HEIGHT; i++) {
                if (board[i][row] == ' ') {
                    if (turn % 2 == 0) {
                        board[i][row] = 'O';
                        break;
                    } else {
                        board[i][row] = 'X';
                        break;
                    }
                }
            }
        }

        // User entered num that was out of bounds
        else {
            System.out.println("\nInvalid row, please choose another row.\n");
            return takeTurn(turn);
        }

        // tile successfully placed - returns checkWin bool val
        return checkWin(turn);
    }
    /**
     * turn method used by Connect4GUI class
     * PLaces a token in the grid either by a player or by a computer
     * @param isRed - player turn - Red = player, !Red = yellow (or computer)
     * @param col - column chip is placed
     * @param row - row chip is placed
     * @return column chip is placed at in finish
     */
    public int takeTurnGUI(boolean isRed, int col, int row) {
    	
    	// computer turn
    	if(!isRed && pvp == 1) {
    		int computerTurn = computer.takeTurn(board);
    		
    		for(int i = 0; i < HEIGHT; i++) {
    			if(board[i][computerTurn] == ' ') {
    	    		board[i][computerTurn] = 'O';
    	    		return computerTurn;
    			}
    		}
    	}
    		
    	// Placing player token for backend
    	for(int i = 0; i < HEIGHT; i++) {
            if (board[i][col] == ' ') {
		    	if(isRed) {
		    		board[i][col] = 'X';
		    		return 0;
		    	} else { 
		    		board[i][col] = 'O';
		    		return 0;
		    	}
            }
    	}
    	return -1;
    }

    /***
     * Checks for a win.
     * Checks for 4 of the same icon in a row (X X X X) or (O O O O)
     * Checks horizontal, vertical, and both diagonals
     * @param turn - player that has currently went - icon to check for
     * @return whether or not a player wins
     */
    public static boolean checkWin(int turn) {

        // Sets icon to check for win
        char icon;
        if(turn % 2 == 0)
            icon = 'O';
        else
            icon = 'X';

        // horizontal check
        for (int j = 0; j < HEIGHT - 2; j++ ){
            for (int i = 0; i < WIDTH; i++){
                if (board[i][j] == icon && 
                		board[i][j + 1] == icon && 
                		board[i][j + 2] == icon && 
                		board[i][j + 3] == icon){
                    return true;
                }
            }
        }
        // vertical check
        for (int i = 0; i < WIDTH; i++ ){
            for (int j = 0; j < HEIGHT + 1; j++){
                if (board[i][j] == icon &&
                        board[i + 1][j] == icon &&
                        board[i + 2][j] == icon &&
                        board[i + 3][j] == icon){
                    return true;
                }
            }
        }
        // ascending diagonal check
        for (int i = 3; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT - 3; j++){
                if (board[i][j] == icon &&
                        board[i - 1][j + 1] == icon &&
                        board[i - 2][j + 2] == icon &&
                        board[i - 3][j + 3] == icon)
                    return true;
            }
        }
        // descending diagonal check
        for (int i = 3; i < WIDTH; i++){
            for (int j = 3; j < HEIGHT; j++){
                if (board[i][j] == icon &&
                        board[i - 1][j - 1] == icon &&
                        board[i - 2][j - 2] == icon &&
                        board[i - 3][j - 3] == icon)
                    return true;
            }
        }

        // no winner found
        return false;
    }

    /**
     * Returns a string that prints the layout of the board
     * @return Current layout of board
     */
    public String toString() {
        String temp = "_1_2_3_4_5_6_7_\n";
        for(int i = HEIGHT; i > 0; i--) {
            temp += "|";
            for(int j = 0; j < WIDTH; j++) {
                temp += board[i - 1][j] + "|";
            }
            temp += "\n";
        }
        return temp;
    }
}