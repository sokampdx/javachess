import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class King extends Piece {
    private boolean canCastle;

    public King(PieceColor color, Position position) {
        super(color, position);
        this.canCastle = true;
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
        if (colDiff == 2 && rowDiff == 0 && canCastle) {
             // Check if the path is clear and the rook is in the correct position
            int rookCol = (newPosition.getCol() == 6) ? 7 : 0;
            Piece rook = board.getPiece(position.getRow(), rookCol);
            if (rook instanceof Rook && rook.getColor() == this.color && ((Rook) rook).isCanCastle()) {
                // Check if the squares between the king and rook are empty
                int step = (newPosition.getCol() - position.getCol()) / 2;
                for (int col = position.getCol() + step; col != rookCol; col += step) {
                    if (!board.isEmpty(position.getRow(), col)) {
                        return false; // Path is blocked
                    } else { 
                        // Check if the square in betweenis under attack
                    }
                }
                // Check if the squares between the king and rook are not under attack
            }
        }
        this.canCastle = false; // After the first move, castling is no longer possible
        return false;
    }
    
    @Override
    public Piece clone() {
        return King.builder()
                .color(this.color)
                .position(new Position(this.position.getRow(), this.position.getCol()))
                .canCastle(this.canCastle)
                .build();
    }
}
