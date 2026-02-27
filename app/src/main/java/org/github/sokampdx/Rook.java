package org.github.sokampdx;
import lombok.*;

@Getter
@Setter
public class Rook extends Piece {
    private boolean canCastle;

    public Rook(PieceColor color, Position position) {
        super(color, position);
        this.canCastle = true;
    }

    public Rook(PieceColor color, Position position, boolean canCastle) {
        super(color, position);
        this.canCastle = canCastle;
    }

    public boolean getCanCastle() {
        return this.canCastle;
    }

    public void setCanCastle(boolean canCastle) { this.canCastle = canCastle; }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        // Implement rook movement logic here
        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getCol() - position.getCol();

        if (rowDiff != 0 && colDiff != 0) {
            return false; // Rook moves only in straight lines
        }

        // Check if the path is clear
        int stepRow = Integer.signum(rowDiff);
        int stepCol = Integer.signum(colDiff);
        int currentRow = position.getRow() + stepRow;
        int currentCol = position.getCol() + stepCol;

        while (currentRow != newPosition.getRow() || currentCol != newPosition.getCol()) {
            if (!board.isEmpty(currentRow, currentCol)) {
                return false; // Path is blocked
            }
            currentRow += stepRow;
            currentCol += stepCol;
        }

        // Check if the destination square is empty or occupied by an opponent's piece
        if (board.isEmpty(newPosition)) {
            return true; // Valid move to an empty square
        }
        
        // Capture move
        return board.getPiece(newPosition).getColor() != this.color; 
    }

    @Override
    public Piece clone() {
        return new Rook(color, new Position(position), canCastle);
    }
}
