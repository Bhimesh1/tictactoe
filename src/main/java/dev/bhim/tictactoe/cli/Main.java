package dev.bhim.tictactoe.cli;

import dev.bhim.tictactoe.core.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name for Player X: ");
        String nameX = sc.nextLine();
        System.out.print("Enter name for Player O: ");
        String nameO = sc.nextLine();

        Player x = new Player(nameX, 'X');
        Player o = new Player(nameO, 'O');
        Game game = new Game(x, o);

        System.out.println("\nGame start!");
        game.board().print();

        while (game.status() == GameStatus.IN_PROGRESS) {
            System.out.println("\n" + game.currentPlayer().name() + "'s turn (" + game.currentPlayer().symbol() + ")");
            int row = readIndex(sc, "Row (0-2): ");
            int col = readIndex(sc, "Col (0-2): ");

            try {
                game.playMove(row, col);
            } catch (Exception e) {
                System.out.println("Invalid move: " + e.getMessage());
                continue; // retry same player
            }

            System.out.println();
            game.board().print();
        }

        System.out.println();
        switch (game.status()) {
            case X_WINS -> System.out.println(nameX + " (X) wins!");
            case O_WINS -> System.out.println(nameO + " (O) wins!");
            case DRAW   -> System.out.println("It's a draw.");
        }
    }
    private static int readIndex(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            Integer v = tryParseInt(line);
            if (v != null && v >= 0 && v <= 2) return v;
            System.out.println("Please enter a number 0, 1, or 2.");
        }
    }

    private static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
