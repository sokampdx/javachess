package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RookTest {
    private ChessBoard board;
    private Rook whiteRook;

    @Before
    public void setUp() {
        board = new ChessBoard();
        whiteRook = new Rook(PieceColor.WHITE, new Position(0, 0), true);
        board.setPiece(whiteRook);
    }

    @Test
    public void testValidHorizontalMove() {
        Position position = new Position(0, 5);
        assertTrue(whiteRook.isValidMove(position, board));
    }

    @Test
    public void testInvalidDiagonalMove() {
        Position position = new Position(5, 5);
        assertFalse(whiteRook.isValidMove(position, board));
    }

    @Test
    public void testInvalidBlockedMove() {
        Position position = new Position(0, 5);
        assertTrue(whiteRook.isValidMove(position, board));

        board.setPiece(new Pawn(PieceColor.WHITE, new Position(0, 3), false));
        assertFalse(whiteRook.isValidMove(position, board));
    }

    @Test
    public void testValidVerticalMove() {
        Position position = new Position(5, 0);
        assertTrue(whiteRook.isValidMove(position, board));
    }
}
