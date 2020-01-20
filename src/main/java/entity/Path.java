package entity;

import constant.Data;
import entity.abstracts.StaticObject;


/**
 * klasa sciezki po ktorej moga poruszac sie obiekty
 */
public class Path extends StaticObject {


    public Path(int row, int column) {
        super(row, column, Data.PATH_TYPE);
    }

}
