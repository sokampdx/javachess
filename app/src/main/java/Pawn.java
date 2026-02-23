import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Pawn extends Piece {
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
                    return board.isEmpty(position.getRow() + forwardDirection, position.getCol()) &&
                           board.isEmpty(newPosition.getRow(), newPosition.getCol());
                }
            }
        } else if (Math.abs(colDiff) == 1 && rowDiff == forwardDirection) {
            // Capture move
            return board.isNotEmpty(newPosition.getRow(), newPosition.getCol()) &&
                   board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color;
        }
        return true; // Placeholder
    }
}