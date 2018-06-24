package pt.ipbeja.po2.sokoban.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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
    private ComboBox comboBox;
    private  Button newGame;

    private String choiceMap = "Default Map";
    private String name = "";
    private  String textMessage  = "Welcome, To Sokoban !!!";

    public SokobanGUI() {
        ButtonHandler buttonHandler = new ButtonHandler();

        this.comboBox = new ComboBox();
        comboBox.getItems().addAll( "Default Map", "Map 1", "Map 2", "Map 3");
        comboBox.setValue("Default Map");

        this.newGame = new Button("New Game");
        this.newGame.setOnAction(buttonHandler);
        dialogbox();

        chooseSwitch(this.choiceMap);
       this.board = new Board(gameBoard);

        board.keeperName = this.name;
        board.choiceMap = this.choiceMap;

        this.hBox = new HBox(board.btnUndo, board.btnRedo);
        this.vBox = new VBox(board.label,this.hBox, this.comboBox, board.btnSave,this.newGame);

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
                this.gameBoard = Maps.levels(option);
                this.choiceMap = option;
                break;
            case "Map 2":
                this.gameBoard = Maps.levels(option);
                this.choiceMap = option;

                break;
            case "Map 3":
                this.gameBoard = Maps.levels(option);
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


    class ButtonHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
           choiceMap = (String) comboBox.getValue();

            chooseSwitch(choiceMap);
            board.resetGame(gameBoard);


        }


    }

}
