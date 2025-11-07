package dev.bhim.tictactoe.core;

public class Game {
    private final Board board = new Board();
    private Player current;
    private Player other;

    public Game(Player x, Player o) {
        this.current = x;
        this.other = o;
    }

    public Board board() {
        return board;
    }

    // Makes a move for the current player.
    public void playMove(int row, int col) {
        board.place(row, col, current.symbol());
        swapPlayers();
    }

    private void swapPlayers() {
        Player tmp = current;
        current = other;
        other = tmp;
    }

    // Returns current game status.
    public GameStatus status() {
        char w = board.winner();
        if (w == 'X') return GameStatus.X_WINS;
        if (w == 'O') return GameStatus.O_WINS;
        if (board.isFull()) return GameStatus.DRAW;
        return GameStatus.IN_PROGRESS;
    }

    public Player currentPlayer() {
        return current;
    }

}
