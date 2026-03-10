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
    }

}
