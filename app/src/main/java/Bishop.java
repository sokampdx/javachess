import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Bishop extends Piece {

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        if (rowDiff == colDiff) {
            int rowDirection = (newPosition.getRow() - position.getRow()) / rowDiff;
            int colDirection = (newPosition.getCol() - position.getCol()) / colDiff;

            for (int i = 1; i < rowDiff; i++) {
                int intermediateRow = position.getRow() + i * rowDirection;
                int intermediateCol = position.getCol() + i * colDirection;
                if (!board.isEmpty(intermediateRow, intermediateCol)) {
                    return false; // Path is blocked
                }
            }

            return board.isEmpty(newPosition.getRow(), newPosition.getCol()) ||
                   board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color;
        }

        return false;
    }
    
}
