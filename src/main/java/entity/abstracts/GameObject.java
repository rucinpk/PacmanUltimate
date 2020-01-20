package entity.abstracts;

import constant.Data;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * klasa abstrakcyjna odpowiedajaca za przedstawienie obiektu w grze
 */
public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected int row;
    protected int column;
    protected Rectangle texture;


    public GameObject(int row, int column){
        this.column = column;
        this.row = row;
        texture = new Rectangle(column*Data.BLOCK_SIZE_PX, row*Data.BLOCK_SIZE_PX, Data.BLOCK_SIZE_PX, Data.BLOCK_SIZE_PX);
        this.texture.setX(column*Data.BLOCK_SIZE_PX);
        this.texture.setY(row*Data.BLOCK_SIZE_PX);

    }

    public Rectangle getTexture() {
        return texture;
    }

    public Bounds getBoundary()
    {
        return texture.getBoundsInLocal();
    }
    public boolean intersects(GameObject s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }
    public boolean intersects(double x, double y, double w, double h, GameObject s)
    {
        return s.getBoundary().intersects(x, y,w, h);


    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }



    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


}
