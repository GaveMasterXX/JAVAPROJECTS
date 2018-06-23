package pt.ipbeja.po2.sokoban.model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/9/2018
 */

public class GameBoard {
    private Cell[][] map;
    private List<Box> boxes;
    private List<Position> position;
    public List<Position> positionCopy;
    private List<String> fileDta = new ArrayList<String>();
    private Keeper keeper;
    public int points;
    private List<Score> score;

    public GameBoard(Cell[][] map, List<Box> boxes, Keeper keeper) {
        this.map = map;
        this.boxes = boxes;
        this.keeper = keeper;

        this.score = new ArrayList<Score>();
        this.position = new ArrayList<Position>();
        this.positionCopy = new ArrayList<Position>();
        position.add(new Position(keeper.getLine(), keeper.getCol()));
    }

    public Cell[][] getMap() {
        return map;
    }

    public int getLines() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public List<Score> getScores() {
        return score;
    }

    public Keeper getKeeper() {
        return keeper;
    }

    public void setScore(String keeperName, String levelName, int points) {

        score.add(new Score(keeperName, levelName, points));

        Collections.sort(score, new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                return s1.getPoints() - s2.getPoints();
            }
        });
    }

    public boolean moveKeeper(int line, int col) {
        int nextLine = nextMovesCalulate(line, col).getRow();
        int nextCol = nextMovesCalulate(line, col).getCol();
        if (isValidPosition(line, col)) {
            if (existsBox(line, col) && (!isWalkable(nextLine, nextCol) || existsBox(nextLine, nextCol))) {
                return false;
            } else {
                moveBox(line, col, nextLine, nextCol);
                keeper.goToPosition(line, col);
                this.points++;
                position.add(new Position(line, col));
                return true;
            }
        }
        return false;
    }


    public boolean moveBox(int line, int col, int nextLine, int nextCol) {

        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getCol() == col && boxes.get(i).getLine() == line) {
                if (isWithInBoardLimits(nextLine, nextCol) && isWalkable(nextLine, nextCol) && !existsBox(nextLine, nextCol)) {
                    boxes.get(i).goToNextPosition(nextLine, nextCol);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isWinnigPosition() {
        int n = 0;
        for (int i = 0; i < boxes.size(); i++) {
            if (isMarker(boxes.get(i).getLine(), boxes.get(i).getCol())) {
                n++;
                if (n == boxes.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void redo() {
        int flag = 0;
        if (positionCopy.size() != 1) {
            for (int i = 0; i < positionCopy.size(); i++) {
                if (positionCopy.get(i).getRow() == keeper.getLine() && positionCopy.get(i).getCol() == keeper.getCol() && (i + 1 != positionCopy.size()) && (flag < 1)) {
                    flag++;
                    moveKeeper(positionCopy.get(i + 1).getRow(), positionCopy.get(i + 1).getCol());
                    if (this.points >= 0) {
                        this.points++;
                    }
                }
            }
        }
    }


    public void undoLastMove() {
        if (position.size() != 1) {
            int lineKeeper = position.get(position.size() - 2).getRow(); //next position keeper line
            int colKeeper = position.get(position.size() - 2).getCol();//next position keeper col
            moveKeeper(lineKeeper, colKeeper);

            positionCopy.add(position.get(position.size() - 1));
            this.position.remove(position.size() - 1);

            int lineBox = position.get(position.size() - 1).getRow(); //target position line
            int colBox = position.get(position.size() - 1).getCol(); //target position col
            int line = nextMovesCalulate(lineBox, colBox).getRow();//next position box line
            int col = nextMovesCalulate(lineBox, colBox).getCol();//next position box col
            moveBox(line, col, lineBox, colBox);

            positionCopy.add(position.get(position.size() - 1));
            this.position.remove(position.size() - 1);

            if (this.points != 0) {
                this.points -= 2;
            }
        }

    }

    public void readScoreFromFile() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("Score.txt"));
            String s = "";

            while ((s = in.readLine()) != null) {
                String[] var = s.split(";");
                String name = var[0];
                String level = var[1];
                int points = Integer.parseInt(var[2]);

                System.out.println(name + ", " + level + ", " + points);
                this.setScore(name, level, points);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeToFileScores() throws IOException {
        for (int i = 0; i < score.size(); i++) {
            fileDta.add(score.get(i).getKeeperName() + ";" + score.get(i).getLevalName() + ";" + score.get(i).getPoints());
        }
        Path file = Paths.get("Score.txt");
        Files.write(file, fileDta, Charset.forName("UTF-8"));
    }

    public void writeSteps(){
        try {
            fileDta.add("MAP 1; ;");
            for (int i = 0; i < position.size(); i++) {
                fileDta.add(position.get(i).getRow() + ";" + position.get(i).getCol());
            }
            Path file = Paths.get("save.txt");
            // Files.write(file, " MAP 1", Charset.forName("UTF-8"));
            Files.write(file, fileDta, Charset.forName("UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private Position nextMovesCalulate(int line, int col) {
        Position source = new Position(keeper.getLine(), keeper.getCol());
        Position target = new Position(line, col);
        return Position.getNextPosition(source, target);
    }


    private boolean existsBox(int line, int col) {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getCol() == col && boxes.get(i).getLine() == line) {
                return true;
            }
        }
        return false;
    }


    private boolean isValidPosition(int line, int col) {
        if (isWithInBoardLimits(line, col) && keeperIsAdjacent(line, col) && isWalkable(line, col)) {
            return true;
        }
        return false;
    }

    private boolean isWalkable(int line, int col) {
        return map[line][col].isWalkable();
    }

    private boolean isMarker(int line, int col) {
        return map[line][col].isMarker();
    }

    private boolean keeperIsAdjacent(int line, int col) {
        if (keeper.getLine() == line && (keeper.getCol() == col + 1 || keeper.getCol() == col - 1)) {
            return true;
        } else if (keeper.getCol() == col && (keeper.getLine() == line + 1 || keeper.getLine() == line - 1)) {
            return true;
        }
        return false;
    }

    private boolean isWithInBoardLimits(int line, int col) {
        if (line >= 0 && line < getLines() && col >= 0 && col <= getCols()) {
            return true;
        }
        return false;
    }

}
