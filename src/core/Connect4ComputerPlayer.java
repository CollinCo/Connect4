package core;
import java.util.Random;
/**
 * Computer player to interact with Connect4.java
 * Computer takes turns by deciding which row to export a move
 * to randomly.
 * @author Collin Connolly
 * @version 1.0
 */
public class Connect4ComputerPlayer {

    private final int HEIGHT = 6;
    private final int WIDTH = 7;
    private final char BLANK = ' ';

    private static Random rand = new Random();

    /**
     * Computer algorithm to take a turn for connect 4
     * @param board - 2d array of chars representing the board
     * @return - row that the computer chose for the turn
     */
    public int takeTurn(char[][] board){

        int off = checkBoard(board, 'X');
        if(off != -1)
            return off;

        int def = checkBoard(board, 'O');
        if(def != -1)
            return def;
        // No potential win - random row
        return Math.abs(rand.nextInt() % 6 + 1);
    }

    /**
     * Checks for smart moves for the robot to make
     * @param board - current layout of the connect 4 board
     * @param icon - player number, checks
     * @return row num for icon to be placed
     */
    private int checkBoard(char[][] board, char icon){
        for (int j = 0; j < HEIGHT - 3; j++ ){
            for (int i = 0; i < WIDTH; i++){
                if (board[i][j] == icon &&
                        board[i][j + 1] == icon &&
                        board[i][j + 2] == icon &&
                        board[i][j + 3] == BLANK) {
                    return j + 3;
                }

            }
        }
        // vertical check
        for (int i = 0; i < WIDTH; i++ ){
            for (int j = 0; j < HEIGHT; j++){
                if (board[i][j] == icon &&
                        board[i + 1][j] == icon &&
                        board[i + 2][j] == icon &&
                        board[i + 3][j] == BLANK) {
                    return j;
                }
            }
        }
        // ascending diagonal check
        for (int i = 3; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT - 3; j++){
                if (board[i][j] == icon &&
                        board[i - 1][j + 1] == icon &&
                        board[i - 2][j + 2] == icon &&
                        board[i - 3][j + 3] == BLANK) {
                    return j + 3;
                }
            }
        }
        // descending diagonal check
        for (int i = 3; i < WIDTH; i++){
            for (int j = 3; j < HEIGHT; j++){
                if (board[i][j] == icon &&
                        board[i - 1][j - 1] == icon &&
                        board[i - 2][j - 2] == icon &&
                        board[i - 3][j - 3] == BLANK) {
                    return j - 3;
                }
            }
        }
        return -1;
    }
}