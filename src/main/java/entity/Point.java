package entity;

import constant.Data;
import entity.abstracts.StaticObject;

/**
 * klasa odpowiedzialne za punkty ktore moze zbierac pacman
 */
public class Point extends StaticObject {

    public Point(int row, int column){
        super(row, column, Data.POINT_TYPE);

    }
}
