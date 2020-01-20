package entity.abstracts;

import constant.Data;
import entity.abstracts.GameObject;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

/**
 * klasa abstrakcyjna odpowiadajaca za przedstawienie statycznego elementu w grze
 */
public abstract class StaticObject extends GameObject {
    private int staticObjectType;
    public StaticObject(int row, int column, int type){
        super(row, column);
        this.staticObjectType=type;
        switch(staticObjectType){
            case Data.PATH_TYPE:
                loadPathType();
                break;
            case Data.GHOST_DOORS_TYPE:
                loadGhostDoorsType();
                break;
            case Data.WALL_TYPE:
                loadWallType();
                break;
            case Data.POINT_TYPE:
                loadScoreType();
                break;



        }
    }
    private void loadTexture(String FILE_PATH){
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            Image image = new Image(fileInputStream);
            ImagePattern imagePattern = new ImagePattern(image);
            texture.setFill(imagePattern);
        }
        catch(Exception e ){
            System.out.println("Error loading Path Texture");
        }
    }
    private void loadPathType(){
        loadTexture(Data.PATH_IMAGE);
    }
    private void loadWallType(){
        loadTexture(Data.WALL_IMAGE);
    }
    private void loadGhostDoorsType(){
        loadTexture(Data.DOORS_IMAGE);
    }
    private void loadScoreType(){
        loadTexture(Data.POINT_IMAGE);
        texture.setX(column*Data.BLOCK_SIZE_PX+Data.BLOCK_SIZE_PX/(3));
        texture.setY(row*Data.BLOCK_SIZE_PX+Data.BLOCK_SIZE_PX/(3));
        texture.setHeight(Data.POINT_SIZE_PX);
        texture.setWidth(Data.POINT_SIZE_PX);


    }


    public int getStaticObjectType() {
        return staticObjectType;
    }
}
