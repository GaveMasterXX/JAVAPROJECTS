package pt.ipbeja.po2.sokoban.model;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/9/2018
 */

public class Box{
    private int line;
    private int col;

    public Box(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public void goToNextPosition(int line, int col){
        this.line = line;
        this.col = col;
    }
}
