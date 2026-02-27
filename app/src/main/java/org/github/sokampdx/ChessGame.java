package org.github.sokampdx;
public class ChessGame {
    private final ChessBoard board;
    private PieceColor currentPlayer;

    public ChessGame() {
        board = new ChessBoard();
        currentPlayer = PieceColor.WHITE;
        board.setupNewBoard();
    }

    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPiece(from).clone();
        if (piece == null || piece.getColor() != currentPlayer) {
            return false; // No piece at the source or not the player's turn
        }

        if (piece.isValidMove(to, board)) {
            piece.setPosition(to);
            if (isExecutingEnPassant(to, piece)) {
                updateCapturePawn(to, (Pawn) piece);
            } else if (isCastling(to, piece)) {
                updateCastleRook(to, (King) piece);
            }
            updateMovePiece(from, to, piece);

            switchCurrentPlayer();
            resetEnPassant();
            return true;
        }

        return false; // Invalid move
    }

    private void resetEnPassant() {
        board.resetEnPassant(currentPlayer);
    }

    private void updateMovePiece(Position from, Position to, Piece piece) {
        board.removePiece(from);
        piece.setPosition(to);
        board.setPiece(to, piece);
    }

    private boolean isExecutingEnPassant(Position to, Piece piece) {
        return (piece instanceof Pawn) && ((Pawn) piece).executeEnPassant(board, to);
    }

    private static boolean isCastling(Position to, Piece piece) {
        return (piece instanceof King) && ((King) piece).isCastleMove(to);
    }

    private void switchCurrentPlayer() {
        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
    }

    private void updateCapturePawn(Position to, Pawn piece) {
        Position capturedPawnPosition = piece.enPassantPosition(to, board, piece.getForwardDirection());
        board.removePiece(capturedPawnPosition);
        piece.setAllowPassant(false);
    }

    private void updateCastleRook(Position to, King king) {
        Position rookFrom = (to.getCol() > 4) ? new Position(to.getRow(), 7) : new Position(to.getRow(), 0);
        Position rookTo = (to.getCol() > 4) ? new Position(to.getRow(), 5) : new Position(to.getRow(), 3);
        Rook rook = (Rook) board.getPiece(rookFrom);
        rook.setPosition(rookTo);
        rook.setCanCastle(false);
        king.setCanCastle(false);
        board.removePiece(rookFrom);
        board.setPiece(rook);
    }

    public boolean isCheckmate(PieceColor color) {
        if (!board.isInCheck(color)) {
            return false; // Not in check, so cannot be checkmate
        }

        Position kingPosition = board.findKingPosition(color);
        King king = (King) board.getPiece(kingPosition.getRow(), kingPosition.getCol());

        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (r == 0 && c == 0) continue; // Skip the current position
                Position newPosition = new Position(kingPosition.getRow() + r, kingPosition.getCol() + c);

                if (board.isPositionOnBoard(newPosition) && king.isValidMove(newPosition, board) && !board.isCheckAfterMove(kingPosition, newPosition, color)) {
                    return false; // Found a valid move that gets the king out of check                    
                }
            }
        }

        return false;
    }
}
