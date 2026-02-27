package org.github.sokampdx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KingTest {
    private ChessBoard board;
    private King blackKing;

    @Before
    public void setUp() {
        board = new ChessBoard();
        blackKing = new King(PieceColor.BLACK, new Position(5, 5), false);
        board.setPiece(blackKing);
    }

    @Test
    public void testValidOrthogonalMove() {
        assertTrue(blackKing.isValidMove(new Position(5, 4), board));
        assertTrue(blackKing.isValidMove(new Position(4, 5), board));
    }

    @Test
    public void testValidDiagonalMove() {
        assertTrue(blackKing.isValidMove(new Position(4, 4), board));
        assertTrue(blackKing.isValidMove(new Position(6, 4), board));
    }

    @Test
    public void testInvalidMove() {
        assertFalse(blackKing.isValidMove(new Position(7, 7), board));
        assertFalse(blackKing.isValidMove(new Position(5, 2), board));
    }

    @Test
    public void testInvalidMoveWhenSameColorPieceAtDestination() {
        Position position = new Position(4, 4);
        Rook blackRook = new Rook(PieceColor.BLACK, position, true);
        board.setPiece(blackRook);
        assertFalse(blackKing.isValidMove(position, board));
    }

    @Test
    public void testInvalidMoveWhenDestinationInCheck() {
        Rook whiteRook = new Rook(PieceColor.WHITE, new Position(4, 0), true);
        board.setPiece(whiteRook);

        assertFalse(blackKing.isValidMove(new Position(4, 4), board));
    }

    @Test
    public void testValidCastleMove() {
        ChessBoard newBoard = new ChessBoard();
        newBoard.setupNewBoard();
        Piece newBlackKing = newBoard.getPiece(7, 4);

        newBoard.setPiece(new Position(7, 5), null);
        newBoard.setPiece(new Position(7, 6), null);
        assertTrue(newBlackKing.isValidMove(new Position(7, 6), newBoard));

        newBoard.setPiece(new Position(7, 3), null);
        newBoard.setPiece(new Position(7, 2), null);
        newBoard.setPiece(new Position(7, 1), null);
        assertTrue(newBlackKing.isValidMove(new Position(7, 2), newBoard));
    }

    @Test
    public void testInvalidCastleMoveWhenBlock() {
        ChessBoard newBoard = new ChessBoard();
        newBoard.setupNewBoard();
        Piece newBlackKing = newBoard.getPiece(7, 4);

        newBoard.setPiece(new Position(7, 6), null);
        assertFalse(newBlackKing.isValidMove(new Position(7, 6), newBoard));

        newBoard.setPiece(new Position(7, 3), null);
        newBoard.setPiece(new Position(7, 2), null);
        newBoard.setPiece(new Position(7, 1), null);
        newBoard.setPiece(new Knight(PieceColor.WHITE, new Position(7, 3)));
        assertFalse(newBlackKing.isValidMove(new Position(7, 2), newBoard));
    }

    @Test
    public void testInvalidCastleMoveWhenDestinationChecked() {
        ChessBoard newBoard = new ChessBoard();
        newBoard.setupNewBoard();
        Piece newBlackKing = newBoard.getPiece(7, 4);

        newBoard.setPiece(new Position(7, 5), null);
        newBoard.setPiece(new Position(7, 6), null);
        newBoard.setPiece(new Knight(PieceColor.WHITE, new Position(5, 7)));
        assertFalse(newBlackKing.isValidMove(new Position(7, 6), newBoard));
    }

    @Test
    public void testInvalidCastleMoveWhenPathInChecked() {
        ChessBoard newBoard = new ChessBoard();
        newBoard.setupNewBoard();
        Piece newBlackKing = newBoard.getPiece(7, 4);

        newBoard.setPiece(new Position(7, 5), null);
        newBoard.setPiece(new Position(7, 6), null);
        newBoard.setPiece(new Knight(PieceColor.WHITE, new Position(5, 6)));
        assertFalse(newBlackKing.isValidMove(new Position(7, 6), newBoard));
    }

    @Test
    public void testInvalidCastleMoveWhenInChecked() {
        ChessBoard newBoard = new ChessBoard();
        newBoard.setupNewBoard();
        Piece newBlackKing = newBoard.getPiece(7, 4);

        newBoard.setPiece(new Position(7, 3), null);
        newBoard.setPiece(new Position(7, 2), null);
        newBoard.setPiece(new Position(7, 1), null);
        newBoard.setPiece(new Knight(PieceColor.WHITE, new Position(5, 5)));
        assertFalse(newBlackKing.isValidMove(new Position(7, 2), newBoard));
    }
}
