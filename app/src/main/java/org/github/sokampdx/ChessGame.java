package org.github.sokampdx;
public class ChessGame {
    private ChessBoard board;
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
            if ((piece instanceof Pawn) && ((Pawn) piece).executeEnPassant(board, to)) {
                Position capturedPawnPosition = ((Pawn) piece).enPassantPosition(to, board, ((Pawn) piece).getForwardDirection());
                board.removePiece(capturedPawnPosition);
                ((Pawn) piece).setCanEnPassant(false);
            }
            board.removePiece(from);
            board.setPiece(to, piece);

            // Switch player
            currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
            return true;
        }

        return false; // Invalid move
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
