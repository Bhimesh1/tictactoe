package dev.bhim.tictactoe.core;

// prepared a easy-to-read Board.
// internally its a 3x3 char grid with: 'X', 'O', or ' ' (blank).

public class Board {

    private final char[][] grid = new char[3][3];

    public Board() {
        // I initialized everything to blank spaces.
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                grid[r][c] = ' ';
            }
        }
    }

    //I have  keept this simple getter so tests and UI can read a cell.
    public char get(int row, int col) {
        checkBounds(row, col);
        return grid[row][col];
    }

    // Small helper: validating indexes
    private static void checkBounds(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new IllegalArgumentException("row/col must be 0..2");
        }
    }
}
