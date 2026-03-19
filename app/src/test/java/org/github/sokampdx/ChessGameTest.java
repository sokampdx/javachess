package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessGameTest {
    private ChessGame game;

    @Before
    public void setUp() {
        game = new ChessGame();
    }

    @Test
    public void testValidCurrentPlayerSwitch() {
        assertEquals(PieceColor.WHITE, game.getCurrentPlayer());
        assertTrue(game.movePiece(new Position(1,4), new Position(3, 4)));
        assertEquals(PieceColor.BLACK, game.getCurrentPlayer());
    }

    @Test
    public void testValidCaptureMove() {
        assertTrue(game.movePiece(new Position(1,4), new Position(3, 4)));
        assertTrue(game.movePiece(new Position(6,3), new Position(4, 3)));
        assertTrue(game.movePiece(new Position(3,4), new Position(4, 3)));
    }

    @Test
    public void testValidEnPassant() {
        assertTrue(game.movePiece(new Position(1,4), new Position(3, 4)));
        assertTrue(game.movePiece(new Position(7,6), new Position(5, 5)));
        assertTrue(game.movePiece(new Position(3,4), new Position(4, 4)));
        assertTrue(game.movePiece(new Position(6,3), new Position(4, 3)));
        assertTrue(game.movePiece(new Position(4,4), new Position(5, 3)));

        assertTrue(game.getBoard().isEmpty(4,3));
    }

    @Test
    public void testInvalidEnPassant() {
        assertTrue(game.movePiece(new Position(1,4), new Position(3, 4)));
        assertTrue(game.movePiece(new Position(6,3), new Position(5, 3)));
        assertTrue(game.movePiece(new Position(3,4), new Position(4, 4)));
        assertTrue(game.movePiece(new Position(5,3), new Position(4, 3)));
        assertFalse(game.movePiece(new Position(4,4), new Position(5, 3)));
    }

    @Test
    public void testValidCastling() {
        assertTrue(game.movePiece(new Position(1,4), new Position(3, 4)));
        assertTrue(game.movePiece(new Position(6,4), new Position(4, 4)));
        assertTrue(game.movePiece(new Position(0,5), new Position(3, 2)));
        assertTrue(game.movePiece(new Position(7,5), new Position(4, 2)));
        assertTrue(game.movePiece(new Position(0,6), new Position(2, 5)));
        assertTrue(game.movePiece(new Position(7,6), new Position(5, 5)));
        assertTrue(game.movePiece(new Position(0,4), new Position(0, 6)));

        assertTrue(game.getBoard().isEmpty(0, 7));
        assertTrue(game.getBoard().getPiece(0,5) instanceof Rook);
    }

    @Test
    public void testInvalidCastling() {
        assertTrue(game.movePiece(new Position(1,4), new Position(3, 4)));
        assertTrue(game.movePiece(new Position(6,4), new Position(4, 4)));
        assertTrue(game.movePiece(new Position(0,5), new Position(3, 2)));
        assertTrue(game.movePiece(new Position(7,5), new Position(4, 2)));
        assertTrue(game.movePiece(new Position(0,6), new Position(2, 5)));
        assertTrue(game.movePiece(new Position(7,6), new Position(5, 5)));
        assertTrue(game.movePiece(new Position(0,4), new Position(1, 4)));
        assertTrue(game.movePiece(new Position(7,1), new Position(5, 2)));
        assertTrue(game.movePiece(new Position(1,4), new Position(0, 4)));
        assertTrue(game.movePiece(new Position(6,3), new Position(5, 3)));
        assertFalse(game.movePiece(new Position(0,4), new Position(0, 6)));
    }
}
