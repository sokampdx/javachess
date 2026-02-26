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
    public void testInitBoardIsEmpty() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;j++) {
                assertTrue(board.isEmpty(i, j));
            }
        }
    }

    @Test
    public void testSetUpBoardCorrectly() {
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

    @Test(expected = IllegalStateException.class)
    public void testThrowExceptionWhenKingIsNotFound() {
        board.findKingPosition(PieceColor.BLACK);
    }

    @Test
    public void testNoExceptionWhenKingIsFound() {
        Position position = new Position(5, 5);
        Piece whiteKing = new King(PieceColor.WHITE, position, true);
        board.setPiece(whiteKing.getPosition(), whiteKing);
        Position kingPosition = board.findKingPosition(PieceColor.WHITE);
        assertEquals(kingPosition.getRow(), position.getRow());
        assertEquals(kingPosition.getCol(), position.getCol());
    }

    @Test
    public void testIsInCheck() {
        Piece whiteKing = new King(PieceColor.WHITE, new Position(5, 5));
        board.setPiece(whiteKing.getPosition(), whiteKing);
        Piece blackRook = new Rook(PieceColor.BLACK, new Position(5, 0));
        board.setPiece(blackRook.getPosition(), blackRook);

        assertTrue(board.isInCheck(PieceColor.WHITE));
    }

    @Test
    public void testIsNotInCheck() {
        Piece whiteKing = new King(PieceColor.WHITE, new Position(5, 5));
        board.setPiece(whiteKing.getPosition(), whiteKing);
        Piece blackRook = new Rook(PieceColor.BLACK, new Position(3, 3));
        board.setPiece(blackRook.getPosition(), blackRook);

        assertFalse(board.isInCheck(PieceColor.WHITE));
    }

    @Test
    public void testIsCheckAfterMove() {
        Position from = new Position(5, 5);
        Position to = new Position(4, 5);
        Piece whiteKing = new King(PieceColor.WHITE, from);
        board.setPiece(whiteKing.getPosition(), whiteKing);
        Piece blackRook = new Rook(PieceColor.BLACK, new Position(4, 0));
        board.setPiece(blackRook.getPosition(), blackRook);

        assertTrue(board.isCheckAfterMove(from, to, PieceColor.WHITE));
    }

    @Test
    public void testIsNotCheckAfterMove() {
        Position from = new Position(5, 5);
        Position to = new Position(4, 5);
        Piece whiteKing = new King(PieceColor.WHITE, from);
        board.setPiece(whiteKing.getPosition(), whiteKing);
        Piece blackRook = new Rook(PieceColor.BLACK, new Position(5, 0));
        board.setPiece(blackRook.getPosition(), blackRook);

        assertFalse(board.isCheckAfterMove(from, to, PieceColor.WHITE));
    }
}
