package entity.abstracts;

import constant.Data;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

/**
 * klasa abstrakcyjna odpowiadajaca za przedstawienie poruszajacego sie elementu w grze
 */
public abstract class MovingObject extends GameObject {

    protected int movingObjectType;
    public MovingObject(int row, int column, int type) {
        super(row, column);
        this.movingObjectType = type;

        switch(movingObjectType){
            case Data.PACMAN_TYPE:
                loadTexture(Data.PACMAN_IMAGE);
                break;
            case Data.GHOST_ASTAR_TYPE:
                loadTexture(Data.GHOST_IMAGE);
                break;
            case Data.GHOST_ASTAR_FORWARD_TYPE:
                loadTexture(Data.GREEN_GHOST_IMAGE);
                break;
        }
    }

    private void loadTexture(final String IMAGE_PATH){
        try{
            FileInputStream fileInputStream = new FileInputStream(IMAGE_PATH);
            Image image = new Image(fileInputStream);
            ImagePattern imagePattern = new ImagePattern(image);
            texture = new Rectangle(column*Data.BLOCK_SIZE_PX, row*Data.BLOCK_SIZE_PX, Data.MOVING_OBJECT_SIZE_PX, Data.MOVING_OBJECT_SIZE_PX);
            texture.setFill(imagePattern);
        }
        catch(Exception e){
            System.out.println("error loading ghost texture");
        }
    }

}
