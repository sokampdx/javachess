import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Rook extends Piece {
    private boolean canCastle;
    public Rook(PieceColor color, Position position) {
        super(color, position);
        this.canCastle = true;
    }

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
        if (board.isEmpty(newPosition.getRow(), newPosition.getCol())) {
            return true; // Valid move to an empty square
        }
        
        // Capture move
        return board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color; 
    }

}
