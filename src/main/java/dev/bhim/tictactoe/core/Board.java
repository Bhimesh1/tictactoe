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

    // placed 'X' or 'O' in a empty cell.
    public void place(int row, int col, char symbol) {
        checkBounds(row, col);
        if (symbol != 'X' && symbol != 'O') {
            throw new IllegalArgumentException("symbol must be 'X' or 'O'");
        }
        if (grid[row][col] != ' ') {
            throw new IllegalStateException("cell already taken");
        }
        grid[row][col] = symbol;
    }

    // True when there are no blanks left.
    public boolean isFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[r][c] == ' ') return false;
            }
        }
        return true;
    }

    // Returns 'X' or 'O' if someone has a 3-in-a-row; otherwise ' ' (Blank).
    public char winner() {
        // rows
        for (int r = 0; r < 3; r++) {
            char a = grid[r][0], b = grid[r][1], c = grid[r][2];
            if (a != ' ' && a == b && b == c) return a;
        }
        // cols
        for (int c = 0; c < 3; c++) {
            char a = grid[0][c], b = grid[1][c], d = grid[2][c];
            if (a != ' ' && a == b && b == d) return a;
        }
        // diagonals
        char m = grid[1][1];
        if (m != ' ') {
            if (grid[0][0] == m && grid[2][2] == m) return m;
            if (grid[0][2] == m && grid[2][0] == m) return m;
        }
        return ' ';
    }

    // Prints the board
    public void print() {
        for (int r = 0; r < 3; r++) {
            System.out.printf(" %c | %c | %c %n", grid[r][0], grid[r][1], grid[r][2]);
            if (r < 2) {
                System.out.println("---+---+---");
            }
        }
    }



}
