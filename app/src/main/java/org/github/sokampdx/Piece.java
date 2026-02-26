package org.github.sokampdx;
import lombok.*;

@Getter
@Setter
public abstract class Piece {
    protected PieceColor color;
    protected Position position;

    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract boolean isValidMove(Position newPosition, ChessBoard board);

    public abstract Piece clone();
}