package pt.ipbeja.po2.sokoban.model;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/9/2018
 */

public class Keeper {

    private int line;
    private int col;

    private static Keeper instance = null;

    private Keeper() {
    }

    public static Keeper getInstance(){
        if(instance == null){
            instance = new Keeper();
        }
        return instance;
    }

    // TODO métodos mover, getter

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public void goToPosition(int line, int col){
        this.line = line;
        this.col = col;


    }

}
