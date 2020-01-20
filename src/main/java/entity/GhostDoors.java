package entity;

import constant.Data;
import entity.abstracts.StaticObject;

/**
 * klasa drzwi dla duszkow
 */
public class GhostDoors extends StaticObject {
    public GhostDoors(int row, int column){
        super(row, column, Data.GHOST_DOORS_TYPE);
        this.texture.setRotate(90);
    }
}
