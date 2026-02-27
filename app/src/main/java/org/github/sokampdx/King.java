package org.github.sokampdx;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class King extends Piece {
    private boolean canCastle;

    public King(PieceColor color, Position position) {
        super(color, position);
        this.canCastle = true;
    }

    public King(PieceColor color, Position position, boolean canCastle) {
        super(color, position);
        this.canCastle = canCastle;
    }

    public boolean isCanCastle() {
        return this.canCastle;
    }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        if (board.isCheckAfterMove(position, newPosition, color)) {
            return false; // Destination is in check
        }

        if (isSingleMove(rowDiff, colDiff)) {
            return board.isEmpty(newPosition) || board.isOpponentPiece(newPosition, color);
        }

        if (isCastleMove(rowDiff, colDiff)) {
            if (board.isInCheck(this.color)) {
                return false; // It is in check
            }

            int rookCol = (newPosition.getCol() == 6) ? 7 : 0;
            Piece rook = board.getPiece(position.getRow(), rookCol);

            if (rook instanceof Rook && rook.getColor() == this.color && ((Rook) rook).isCanCastle()) {
                int pathCol = position.getCol() + (newPosition.getCol() - position.getCol()) / 2;
                if (!board.isEmpty(position.getRow(), pathCol)) {
                    return false; // Path is blocked
                }
                return !(board.isCheckAfterMove(position, new Position(position.getRow(), pathCol), color));
            }
        }
        return false;
    }

    private boolean isCastleMove(int rowDiff, int colDiff) {
        return colDiff == 2 && rowDiff == 0 && canCastle;
    }

    private static boolean isSingleMove(int rowDiff, int colDiff) {
        return rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0);
    }

    @Override
    public Piece clone() {
        return new King(color, new Position(position), canCastle);
    }
}
