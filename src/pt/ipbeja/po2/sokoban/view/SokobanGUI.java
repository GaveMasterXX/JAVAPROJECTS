package pt.ipbeja.po2.sokoban.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipbeja.po2.sokoban.model.GameBoard;
import pt.ipbeja.po2.sokoban.model.Maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Creaed by Tiago Campos Nº 13953
 * 5/16/2018
 */

public class SokobanGUI extends BorderPane {
    private GameBoard gameBoard;
    private Board board;
    public TextInputDialog dialog;
    public ChoiceDialog<String> choiceDialog;
    private VBox vBox;
    private HBox hBox;
    private Button btnChoiceMaps;

    private String choiceMap = "";
    private String name = "";
    private  String textMessage  = "Welcome, To Sokoban !!!";

    public SokobanGUI() {
        dialogbox();
        choicebox();

        chooseSwitch(this.choiceMap);
        this.board = new Board(gameBoard);
        board.keeperName = this.name;
        board.choiceMap = this.choiceMap;
        this.hBox = new HBox(board.btnUndo, board.btnRedo);
        this.vBox = new VBox(board.label,this.hBox, this.btnChoiceMaps);
        this.setLeft(vBox);
        this.setCenter(board);
        this.setRight(board.textArea);
    }


    public void chooseSwitch(String option) {
        switch (option) {
            case "Default Map":
                this.gameBoard = Maps.buildSampleLevel();
                this.choiceMap = option;
                break;
            case "Map 1":
                this.gameBoard = Maps.buildSampleLevel();
                this.choiceMap = option;
                break;
            case "Map 2":
                this.gameBoard = Maps.buildSampleLevel();
                this.choiceMap = option;
                break;
            case "Map 3":
                this.gameBoard = Maps.buildSampleLevel();
                this.choiceMap = option;
                break;
        }
    }


    private void dialogbox() {
        this.dialog = new TextInputDialog();
        try {
            dialog.setTitle("Text Input");
            dialog.setHeaderText(this.textMessage);
            dialog.setContentText("Please insert your name:");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                if (result.get().length()  < 9){
                    System.out.println("Your name: " + result.get());
                    this.name = result.get();
                }else{
                    this.textMessage = "Please insert name less or iqual to 8 chars !!!";
                    this.dialogbox();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dialog.close();
        }
    }

    private void choicebox() {
        List<String> choices = new ArrayList<>();
        choices.add("Map 1");
        choices.add("Map 2");
        choices.add("Map 3");
        this.choiceDialog = new ChoiceDialog<>("Default Map", choices);
        try {
            choiceDialog.setTitle("Choice Dialog");
            choiceDialog.setHeaderText("Before start the game you have to chose a map to play!!!");
            choiceDialog.setContentText("Chose the may you like:");

            Optional<String> result = choiceDialog.showAndWait();
            if (result.isPresent()) {
                this.choiceMap =  result.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dialog.close();
        }

    }

    class MapButtonHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
}
