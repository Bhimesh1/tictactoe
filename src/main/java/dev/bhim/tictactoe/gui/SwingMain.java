package dev.bhim.tictactoe.gui;

import dev.bhim.tictactoe.core.*;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SwingMain {

    private final JFrame frame = new JFrame("Tic-Tac-Toe");
    private final JButton[][] cells = new JButton[3][3];
    private final JLabel status = new JLabel(" ");
    private Game game;
    private Player x, o;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingMain().start());
    }

    private void start() {
        // ask names
        String nameX = ask("Enter name for Player X:");
        if (nameX == null || nameX.isBlank()) nameX = "Player X";
        String nameO = ask("Enter name for Player O:");
        if (nameO == null || nameO.isBlank()) nameO = "Player O";
        x = new Player(nameX, 'X');
        o = new Player(nameO, 'O');
        game = new Game(x, o);

        // ui
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        JPanel board = new JPanel(new GridLayout(3, 3, 4, 4));
        board.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 36);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton b = new JButton(" ");
                b.setFont(f);
                b.putClientProperty("r", r);
                b.putClientProperty("c", c);
                b.addActionListener(e -> onCellClick((JButton) e.getSource()));
                cells[r][c] = b;
                board.add(b);
            }
        }

        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        frame.add(status, BorderLayout.NORTH);
        frame.add(board, BorderLayout.CENTER);

        frame.setSize(320, 360);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        refresh(); // initial state
    }

    private String ask(String prompt) {
        return JOptionPane.showInputDialog(frame, prompt, "Tic-Tac-Toe", JOptionPane.QUESTION_MESSAGE);
    }

    private void onCellClick(JButton b) {
        if (game.status() != GameStatus.IN_PROGRESS) return;

        int r = (int) Objects.requireNonNull(b.getClientProperty("r"));
        int c = (int) Objects.requireNonNull(b.getClientProperty("c"));
        try {
            game.playMove(r, c);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Invalid move", JOptionPane.WARNING_MESSAGE);
            return;
        }
        refresh();

        if (game.status() != GameStatus.IN_PROGRESS) {
            endGameDialog();
        }
    }

    private void refresh() {
        Board board = game.board();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                cells[r][c].setText(String.valueOf(board.get(r, c)));
            }
        }
        switch (game.status()) {
            case IN_PROGRESS ->
                    status.setText(game.currentPlayer().name() + " (" + game.currentPlayer().symbol() + ") to move");
            case X_WINS -> status.setText(x.name() + " (X) wins!");
            case O_WINS -> status.setText(o.name() + " (O) wins!");
            case DRAW   -> status.setText("It's a draw.");
        }
    }

    private void endGameDialog() {
        String msg = switch (game.status()) {
            case X_WINS -> x.name() + " (X) wins!";
            case O_WINS -> o.name() + " (O) wins!";
            case DRAW   -> "It's a draw.";
            default     -> "";
        };
        int choice = JOptionPane.showOptionDialog(
                frame, msg + "\nPlay again?", "Game over",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{"New game", "Close"}, "New game"
        );
        if (choice == JOptionPane.YES_OPTION) {
            game = new Game(x, o);
            refresh();
        } else {
            frame.dispose();
        }
    }
}
