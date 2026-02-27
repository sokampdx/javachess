package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueenTest {
    private ChessBoard board;
    private Queen whiteQueen;

    @Before
    public void setUp() {
        board = new ChessBoard();
        whiteQueen = new Queen(PieceColor.WHITE, new Position(5, 5));
        board.setPiece(whiteQueen);
    }

    @Test
    public void testValidOrthogonalMove() {
        assertTrue(whiteQueen.isValidMove(new Position(5, 0), board));
        assertTrue(whiteQueen.isValidMove(new Position(0, 5), board));
    }

    @Test
    public void testValidDiagonalMove() {
        assertTrue(whiteQueen.isValidMove(new Position(1, 1), board));
        assertTrue(whiteQueen.isValidMove(new Position(3, 7), board));
    }

    @Test
    public void testInvalidMove() {
        assertFalse(whiteQueen.isValidMove(new Position(3, 4), board));
    }

    @Test
    public void testInvalidBlockedMove() {
        Position position1 = new Position(0, 5);
        assertTrue(whiteQueen.isValidMove(position1, board));
        Position position2 = new Position(1, 1);
        assertTrue(whiteQueen.isValidMove(position2, board));

        board.setPiece(new Pawn(PieceColor.WHITE, new Position(1, 5), false));
        assertFalse(whiteQueen.isValidMove(position1, board));
        board.setPiece(new Pawn(PieceColor.WHITE, new Position(4, 4), false));
        assertFalse(whiteQueen.isValidMove(position2, board));
    }
}
