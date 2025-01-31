package com.cesar.javafx;

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
import java.io.IOException;

public class HelloApplication extends Application {
    public static Scene scene = new Scene(new Group(), 400, 400);
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        // Stage Properties
        int size = 400;
        stage.setTitle("pruebas");

        // Scene Properties
        Group root = new Group();

        root.getChildren().addAll(chessGrid());
        root.getChildren().addAll(pieces());

        scene.setRoot(root);
        scene.setOnMouseClicked(this::logic);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void logic(MouseEvent mouseEvent){
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

        System.out.println(mouseTableroCoord);
        System.out.println(Logic.plano2dTS.get(mouseTableroCoord));
    }

    public Group chessGrid(){
        Group root = new Group();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = new Rectangle();
                int sizeX = (int) (scene.getWidth()/8);
                int sizeY = (int) (scene.getHeight()/8);

                rectangle.setX(j*sizeX/10*10);
                rectangle.setY(i*sizeY/10*10);

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

    public Group pieces() throws FileNotFoundException {
        Group root = new Group();

        int sizeX = (int) (scene.getWidth()/8);
        int sizeY = (int) (scene.getHeight()/8);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView imageView = null;

                switch (i){
                    case 0 -> {
                        switch (j){
                            case 0,7 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/torrenegra.png"));
                                imageView = new ImageView(image);
                            }
                            case 1,6 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/caballonegra.png"));
                                imageView = new ImageView(image);
                            }
                            case 2,5 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/alfilnegra.png"));
                                imageView = new ImageView(image);
                            }
                            case 3 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/reynegra.png"));
                                imageView = new ImageView(image);
                            }
                            case 4 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/reinanegra.png"));
                                imageView = new ImageView(image);
                            }
                            default -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/vacio.png"));
                                imageView = new ImageView(image);
                            }
                        }

                    }
                    case 1 -> {
                        Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/peonnegro.png"));
                        imageView = new ImageView(image);
                    }
                    case 2,3,4,5 -> {
                        Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/vacio.png"));
                        imageView = new ImageView(image);
                    }
                    case 6 -> {
                        Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/peonblanca.png"));
                        imageView = new ImageView(image);
                    }
                    case 7 -> {
                        switch (j){
                            case 0,7 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/torreblanca.png"));
                                imageView = new ImageView(image);
                            }
                            case 1,6 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/caballoblanca.png"));
                                imageView = new ImageView(image);
                            }
                            case 2,5 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/alfilblanca.png"));
                                imageView = new ImageView(image);
                            }
                            case 3 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/reinablanca.png"));
                                imageView = new ImageView(image);
                            }
                            case 4 -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/reyblanca.png"));
                                imageView = new ImageView(image);
                            }
                            default -> {
                                Image image = new Image(new FileInputStream("src/main/resources/com/cesar/assets/vacio.png"));
                                imageView = new ImageView(image);
                            }
                        }
                    }
                }
                imageView.setX(sizeX*j);
                imageView.setY(sizeY*i);
                imageView.setFitHeight(sizeY);
                imageView.setFitWidth(sizeX);

                root.getChildren().addAll(imageView);
            }
        }

        return root;
    }

    public static void main(String[] args) {
        launch();
    }
}