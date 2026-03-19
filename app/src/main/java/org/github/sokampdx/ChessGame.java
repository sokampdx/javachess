package org.github.sokampdx;

public class ChessGame {
    private final ChessBoard board;
    private PieceColor currentPlayer;

    public ChessGame() {
        board = new ChessBoard();
        currentPlayer = PieceColor.WHITE;
        board.setupNewBoard();
    }

    public PieceColor getCurrentPlayer() { return this.currentPlayer; }

    public ChessBoard getBoard() { return this.board; }

    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPiece(from).clone();
        if (piece == null || piece.getColor() != currentPlayer) {
            return false; // No piece at the source or not the player's turn
        }

        if (piece.isValidMove(to, board)) {
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

        if (piece instanceof King) {
            ((King) piece).setCanCastle(false);
        }
    }

    private boolean isExecutingEnPassant(Position to, Piece piece) {
        return (piece instanceof Pawn) && ((Pawn) piece).executeEnPassant(board, to);
    }

    private static boolean isCastling(Position to, Piece piece) {
        return (piece instanceof King) && ((King) piece).isCastleMove(to);
    }

    private void switchCurrentPlayer() {
        currentPlayer = opponentColor(currentPlayer);
    }

    private PieceColor opponentColor(PieceColor color) {
        return (color == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
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
}
