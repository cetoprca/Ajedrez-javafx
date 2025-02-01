package com.cesar.javafx;

import com.cesar.ajedrez.Pieza;
import com.cesar.ajedrez.Tablero;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static Scene scene = new Scene(new Group(), 400, 400);
    public static Tablero tablero = new Tablero();
    public static List<Coord> mouseCoords = new ArrayList<>();
    @Override
    public void start(Stage stage) {
        // Stage Properties
        stage.setTitle("pruebas");

        // Scene Properties
        scene.setOnMouseClicked(this::logic);

        tablero.reset();
        actualizarTablero();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void logic(MouseEvent mouseEvent) {
        int mouseY = (int) (mouseEvent.getY()/10*10);
        int mouseX = (int) (mouseEvent.getX()/10*10);
        int sizeX = (int) (scene.getWidth()/8);
        int sizeY = (int) (scene.getHeight()/8);

        Coord mouseTableroCoord = new Coord();

        for (int i = 0; i < 8; i++) {
            if (mouseY > sizeY*i){
                mouseTableroCoord.setY(i);
            }
            if (mouseX > sizeX*i){
                mouseTableroCoord.setX(i);
            }
        }

        if (mouseCoords.isEmpty()){
            mouseCoords.add(mouseTableroCoord);
            selectSquare(mouseTableroCoord, Color.LIGHTBLUE);
        }else {
            mouseCoords.add(mouseTableroCoord);
            selectSquare(mouseTableroCoord, Color.PINK);
            if (tablero.getTablero2d()[mouseCoords.getFirst().getY()][mouseCoords.getFirst().getX()] != null){
                if(tablero.getTablero2d()[mouseCoords.getFirst().getY()][mouseCoords.getFirst().getX()].isBando() == tablero.jugadorActivo){
                    tablero.move(mouseCoords.getFirst().getY(), mouseCoords.getFirst().getX(), mouseCoords.get(1).getY(), mouseCoords.get(1).getX(), false);
                    actualizarTablero();
                    selectSquare(mouseTableroCoord, Color.LIGHTGREEN);
                }
            }
            mouseCoords.clear();
        }

    }

    public Group chessGrid(){
        Group root = new Group();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = new Rectangle();
                int sizeX = (int) (scene.getWidth()/8);
                int sizeY = (int) (scene.getHeight()/8);

                rectangle.setX((double) (j * sizeX) /10*10);
                rectangle.setY((double) (i * sizeY) /10*10);

                rectangle.setWidth(sizeX);
                rectangle.setHeight(sizeY);

                Logic.plano2dST.put(new Coord((int) rectangle.getX(), (int) rectangle.getY()), new Coord(j, i));
                Logic.plano2dTS.put(new Coord(j, i), new Coord((int) rectangle.getX(), (int) rectangle.getY()));

                rectangle.setFill((j%2==0 && i%2==0) || (j%2==1 && i%2==1) ? Color.WHITE : Color.GRAY);

                root.getChildren().addAll(rectangle);
            }
        }

        return root;
    }

    public Group pieces(Pieza[][] tablero2d) {
        Group root = new Group();

        int sizeX = (int) (scene.getWidth()/8);
        int sizeY = (int) (scene.getHeight()/8);



        for (int i = 0; i < tablero2d.length; i++) {
            for (int j = 0; j < tablero2d.length; j++) {
                StringBuilder path = new StringBuilder("src/main/resources/com/cesar/assets/");
                if (tablero2d[i][j] != null){
                    switch (tablero2d[i][j].getId().id()){
                        case "torre" -> path.append("torre");
                        case "alfil" -> path.append("alfil");
                        case "caballo" -> path.append("caballo");
                        case "rey" -> path.append("rey");
                        case "reina" -> path.append("reina");
                        case "peon" -> path.append("peon");
                    }
                    if (tablero2d[i][j].isBando()){
                        path.append("blanco.png");
                    }else path.append("negro.png");
                }else path.append("vacio.png");

                Image image = null;
                try {
                    image = new Image(new FileInputStream(path.toString()));
                } catch (FileNotFoundException _) {

                }
                ImageView imageView = new ImageView(image);
                imageView.setY(sizeY*i);
                imageView.setX(sizeX*j);
                root.getChildren().addAll(imageView);
            }
        }

        return root;
    }

    public void actualizarTablero() {
        Group root = new Group();
        root.getChildren().addAll(chessGrid());
        root.getChildren().addAll(pieces(tablero.getTablero2d()));

        scene.setRoot(root);
    }

    public void selectSquare(Coord coord, Color color){
        int sizeX = (int) (scene.getWidth()/8);
        int sizeY = (int) (scene.getHeight()/8);


        Group root = new Group();
        root.getChildren().addAll(chessGrid());
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(color);
        rectangle.setHeight(sizeY);
        rectangle.setWidth(sizeX);

        rectangle.setY(Logic.getScreenCoord(coord).getY());
        rectangle.setX(Logic.getScreenCoord(coord).getX());

        root.getChildren().addAll(rectangle);

        root.getChildren().addAll(pieces(tablero.getTablero2d()));

        scene.setRoot(root);
    }

    public static void main(String[] args) {
        launch();
    }
}