package l12.brainfriendly.persistenceexample.persistence;

import android.content.ContentValues;

import l12.brainfriendly.persistenceexample.Auto;

/**
 * @author pjohnson on 15/03/18.
 */

public class AutoTable {

    public static final String TABLE_NAME = "autos";
    public static final String ID = "id";
    public static final String MARCA = "marca";
    public static final String MODELO = "modelo";
    public static final String PRECIO = "precio";
    public static final String ANHO = "anho";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "( "
            + ID + " INTEGER, "
            + MARCA + " TEXT, "
            + MODELO + " TEXT, "
            + PRECIO + " REAL, "
            + ANHO + " INTEGER "
            + " )";

    public static ContentValues getAutoContentValue(Auto auto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MARCA, auto.getMarca());
        contentValues.put(MODELO, auto.getModelo());
        contentValues.put(PRECIO, auto.getPrecio());
        contentValues.put(ANHO, auto.getAnho());

        return contentValues;
    }

}
