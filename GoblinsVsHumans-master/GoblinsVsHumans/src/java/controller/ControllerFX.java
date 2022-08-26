package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerFX extends Application {
    private  static final int SCENE_WIDTH = 600;
    private  static final int SCENE_HIGHT = 500;
    private Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane();
        root.setPrefSize(SCENE_WIDTH,SCENE_HIGHT);


        Scene scene = new Scene(root);
        stage.setTitle("Humans Vs Goblins!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }
    public static void main(String[] args) {
        launch();
    }

}
