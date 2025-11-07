package dev.bhim.tictactoe.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void board_is_empty_on_start() {
        // I expect that all cells be blanks ' ' when a board is created.
        Board b = new Board();

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertThat(b.get(r, c)).isEqualTo(' ');
            }
        }
    }
}
