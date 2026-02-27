package org.github.sokampdx;
import lombok.*;

@Getter
@Setter
public class Pawn extends Piece {
    private boolean allowPassant;

    public Pawn(PieceColor color, Position position) {
        super(color, position);
        this.allowPassant = false;
    }

    public Pawn(PieceColor color, Position position, boolean allowPassant) {
        super(color, position);
        this.allowPassant = allowPassant;
    }

    public boolean getAllowPassant() { return this.allowPassant; }

    public void setAllowPassant(boolean allowPassant) {
        this.allowPassant = allowPassant;
    }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        int forwardDirection = getForwardDirection();
        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getCol() - position.getCol();

        if (isForwardMove(colDiff) && board.isEmpty(newPosition)) {
            if (isSingleMove(forwardDirection, rowDiff)) return true;
            if (isDoubleMove(forwardDirection, rowDiff)) {
                Position intermediatePosition = new Position(position.getRow() + forwardDirection, position.getCol());
                return board.isEmpty(intermediatePosition);
            }
        } else if (isCaptureMove(forwardDirection, rowDiff, colDiff)) {
            if (isNormalCapture(newPosition, board)) {
                return board.isOpponentPiece(newPosition, color);
            } else {
                return isEnPassantCapture(newPosition, board, forwardDirection);
            }
        }

        return false;
    }

    @Override
    public Piece clone() {
        return new Pawn(color, new Position(position), allowPassant);
    }

    private boolean isNormalCapture(Position newPosition, ChessBoard board) {
        return board.isNotEmpty(newPosition.getRow(), newPosition.getCol());
    }

    public Position enPassantPosition(Position newPosition, ChessBoard board, int forwardDirection) {
        return new Position(newPosition.getRow() - forwardDirection, newPosition.getCol());
    }
        
    private boolean isEnPassantCapture(Position newPosition, ChessBoard board, int forwardDirection) {
        Position capturedPawnPosition = enPassantPosition(newPosition, board, forwardDirection);
        return board.isEmpty(newPosition) && board.isOpponentPiece(newPosition, color) &&
        board.getPiece(capturedPawnPosition) instanceof Pawn && ((Pawn) board.getPiece(capturedPawnPosition)).allowPassant;
    }

    public boolean isDoubleMove(int forwardDirection, int rowDiff) {
        return ((position.getRow() == 1 && color == PieceColor.WHITE) || (position.getRow() == 6 && color == PieceColor.BLACK)) && (rowDiff == 2 * forwardDirection);
    }

    private boolean isSingleMove(int forwardDirection, int rowDiff) {
        return rowDiff == forwardDirection;
    }

    private boolean isForwardMove(int colDiff) {
        return colDiff == 0;
    }

    private boolean isCaptureMove(int forwardDirection, int rowDiff, int colDiff) {
        return Math.abs(colDiff) == 1 && isSingleMove(forwardDirection, rowDiff);
    }

    public int getForwardDirection() {
        return (color == PieceColor.WHITE) ? 1 : -1;
    }

    public boolean executeEnPassant(ChessBoard board, Position newPosition) {
        return isEnPassantCapture(newPosition, board, getForwardDirection());
    }
}