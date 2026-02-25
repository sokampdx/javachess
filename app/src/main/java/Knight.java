import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Knight extends Piece {
    @Override
    public boolean isValidMove(Position newPosition, ChessBoard board) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return board.isEmpty(newPosition.getRow(), newPosition.getCol()) ||
                   board.getPiece(newPosition.getRow(), newPosition.getCol()).getColor() != this.color;
        }

        return false;
    }

    @Override
    public Piece clone() {
        return Knight.builder()
                .color(this.color)
                .position(new Position(this.position.getRow(), this.position.getCol()))
                .build();
    }
}
