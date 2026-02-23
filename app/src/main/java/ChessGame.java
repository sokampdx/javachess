public class ChessGame {
    private ChessBoard board;
    private PieceColor currentPlayer;

    public ChessGame() {
        board = new ChessBoard();
        currentPlayer = PieceColor.WHITE;
    }

    private Position findKingPosition(PieceColor color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(row, col);
                }
            }
        }
        throw new IllegalStateException("King not found on the board");
    }

    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        if (piece == null || piece.getColor() != currentPlayer) {
            return false; // No piece at the source or not the player's turn
        }

        if (piece.isValidMove(to, board)) {
            // Move the piece
            board.getPiece(to.getRow(), to.getCol()).setPosition(to);
            board.getPiece(from.getRow(), from.getCol()).setPosition(null);
            board.getPiece(to.getRow(), to.getCol()).setColor(currentPlayer);
            board.getPiece(from.getRow(), from.getCol()).setColor(null);

            // Switch player
            currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
            return true;
        }

        return false; // Invalid move
    }

    public boolean isInCheck(PieceColor color) {
        Position kingPosition = findKingPosition(color);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() != color) {
                    if (piece.isValidMove(kingPosition, board)) {
                        return true; // King is in check
                    }
                }
            }        
        }  
        return false;
    }

    private boolean isInCheckAfterMove(Position kingPosition, Position newPosition, PieceColor color) {
        // Simulate the move and check if the king is still in check
        Piece originalPiece = board.getPiece(newPosition.getRow(), newPosition.getCol());
        board.setPiece(newPosition.getRow(), newPosition.getCol(), board.getPiece(kingPosition.getRow(), kingPosition.getCol()));
        board.setPiece(kingPosition.getRow(), kingPosition.getCol(), null);

        boolean inCheck = isInCheck(color);

        // Revert the move
        board.setPiece(kingPosition.getRow(), kingPosition.getCol(), board.getPiece(newPosition.getRow(), newPosition.getCol()));
        board.setPiece(newPosition.getRow(), newPosition.getCol(), originalPiece);

        return inCheck;
    }

    public boolean isCheckmate(PieceColor color) {
        if (!isInCheck(color)) {
            return false; // Not in check, so cannot be checkmate
        }

        Position kingPosition = findKingPosition(color);
        King king = (King) board.getPiece(kingPosition.getRow(), kingPosition.getCol());

        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (r == 0 && c == 0) continue; // Skip the current position
                Position newPosition = new Position(kingPosition.getRow() + r, kingPosition.getCol() + c);

                
                if (isPositionOnBoard(newPosition) && king.isValidMove(newPosition, board) && !isInCheckAfterMove(kingPosition, newPosition, color)) {
                    return false; // Found a valid move that gets the king out of check                    
                }
            }
        }

        return false;
    }

    private boolean isPositionOnBoard(Position newPosition) {
        return newPosition.getRow() >= 0 && newPosition.getRow() < 8 && newPosition.getCol() >= 0 && newPosition.getCol() < 8;
    }
}
