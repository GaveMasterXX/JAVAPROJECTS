package pt.ipbeja.po2.sokoban.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipbeja.po2.sokoban.model.*;
import pt.ipbeja.po2.sokoban.model.Cell;

import java.util.ArrayList;
import java.util.List;


/**
 * Creaed by Tiago Campos Nº 13953
 * 5/2/2018
 */

public class Board extends GridPane {
    private Buttons[][] buttons;
    private GameBoard gameBoard;
    private String histMoves = "Long Keeper Moves: \n";
    private int lastPosLine = 0;
    private int lastPosCol = 0;

    public Button btnUndo;
    public Button btnRedo;
    public Label label;
    public TextArea textArea;
    public Alert alert;

    public String keeperName = "";
    public String choiceMap = "";

    public Board(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        buttons = new Buttons[this.gameBoard.getLines()][this.gameBoard.getCols()];
        createBoard();
        updateBoard();
        this.label = new Label(this.score());
        this.label.setMinSize(150, 50);
        this.textArea = new TextArea();

    }


    private void createBoard() {
        ButtonsHandler handler = new ButtonsHandler();
        UndoButtonHandler undoButtonHandler = new UndoButtonHandler();
        RedoButtonHandler redoButtonHandler = new RedoButtonHandler();

        this.btnUndo = new Button("Undo");
        this.btnUndo.setOnAction(undoButtonHandler);

        this.btnRedo = new Button("Redo");
        this.btnRedo.setOnAction(redoButtonHandler);

        for (int line = 0; line < gameBoard.getLines(); line++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                buttons[line][col] = new Buttons(line, col);
                buttons[line][col].setMaxSize(50, 50);
                buttons[line][col].setMinSize(50, 50);
                buttons[line][col].setOnAction(handler);
                this.add(buttons[line][col], col, line);
                drawBoard(line, col);
            }
        }
    }

    private void drawBoard(int line, int col) {
        Cell cell = gameBoard.getMap()[line][col];
        if (cell.isWalkable() && !cell.isMarker()) {
            buttons[line][col].setFloor();
        } else if (cell.isWalkable() && cell.isMarker()) {
            buttons[line][col].setMarker();
            ;
        } else if (!cell.isWalkable() && !cell.isMarker()) {
            buttons[line][col].setWall();
        }
        checkForKeeper(line, col);
        checkForBox(line, col);

    }


    private void updateBoard() {
        for (int line = 0; line < gameBoard.getLines(); line++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                drawBoard(line, col);
            }
        }
    }

    private void checkForKeeper(int line, int col) {
        if (gameBoard.getKeeper().getLine() == line && gameBoard.getKeeper().getCol() == col) {
            buttons[line][col].setKeeper(); //add image for the position of the keeper
        }
    }

    private void checkForBox(int line, int col) {
        for (Box box : gameBoard.getBoxes()) {
            if (box.getLine() == line && box.getCol() == col) {
                buttons[line][col].setBox();
            }

        }

    }

    private void winnigMessage() {
        String message = "";
        String name = gameBoard.getScores().get(0).getKeeperName();
        String level = gameBoard.getScores().get(0).getLevalName();
        int points = gameBoard.getScores().get(0).getPoints();


        for (int i = 0; i < gameBoard.getScores().size(); i++) {
            if( i == 0){
                message += "Player: " + gameBoard.getScores().get(i).getKeeperName() + " " + gameBoard.getScores().get(i).getLevalName() + " Pontos: " + gameBoard.getScores().get(i).getPoints() + " ***\n";
            }else if(i < 10){
                message += "Player: " + gameBoard.getScores().get(i).getKeeperName() + " " + gameBoard.getScores().get(i).getLevalName() + " Pontos: " + gameBoard.getScores().get(i).getPoints() + "\n";
            }

        }

        this.alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End Game");
        alert.setHeaderText("Congratulations you Win!!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String score() {
        String position = "";

        position = "Total Points: (" + gameBoard.points + ")";

        return position;
    }

    private String historicMoves() {
        histMoves += "Move from (" + lastPosLine + ", " + lastPosCol + ") --> (" + gameBoard.getKeeper().getLine() + ", " + gameBoard.getKeeper().getCol() + ")\n";
        return histMoves;
    }

    class ButtonsHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Buttons buttons = (Buttons) event.getSource();

            lastPosLine = gameBoard.getKeeper().getLine();
            lastPosCol = gameBoard.getKeeper().getCol();

            if (gameBoard.moveKeeper(buttons.getLine(), buttons.getCol())) {
                gameBoard.writeSteps();
                gameBoard.positionCopy.clear();
                textArea.setText(historicMoves());
            }

            updateBoard();
            label.setText(score());
            if (gameBoard.isWinnigPosition()) {
                try{
                    gameBoard.readScoreFromFile();
                    gameBoard.setScore(keeperName, choiceMap, gameBoard.points);
                    gameBoard.writeToFileScores();
                    winnigMessage();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }

    }

    private class UndoButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            lastPosLine = gameBoard.getKeeper().getLine();
            lastPosCol = gameBoard.getKeeper().getCol();

            gameBoard.undoLastMove();
            updateBoard();
            label.setText(score());
            textArea.setText(historicMoves());
        }
    }

    private class RedoButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            lastPosLine = gameBoard.getKeeper().getLine();
            lastPosCol = gameBoard.getKeeper().getCol();

            gameBoard.redo();
            updateBoard();
            label.setText(score());
            textArea.setText(historicMoves());
        }
    }

}
