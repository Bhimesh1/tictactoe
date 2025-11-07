package dev.bhim.tictactoe.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void board_is_empty_on_start() {
        Board b = new Board();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertThat(b.get(r, c)).isEqualTo(' ');
            }
        }
    }

    @Test
    void placing_mark_updates_cell() {
        Board b = new Board();
        b.place(1, 1, 'X');
        assertThat(b.get(1, 1)).isEqualTo('X');
        assertThat(b.get(0, 0)).isEqualTo(' ');
    }

    @Test
    void cannot_place_on_occupied_cell() {
        Board b = new Board();
        b.place(0, 0, 'X');
        assertThatThrownBy(() -> b.place(0, 0, 'O'))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cell already taken");
    }

    @Test
    void board_reports_full_when_no_blanks() {
        Board b = new Board();
        char[] marks = {'X','O'};
        int k = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                b.place(r, c, marks[k++ % 2]);
            }
        }
        assertThat(b.isFull()).isTrue();
    }

    @Test
    void detects_row_win() {
        Board b = new Board();
        b.place(0, 0, 'X');
        b.place(0, 1, 'X');
        b.place(0, 2, 'X');
        assertThat(b.winner()).isEqualTo('X');
    }

    @Test
    void detects_column_win() {
        Board b = new Board();
        b.place(0, 1, 'O');
        b.place(1, 1, 'O');
        b.place(2, 1, 'O');
        assertThat(b.winner()).isEqualTo('O');
    }

    @Test
    void detects_diagonal_win_main() {
        Board b = new Board();
        b.place(0, 0, 'X');
        b.place(1, 1, 'X');
        b.place(2, 2, 'X');
        assertThat(b.winner()).isEqualTo('X');
    }

    @Test
    void detects_diagonal_win_anti() {
        Board b = new Board();
        b.place(0, 2, 'O');
        b.place(1, 1, 'O');
        b.place(2, 0, 'O');
        assertThat(b.winner()).isEqualTo('O');
    }

    @Test
    void winner_blank_when_no_line() {
        Board b = new Board();
        b.place(0, 0, 'X');
        b.place(0, 1, 'O');
        b.place(1, 1, 'X');
        assertThat(b.winner()).isEqualTo(' ');
    }


}
