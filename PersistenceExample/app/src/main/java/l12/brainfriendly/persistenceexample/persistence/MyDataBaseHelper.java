package l12.brainfriendly.persistenceexample.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author pjohnson on 15/03/18.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "BrainFriendly";
    public static final int DB_VERSION = 1;

    private static MyDataBaseHelper instance;

    private MyDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized MyDataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDataBaseHelper(context);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AutoTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("Drop TABLE IF EXISTS " + AutoTable.TABLE_NAME);
            onCreate(db);
        }
    }
}
