package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PawnTest {
    private ChessBoard board;
    private Pawn whitePawn;

    @Before
    public void setUp() {
        board = new ChessBoard();
        whitePawn = new Pawn(PieceColor.WHITE, new Position(1, 5));
        board.setPiece(whitePawn);
    }
    @Test
    public void testValidSingleMove() {
        assertTrue(whitePawn.isValidMove(new Position(2, 5), board));
    }

    @Test
    public void testValidDoubleMove() {
        assertTrue(whitePawn.isValidMove(new Position(3, 5), board));
    }

    @Test
    public void testValidCaptureMove() {
        Position position = new Position(2, 6);
        board.setPiece(new Pawn(PieceColor.BLACK, position));
        assertTrue(whitePawn.isValidMove(position, board));
    }

    @Test
    public void testValidEnPassantMove() {
        board.setPiece(new Pawn(PieceColor.BLACK, new Position(4, 6), true));
        whitePawn = new Pawn(PieceColor.WHITE, new Position(4, 5));
        board.setPiece(whitePawn);
        assertTrue(whitePawn.isValidMove(new Position(5, 6), board));
    }

    @Test
    public void testInvalidBlockedMove() {
        board.setPiece(new Pawn(PieceColor.WHITE, new Position(2, 5)));
        assertFalse(whitePawn.isValidMove(new Position(3, 5), board));
        assertFalse(whitePawn.isValidMove(new Position(2, 5), board));
    }

    @Test
    public void testInvalidEnPassantMove() {
        board.setPiece(new Pawn(PieceColor.BLACK, new Position(4, 6), false));
        whitePawn = new Pawn(PieceColor.WHITE, new Position(4, 5));
        board.setPiece(whitePawn);
        assertFalse(whitePawn.isValidMove(new Position(5, 6), board));
    }
}
