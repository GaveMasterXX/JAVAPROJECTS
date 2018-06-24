package pt.ipbeja.po2.sokoban.model;

import pt.ipbeja.po2.sokoban.model.cells.Floor;
import pt.ipbeja.po2.sokoban.model.cells.PlaceToPut;
import pt.ipbeja.po2.sokoban.model.cells.Wall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/16/2018
 */

public class Maps {
    private static String levelString = "";

    private static String[] level;
    private static Cell[][] map;
    private static List<Box> boxes;
    private static Keeper keeper;

    public static GameBoard levels(String option) {
        switch (option){
            case "Map 1":
                levelString = "";
                readMapFromFile("Map1.txt");
                level = levelString.split("\r\n|\r|\n");
                buildMapLevel();
                break;
            case "Map 2":
                levelString = "";
                readMapFromFile("Map2.txt");
                level = levelString.split("\r\n|\r|\n");
                buildMapLevel();
                break;
            case "Map 3":
                levelString = "";
                readMapFromFile("Map3.txt");
                level = levelString.split("\r\n|\r|\n");
                buildMapLevel();
                break;
        }

        return new GameBoard(map, boxes, keeper);
    }

    private static void readMapFromFile(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String s = "";

            while ((s = in.readLine()) != null ) {
                String[] var = s.split("\n");
                    levelString += var[0] + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void prepareMap(int width){
        for (int i = 0; i < level.length; i++) {
            if (level[i].length() < width) {
                while (level[i].length() < width) {
                    level[i] += " ";
                }
            }
        }
    }


    private static int getMapWidth(){
        int width = level[0].length();
        for (int i = 1; i < level.length; i++) {
            if (level[i].length() > width) {
                width = level[i].length();
            }


        }
        return width;
    }

    public static void buildMapLevel() {
        boxes = new ArrayList<>();
        int heigth = level.length;
        int width = getMapWidth();
       prepareMap(width);
        map = new Cell[heigth][width];
        for (int line = 0; line < heigth; line++) {
            for (int col = 0; col < width; col++) {
                // TODO: Read individual characters from level and assign cells accordingly
                String option = String.valueOf(level[line].charAt(col));
                switch (option){
                    case "#"://Wall
                        map[line][col] = new Wall();
                        break;
                    case "$"://Box
                        map[line][col] = new Floor();

                        boxes.add(new Box(line, col));
                        break;
                    case "*"://Box in PlaceToPut
                        map[line][col] = new PlaceToPut();
                        boxes.add(new Box(line, col));
                        break;
                    case "."://PlaceToPut
                        map[line][col] = new PlaceToPut();
                        break;
                    case "@"://Player
                        map[line][col] = new Floor();
                        keeper = Keeper.getInstance();
                        keeper.goToPosition(line, col);
                        break;
                    case " "://flor
                        map[line][col] = new Floor();
                        break;
                    default://Floor
                        map[line][col] = new Floor();
                        break;
                }

                if(option == null){
                    map[line][col] = new Floor();
                }

            }
        }


    }

    public static GameBoard buildSampleLevel() {
        int height = 7;
        int width = 7;

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
