package org.github.sokampdx;

import lombok.Getter;

@Getter
public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(Position position) {
        this.row = position.getRow();
        this.col = position.getCol();
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
