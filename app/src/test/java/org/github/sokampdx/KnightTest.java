package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KnightTest {
    private ChessBoard board;
    private Knight blackKnight;

    @Before
    public void setUp() {
        board = new ChessBoard();
        blackKnight = new Knight(PieceColor.BLACK, new Position(0, 0));
        board.setPiece(blackKnight);
    }

    @Test
    public void testIsValidMove() {
        Position position = new Position(1, 2);
        assertTrue(blackKnight.isValidMove(position, board));
    }

    @Test
    public void testIsInvalidMove() {
        Position position = new Position(1, 3);
        assertFalse(blackKnight.isValidMove(position, board));
    }
}
