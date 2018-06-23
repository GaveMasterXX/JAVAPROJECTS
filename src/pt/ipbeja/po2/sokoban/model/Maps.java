package pt.ipbeja.po2.sokoban.model;

import pt.ipbeja.po2.sokoban.model.cells.Floor;
import pt.ipbeja.po2.sokoban.model.cells.PlaceToPut;
import pt.ipbeja.po2.sokoban.model.cells.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/16/2018
 */

public class Maps {
    private static String levelString =
            "    ####\n" +
                    "#####  #\n" +
                    "#   $  #\n" +
                    "# *.#  #\n" +
                    "## ## ##\n" +
                    "#      #\n" +
                    "# @#   #\n" +
                    "#  #####\n" +
                    "####";

    private static String[] level = levelString.split("\r\n|\r|\n");
    private static Cell[][] map;
    private static List<Box> boxes;
    private static Keeper keeper;

    public static GameBoard levels() {
        buildMapLevel();
        return new GameBoard(map, boxes, keeper);
    }


    private static int getMapWidth() {
        int width = level[0].length();
        for (int i = 1; i < level.length; i++) {
            if (level[i].length() > width) {
                width = level[i].length();
            }
        }
        return width;
    }

    private static void buildMapLevel() {
        int heigth = level.length;
        int width = getMapWidth();
        map = new Cell[heigth][width];
        for (int line = 0; line < heigth; line++) {
            for (int col = 0; col < width; col++) {
                // TODO: Read individual characters from level and assign cells accordingly

            }
        }
    }

    public static GameBoard buildSampleLevel() {
        int height = 10;
        int width = 10;

        // build the sample map
        map = new Cell[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == height - 1 || col == width - 1) {
                    map[row][col] = new Wall();
                } else {
                    map[row][col] = new Floor();
                }
            }
        }
        map[1][1] = new PlaceToPut();
        map[5][5] = new PlaceToPut();
        boxes = new ArrayList<>();
        boxes.add(new Box(4, 4));
        boxes.add(new Box(4, 3));


        // places the keeper in the centre
        keeper = Keeper.getInstance();
        keeper.goToPosition(height / 2, width / 2);

        // returns the board with a new (empty) list of boxes
        return new GameBoard(map, boxes, keeper);
    }

}
