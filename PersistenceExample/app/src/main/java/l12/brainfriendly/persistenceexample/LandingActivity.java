package l12.brainfriendly.persistenceexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import l12.brainfriendly.persistenceexample.persistence.AutoTable;
import l12.brainfriendly.persistenceexample.persistence.MyDataBaseHelper;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        MyDataBaseHelper myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        SQLiteDatabase database = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(AutoTable.TABLE_NAME, null, null, null, null, null, null);
        List<Auto> autos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Auto auto = new Auto();
                auto.setMarca(cursor.getString(cursor.getColumnIndex(AutoTable.MARCA)));
                auto.setAnho(cursor.getInt(cursor.getColumnIndex(AutoTable.ANHO)));
                autos.add(auto);
            } while (cursor.moveToNext());
        }

        for (int i = 0; i < autos.size(); i++) {
            Auto auto = autos.get(i);
            Log.e("auto", auto.getMarca() + " " + auto.getAnho());
        }

//        insertarAuto();
//

//        leerFiles();

//        method();

    }

    private void insertarAuto() {
        Auto auto1 = new Auto();
        auto1.setMarca("Toyota");
        auto1.setModelo("Yaris");
        auto1.setAnho(2013);
        auto1.setPrecio(16000);

        MyDataBaseHelper myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();

        ContentValues autoContentValue = AutoTable.getAutoContentValue(auto1);
        long id = database.insertOrThrow(AutoTable.TABLE_NAME, null, autoContentValue);
        Log.e("BD", "id= " + id);
    }

    private void leerFiles() {
        String FILENAME = "hello_file";
        String content = "hello world!";

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILENAME);
            byte[] buffer = new byte[1000];
            fis.read(buffer);
            String string = new String(buffer, "UTF-8");

            TextView textView = findViewById(R.id.textView);
            textView.setText(string);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void method() {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("autos.json"), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        String autosJson = sb.toString();

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Auto>>() {
        }.getType();
        List<Auto> autos = new Gson().fromJson(autosJson, listType);
        Log.e("Pablo", autosJson);
    }
}
