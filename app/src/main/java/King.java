import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class King extends Piece {
    private boolean isInCheck;

    public King(PieceColor color, Position position) {
        super(color, position);
        this.isInCheck = false;
    }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0)) {
            return board.isEmpty(newPosition.getRow(), newPosition.getCol()) ||
                   board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color;
        }

        // Castling logic can be added here

        return false;
    }
    
}
