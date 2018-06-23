package pt.ipbeja.po2.sokoban.model;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/2/2018
 */
public abstract class Cell {
    private boolean isWalkable;
    private boolean hasMarker;

    public Cell(boolean  isWalkable, boolean hasMarker){
        this.isWalkable = isWalkable;
        this.hasMarker = hasMarker;
    }

    public  boolean isWalkable(){return isWalkable;}
    public  boolean isMarker(){return hasMarker;}

}//END abstract class Cell
