/*
	Anthony Thorpe
	COP 3503
	Programming assignment #2 - tentaizu
	2/2/2022
*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class tentaizu {
    private char[][] board;
    private final int NUM_ROWS = 7;
    private final int NUM_COL = 7;
    private int NUM_BOMBS;
    private int numBoards;
    
    public static void main(String[] args) throws IOException {
        tentaizu t = new tentaizu();
    }

    // read in and generate the finished tentaizu boards
    public tentaizu() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String numB = reader.readLine();
        numBoards = Integer.parseInt(numB);
        NUM_BOMBS = 10;

        // loop through each board and read them in
        for(int i = 0; i < numBoards; i++) {

            board = new char[NUM_ROWS][NUM_COL];

            for(int j = 0; j < NUM_ROWS; j++) {

                String[] tmp = reader.readLine().split("");
                for(int k = 0; k < tmp.length; k++) {
                    
                    board[j][k] = tmp[k].charAt(0);

                }

            }

            System.out.println("Tentaizu Board #" + (i + 1) + ":");

            //Long currTime = System.currentTimeMillis();
            if(solve(0, 0, NUM_BOMBS)) printBoard(); 
            //Long finalTime = System.currentTimeMillis();
            //System.out.println(finalTime - currTime);
            System.out.println();        

            // read in the separator
            String sep = reader.readLine();

        }

    }

    public boolean solve(int row, int col, int NUM_BOMBS) {
        
        if(row == NUM_ROWS && NUM_BOMBS != 0) {
            return false;
        }

        if(row == NUM_ROWS - 1 && col == NUM_COL - 1 && NUM_BOMBS > 1) {
            return false;
        }

        if(NUM_BOMBS < 0)
            return false;

        // check board from previous state/function call
        if(!verifyBoard(row, col, NUM_BOMBS)) {
            return false;
        }

        if(NUM_BOMBS == 0) {
            return true;
        }

        boolean result;

        // on last square
        if(row == NUM_ROWS - 1 && col == NUM_COL - 1) {
            if(board[row][col] != '.') {
                return solve(row + 1, 0, NUM_BOMBS);
            } else {
                board[row][col] = '*';
                result = solve(row, col, NUM_BOMBS - 1);
                if(result) return true;
                else {
                    board[row][col] = '.';
                    result = solve(row + 1, 0, NUM_BOMBS);
                    if(result) return true;
                    else return false;
                }
            }
        }

        // at the end of row
        if(col == NUM_COL - 1) {
            if(board[row][col] != '.') {
                return solve(row + 1, 0, NUM_BOMBS);
            } else {
                board[row][col] = '*';
               // printBoard();
                result = solve(row + 1, 0, NUM_BOMBS - 1);

                if(result) return true;
                else {
                    board[row][col] = '.';
                   // printBoard();
                    result = solve(row + 1, 0, NUM_BOMBS);
                    if(result) return true;
                    else return false;
                }
            }
        } else {
            if(board[row][col] != '.') {
                return solve(row, col + 1, NUM_BOMBS);
            } else {
                board[row][col] = '*';
               // printBoard();
                result = solve(row, col + 1, NUM_BOMBS - 1);
                if(result) return true;
                else {
                    board[row][col] = '.';
                   // printBoard();
                    result = solve(row, col + 1, NUM_BOMBS);
                    if(result) return true;
                    else return false;
                }
            }
        }
    }

    // check to see if board is valid
    public boolean verifyBoard(int currRow, int currCol, int NUM_BOMBS) {
        int r, c;
        for(r = 0; r < NUM_ROWS; r++) {
            for(c = 0; c < NUM_COL; c++) {
                if(board[r][c] != '.' && board[r][c] != '*') {
                    int value = Character.getNumericValue(board[r][c]);

                    // if the value was successfully recorded
                    if(value != -1) {
                        // count the number of bombs surrounding the numbered square
                        int bombCount = countBombsSurroundingNum(r, c);
                        
                        // if bombcount is != the number, and all of the bombs have been used, that is clearly false
                        if(NUM_BOMBS == 0 && bombCount != value)
                            return false;
                        // if bombcount is greater than value, that is false
                        if(bombCount > value)
                            return false;
                        // if bombcount is less than number, but the current number is outside the influence of the problem number, then that's false
                        if(r < currRow && c < currCol - 1 && bombCount < value)
                            return false;
                    }
                    
                }
            }
        }

        return true;
    }

    // return the bomb count surrounding a number
    public int countBombsSurroundingNum(int row, int col) {
        int r, c, bombCount = 0;
        for(r = row - 1; r <= row + 1; r++) {
            for(c = col - 1; c <= col + 1; c++) {
                if(inBounds(r, c) && board[r][c] == '*') {
                    bombCount++;
                }
            }
        }

        return bombCount;
    }

    // checks to see if a coordinate is in bounds
    public boolean inBounds(int row, int col) {

        if(row < 0 || col < 0 || row >= NUM_ROWS || col >= NUM_COL)
            return false;
        return true;

    }

    public void printBoard() {
        for(int i = 0; i < board.length; i++) {

            for(int j = 0; j < board[i].length; j++) {

                System.out.print(board[i][j]);

            }

            System.out.println();

        }
    }
}
