import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Pawn extends Piece {
    private boolean canEnPassant;

    public Pawn(PieceColor color, Position position) {
        super(color, position);
        this.canEnPassant = false;
    }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        // Implement pawn movement logic here
        int forwardDirection = (color == PieceColor.WHITE) ? 1 : -1;
        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getCol() - position.getCol();

        if (colDiff == 0) {
            // Move forward
            if (rowDiff == forwardDirection) {
                return board.isEmpty(newPosition.getRow(), newPosition.getCol());
            }
            // Initial double move
            if ((position.getRow() == 1 && color == PieceColor.WHITE) || (position.getRow() == 6 && color == PieceColor.BLACK)) {
                if (rowDiff == 2 * forwardDirection) {
                    this.canEnPassant = true; // Set en passant flag
                    return board.isEmpty(position.getRow() + forwardDirection, position.getCol()) &&
                           board.isEmpty(newPosition.getRow(), newPosition.getCol());
                }
            }
        } else if (Math.abs(colDiff) == 1 && rowDiff == forwardDirection) {
            if (board.isNotEmpty(newPosition.getRow(), newPosition.getCol())) {
                // Capture move
                return board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color;
            } else {
                // En passant
                return board.getPiece(newPosition.getRow(), newPosition.getCol()) == null && 
                board.getPiece(newPosition.getRow() - forwardDirection, newPosition.getCol()).getColor() != this.color &&
                board.getPiece(newPosition.getRow() - forwardDirection, newPosition.getCol()) instanceof Pawn &&
                ((Pawn) board.getPiece(newPosition.getRow() - forwardDirection, newPosition.getCol())).canEnPassant;
            }
        }

        return false;
    }
}