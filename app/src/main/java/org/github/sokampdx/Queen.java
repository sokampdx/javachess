package org.github.sokampdx;
import lombok.*;

@Getter
@Setter
public class Queen extends Piece {

    public Queen(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        if (rowDiff == colDiff || newPosition.getRow() == position.getRow() || newPosition.getCol() == position.getCol()) {
            // Check if the path is clear
            int rowDirection = Integer.signum(newPosition.getRow() - position.getRow());
            int colDirection = Integer.signum(newPosition.getCol() - position.getCol());

            int currentRow = position.getRow() + rowDirection;
            int currentCol = position.getCol() + colDirection;

            while (currentRow != newPosition.getRow() || currentCol != newPosition.getCol()) {
                if (!board.isEmpty(currentRow, currentCol)) {
                    return false; // Path is blocked
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }

            return board.isEmpty(newPosition.getRow(), newPosition.getCol()) ||
                   board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color;
        }

        return false;
    }
    
    @Override
    public Piece clone() {
        return new Queen(color, new Position(position));
    }
}
