package pt.ipbeja.po2.sokoban.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/8/2018
 */

public class Buttons extends Button {

    private int line;
    private int col;

    private static final Image MAP_BOX = new Image("box.png");
    private static final Image MAP_BOXINMARKER = new Image("boxInMarker.png");
    private static final Image MAP_WALL = new Image("wall.png");
    private static final Image MAP_FLOOR = new Image("floor.png");
    private static final Image MAP_MARKER = new Image("marker.png");
    private static final Image MAP_KEEPER = new Image("keeper.png");
   // private static final Image MAP_EMPTY = new Image("/resources/noplayer.png");

    private final ImageView imageView;

    public Buttons(int line, int col) {
        this.line = line;
        this.col = col;
        this.imageView = new ImageView();
        this.setGraphic(imageView);
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    /**
     * Stes board image of the Box('B')
     */
    public void setBox(){this.imageView.setImage(MAP_BOX);}

    /**
     * Stes board image of the Wall('W')
     */
    public void setWall(){this.imageView.setImage(MAP_WALL);}

    /**
     * Stes board image of the Floor('F')
     */
    public void setFloor(){this.imageView.setImage(MAP_FLOOR);}

    /**
     * Stes board image of the Marker('M')
     */
    public void setMarker(){this.imageView.setImage(MAP_MARKER);}

    /**
     * Stes board image of the Keeper('K')
     */
    public void setKeeper(){this.imageView.setImage(MAP_KEEPER);}

    /**
     * Stes board imgae when box is in the posion where exists one marker('BM')
     */
    public void setBoxInMarker(){this.imageView.setImage(MAP_BOXINMARKER);}
}
