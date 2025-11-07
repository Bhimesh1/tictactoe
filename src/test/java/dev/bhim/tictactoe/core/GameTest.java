package dev.bhim.tictactoe.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameTest {
    @Test
    void game_starts_with_player_x() {
        Player x = new Player("A", 'X');
        Player o = new Player("B", 'O');
        Game g = new Game(x, o);

        assertThat(g.currentPlayer()).isEqualTo(x);
        assertThat(g.status()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void after_x_moves_it_is_os_turn() {
        Player x = new Player("A", 'X');
        Player o = new Player("B", 'O');
        Game g = new Game(x, o);

        g.playMove(0, 0);

        assertThat(g.currentPlayer()).isEqualTo(o);
    }

    @Test
    void detects_x_win() {
        Player x = new Player("A", 'X');
        Player o = new Player("B", 'O');
        Game g = new Game(x, o);

        g.playMove(0,0); // X
        g.playMove(1,0); // O
        g.playMove(0,1); // X
        g.playMove(1,1); // O
        g.playMove(0,2); // X wins

        assertThat(g.status()).isEqualTo(GameStatus.X_WINS);
    }
}
