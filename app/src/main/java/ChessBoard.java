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
