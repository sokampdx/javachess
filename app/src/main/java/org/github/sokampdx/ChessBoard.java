package org.github.sokampdx;

public class ChessBoard {
    private final Piece[][] board_pieces;

    public ChessBoard() {
        board_pieces = new Piece[8][8];
    }

    public void setPiece(int row, int col, Piece piece) {
        board_pieces[row][col] = piece;
        if (piece != null) {
            piece.setPosition((new Position(row, col)));
        }
    }

    public void setPiece(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("The piece can not be nothing");
        }
        board_pieces[piece.position.getRow()][piece.position.getCol()] = piece;
    }

    public void setPiece(Position position, Piece piece) {
        setPiece(position.getRow(), position.getCol(), piece);
    }

    public void setupNewBoard() {
        // Setup pawns
        for (int i = 0; i < 8; i++) {
            board_pieces[1][i] = new Pawn(PieceColor.WHITE, new Position(1, i));
            board_pieces[6][i] = new Pawn(PieceColor.BLACK, new Position(6, i));
        }

        // Setup other pieces
        board_pieces[0][0] = new Rook(PieceColor.WHITE, new Position(0, 0));
        board_pieces[0][7] = new Rook(PieceColor.WHITE, new Position(0, 7));
        board_pieces[7][0] = new Rook(PieceColor.BLACK, new Position(7, 0));
        board_pieces[7][7] = new Rook(PieceColor.BLACK, new Position(7, 7));
        board_pieces[0][1] = new Knight(PieceColor.WHITE, new Position(0, 1));
        board_pieces[0][6] = new Knight(PieceColor.WHITE, new Position(0, 6));
        board_pieces[7][1] = new Knight(PieceColor.BLACK, new Position(7, 1));
        board_pieces[7][6] = new Knight(PieceColor.BLACK, new Position(7, 6));
        board_pieces[0][2] = new Bishop(PieceColor.WHITE, new Position(0, 2));
        board_pieces[0][5] = new Bishop(PieceColor.WHITE, new Position(0, 5));
        board_pieces[7][2] = new Bishop(PieceColor.BLACK, new Position(7, 2));
        board_pieces[7][5] = new Bishop(PieceColor.BLACK, new Position(7, 5));
        board_pieces[0][3] = new Queen(PieceColor.WHITE, new Position(0, 3));
        board_pieces[7][3] = new Queen(PieceColor.BLACK, new Position(7, 3));
        board_pieces[0][4] = new King(PieceColor.WHITE, new Position(0, 4));
        board_pieces[7][4] = new King(PieceColor.BLACK, new Position(7, 4));
    }

    public boolean isEmpty(int row, int col) {
        return getPiece(row, col) == null;
    }

    public boolean isEmpty(Position position) {
        return getPiece(position) == null;
    }

    public boolean isNotEmpty(int row, int col) {
        return !isEmpty(row, col);
    }

    public boolean isSamePlayerPiece(Position position, PieceColor color) {
        return getPiece(position) != null && getPiece(position).getColor() == color;
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

    public void removePiece(Position position) {
        setPiece(position.getRow(), position.getCol(), null);
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
