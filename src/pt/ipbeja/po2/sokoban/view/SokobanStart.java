package pt.ipbeja.po2.sokoban.view;/**
 * Creaed by Tiago Campos Nº 13953
 * 5/2/2018
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SokobanStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SokobanGUI gui = new SokobanGUI();
        Scene scene = new Scene(gui);
        primaryStage.setScene(scene);

        primaryStage.show();


    }
}
