public class ChessGame {
    private ChessBoard board;
    private PieceColor currentPlayer;

    public ChessGame() {
        board = new ChessBoard();
        currentPlayer = PieceColor.WHITE;
    }

    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        if (piece == null || piece.getColor() != currentPlayer) {
            return false; // No piece at the source or not the player's turn
        }

        if (piece.isValidMove(to, board)) {
            if ((piece instanceof Pawn) && ((Pawn) piece).getExecuteEnPassant()) {
                // Handle en passant capture
                Position capturedPawnPosition = ((Pawn) piece).enPassantPosition(to, board, ((Pawn) piece).getForwardDirection());
                board.getPiece(capturedPawnPosition.getRow(), capturedPawnPosition.getCol()).setPosition(null);
                board.getPiece(capturedPawnPosition.getRow(), capturedPawnPosition.getCol()).setColor(null);
                ((Pawn) piece).setExecuteEnPassant(false); // Reset en passant execution flag
            }
            // Regular move, clear the source square
            board.getPiece(from.getRow(), from.getCol()).setPosition(null);
            board.getPiece(from.getRow(), from.getCol()).setColor(null);
            
            // Move the piece
            board.getPiece(to.getRow(), to.getCol()).setPosition(to);
            board.getPiece(to.getRow(), to.getCol()).setColor(currentPlayer);

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
