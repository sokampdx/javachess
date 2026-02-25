import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public abstract class Piece {
    protected PieceColor color;
    protected Position position;

    public abstract boolean isValidMove(Position newPosition, ChessBoard board);

    public abstract Piece clone();
}