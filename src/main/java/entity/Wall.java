package entity;

import constant.Data;
import entity.abstracts.StaticObject;

/**
 * klasa odpowiedzialne za sciane przez ktora nie moga przechodzic obiekty
 */
public class Wall extends StaticObject {
    public Wall(int row, int column) {
        super(row, column, Data.WALL_TYPE);
    }
}
