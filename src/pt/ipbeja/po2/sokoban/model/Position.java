package pt.ipbeja.po2.sokoban.model;

import java.util.Objects;

/**
 * Creaed by Tiago Campos Nº 13953
 * 6/6/2018
 */

public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public static Position getNextPosition(Position source, Position target) {
        int dirX = target.col - source.col;
        int dirY = target.row - source.row;

        return new Position(target.row + dirY, target.col + dirX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                col == position.col;
    }

    @Override
    public int hashCode() {

        return Objects.hash(row, col);
    }
}
