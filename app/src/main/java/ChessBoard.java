public class ChessBoard {
    private Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        // Setup pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = Pawn.builder().color(PieceColor.WHITE).position(new Position(1, i)).build();
            board[6][i] = Pawn.builder().color(PieceColor.BLACK).position(new Position(6, i)).build();
        }

        // Setup other pieces
        board[0][0] = Rook.builder().color(PieceColor.WHITE).position(new Position(0, 0)).build();
        board[0][7] = Rook.builder().color(PieceColor.WHITE).position(new Position(0, 7)).build();
        board[7][0] = Rook.builder().color(PieceColor.BLACK).position(new Position(7, 0)).build();
        board[7][7] = Rook.builder().color(PieceColor.BLACK).position(new Position(7, 7)).build();
        board[0][1] = Knight.builder().color(PieceColor.WHITE).position(new Position(0, 1)).build();
        board[0][6] = Knight.builder().color(PieceColor.WHITE).position(new Position(0, 6)).build();
        board[7][1] = Knight.builder().color(PieceColor.BLACK).position(new Position(7, 1)).build();
        board[7][6] = Knight.builder().color(PieceColor.BLACK).position(new Position(7, 6)).build();
        board[0][2] = Bishop.builder().color(PieceColor.WHITE).position(new Position(0, 2)).build();
        board[0][5] = Bishop.builder().color(PieceColor.WHITE).position(new Position(0, 5)).build();
        board[7][2] = Bishop.builder().color(PieceColor.BLACK).position(new Position(7, 2)).build();
        board[7][5] = Bishop.builder().color(PieceColor.BLACK).position(new Position(7, 5)).build();
        board[0][3] = Queen.builder().color(PieceColor.WHITE).position(new Position(0, 3)).build();
        board[7][3] = Queen.builder().color(PieceColor.BLACK).position(new Position(7, 3)).build();
        board[0][4] = King.builder().color(PieceColor.WHITE).position(new Position(0, 4)).build();
        board[7][4] = King.builder().color(PieceColor.BLACK).position(new Position(7, 4)).build();
        
    }

    public boolean isEmpty(int row, int col) {
        return board[row][col] == null;
    }

    public boolean isNotEmpty(int row, int col) {
        return board[row][col] != null;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }
}
