package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BishopTest {
    private ChessBoard board;
    private Bishop blackBishop;

    @Before
    public void setUp() {
        board = new ChessBoard();
        blackBishop = new Bishop(PieceColor.BLACK, new Position(5, 5));
        board.setPiece(blackBishop);
    }

    @Test
    public void testValidDiagonalMove() {
        Position position = new Position(7, 3);
        assertTrue(blackBishop.isValidMove(position, board));
    }

    @Test
    public void testInvalidMove() {
        Position position = new Position(5, 0);
        assertFalse(blackBishop.isValidMove(position, board));
    }

    @Test
    public void testInvalidBlockedMove() {
        Position position = new Position(3, 3);
        assertTrue(blackBishop.isValidMove(position, board));

        board.setPiece(new Pawn(PieceColor.WHITE, new Position(4, 4), false));
        assertFalse(blackBishop.isValidMove(position, board));
    }
}
