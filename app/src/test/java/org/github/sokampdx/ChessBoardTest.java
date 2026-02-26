package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessBoardTest {
    private ChessBoard board;

    @Before
    public void setUp() {
        board = new ChessBoard();
    }

    @Test
    public void initBoardIsEmpty() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;j++) {
                assertTrue(board.isEmpty(i, j));
            }
        }
    }

    @Test
    public void setUpBoardCorrectly() {
        board.setupNewBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;j++) {
                switch(i) {
                    case 0:
                    case 1:
                    case 6:
                    case 7:
                        assertTrue(board.isNotEmpty(i, j));
                        break;
                    default:
                        assertTrue(board.isEmpty(i, j));
                }
            }
        }
    }

}
