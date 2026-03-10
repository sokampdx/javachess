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
        int rowDiff = position.getRowDiff(newPosition);
        int colDiff = position.getColDiff(newPosition);
        System.out.println("Pawn: isValidMove");
        if (isForwardMove(colDiff) && board.isEmpty(newPosition)) {
            System.out.println("Pawn: isForwardMove");

            if (isSingleMove(forwardDirection, rowDiff)) {
                System.out.println("Pawn: isSingleMove");
                return true;
            }
            if (isDoubleMove(forwardDirection, rowDiff)) {
                System.out.println("Pawn: isDoubleMove");
                Position intermediatePosition = new Position(position.getRow() + forwardDirection, position.getCol());
                return board.isEmpty(intermediatePosition);
            }
        } else if (isCaptureMove(forwardDirection, rowDiff, colDiff)) {
            System.out.println("Pawn: isCaptureMove");

            if (isNormalCapture(newPosition, board)) {
                System.out.println("Pawn: isNormalCapture");
                return board.isOpponentPiece(newPosition, color);
            } else {
                System.out.println("Pawn: isNotNormalCapture");
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