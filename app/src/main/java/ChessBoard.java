public class ChessBoard {
    private Piece[][] board_pieces;

    public ChessBoard() {
        board_pieces = new Piece[8][8];
        setupBoard();
    }

    public void setPiece(int row, int col, Piece piece) {
        board_pieces[row][col] = piece;
        if (piece != null) {
            piece.setPosition(new Position(row, col));
        }
    }

    private void setupBoard() {
        // Setup pawns
        for (int i = 0; i < 8; i++) {
            board_pieces[1][i] = Pawn.builder().color(PieceColor.WHITE).position(new Position(1, i)).build();
            board_pieces[6][i] = Pawn.builder().color(PieceColor.BLACK).position(new Position(6, i)).build();
        }

        // Setup other pieces
        board_pieces[0][0] = Rook.builder().color(PieceColor.WHITE).position(new Position(0, 0)).build();
        board_pieces[0][7] = Rook.builder().color(PieceColor.WHITE).position(new Position(0, 7)).build();
        board_pieces[7][0] = Rook.builder().color(PieceColor.BLACK).position(new Position(7, 0)).build();
        board_pieces[7][7] = Rook.builder().color(PieceColor.BLACK).position(new Position(7, 7)).build();
        board_pieces[0][1] = Knight.builder().color(PieceColor.WHITE).position(new Position(0, 1)).build();
        board_pieces[0][6] = Knight.builder().color(PieceColor.WHITE).position(new Position(0, 6)).build();
        board_pieces[7][1] = Knight.builder().color(PieceColor.BLACK).position(new Position(7, 1)).build();
        board_pieces[7][6] = Knight.builder().color(PieceColor.BLACK).position(new Position(7, 6)).build();
        board_pieces[0][2] = Bishop.builder().color(PieceColor.WHITE).position(new Position(0, 2)).build();
        board_pieces[0][5] = Bishop.builder().color(PieceColor.WHITE).position(new Position(0, 5)).build();
        board_pieces[7][2] = Bishop.builder().color(PieceColor.BLACK).position(new Position(7, 2)).build();
        board_pieces[7][5] = Bishop.builder().color(PieceColor.BLACK).position(new Position(7, 5)).build();
        board_pieces[0][3] = Queen.builder().color(PieceColor.WHITE).position(new Position(0, 3)).build();
        board_pieces[7][3] = Queen.builder().color(PieceColor.BLACK).position(new Position(7, 3)).build();
        board_pieces[0][4] = King.builder().color(PieceColor.WHITE).position(new Position(0, 4)).build();
        board_pieces[7][4] = King.builder().color(PieceColor.BLACK).position(new Position(7, 4)).build();
        
    }

    public boolean isEmpty(int row, int col) {
        return board_pieces[row][col] == null;
    }

    public boolean isEmpty(Position position) {
        return isEmpty(position.getRow(), position.getCol());
    }

    public boolean isNotEmpty(int row, int col) {
        return board_pieces[row][col] != null;
    }

    public boolean isSamePlayerPiece(Position position, PieceColor color) {
        return board_pieces[position.getRow()][position.getCol()] != null && board_pieces[position.getRow()][position.getCol()].getColor() == color;
    }

    public boolean isOpponentPiece(Position position, PieceColor color) {
        return !isSamePlayerPiece(position, color);
    }

    public Piece getPiece(int row, int col) {
        return board_pieces[row][col];
    }

    public Piece getPiece(Position position) {
        return getPiece(position.getRow(), position.getCol());
    }

    public Position findKingPosition(PieceColor color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(row, col);
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(row, col);
                }
            }
        }
        throw new IllegalStateException("King not found on the board");
    }

    public boolean isInCheck(PieceColor color) {
        Position kingPosition = findKingPosition(color);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(row, col);
                if (piece != null && piece.getColor() != color) {
                    if (piece.isValidMove(kingPosition, this)) {
                        return true; // King is in check
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckAfterMove(Position from, Position to, PieceColor color) {
        // Simulate the move and check if the king is still in check
        Piece originalPiece = getPiece(to.getRow(), to.getCol());
        setPiece(to.getRow(), to.getCol(), getPiece(from.getRow(), from.getCol()));
        setPiece(from.getRow(), from.getCol(), null);

        boolean inCheck = isInCheck(color);

        // Revert the move
        setPiece(from.getRow(), from.getCol(), getPiece(to.getRow(), to.getCol()));
        setPiece(to.getRow(), to.getCol(), originalPiece);

        return inCheck;
    }

    public boolean isPositionOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < 8 && position.getCol() >= 0 && position.getCol() < 8;
    }
}
