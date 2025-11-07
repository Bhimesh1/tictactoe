package dev.bhim.tictactoe.core;

// Player has a name and a symbol ('X' or 'O').
public record Player(String name, char symbol) {
    public Player {
        if (symbol != 'X' && symbol != 'O') {
            throw new IllegalArgumentException("symbol must be 'X' or 'O'");
        }
    }
}
