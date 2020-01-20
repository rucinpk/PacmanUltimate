package UI;


import constant.Data;
import core.Board;
import entity.Pacman;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * klasa odpowiadajaca za wyswietlanie zyc pacmana
 */
public class LifePane {
    private  static List<Rectangle> lifes;
    private  static HBox lifeTable;
    private  static Board boardState;


    private void clearChildren(){
        lifes.clear();
        lifeTable.getChildren().clear();
        lifeTable.getChildren().removeAll();
    }
    public LifePane(Board boardStateArg){
        int pacLifes = Board.getPacman().getPacmanLifes();
        boardState=boardStateArg;
        lifes = new LinkedList<>();
        lifeTable = new HBox();

        clearChildren();


        for(int i=0; i<pacLifes; i++){
            lifes.add(new Rectangle(Data.BLOCK_SIZE_PX, Data.BLOCK_SIZE_PX));
            loadTexture(i);
        }
    }

    private void loadTexture(int i){
        try {
            FileInputStream fileInputStream = new FileInputStream(Data.HEART_IMAGE);
            Image image = new Image(fileInputStream);
            ImagePattern imagePattern = new ImagePattern(image);
            lifes.get(i).setFill(imagePattern);
            lifeTable.getChildren().add(lifes.get(i));
        }
        catch(Exception e ){
            System.out.println("Error loading Heart Texture");
        }
    }
    public  HBox getLifeTable() {
        return lifeTable;
    }

    public static void render(){
        lifeTable.getChildren().removeAll();
        lifeTable.getChildren().clear();
      for(int i=0; i<boardState.getPacman().getPacmanLifes(); i++){
         lifeTable.getChildren().add(lifes.get(i));
       }
    }
}
