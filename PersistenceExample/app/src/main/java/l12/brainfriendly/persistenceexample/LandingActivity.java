package l12.brainfriendly.persistenceexample;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

        SharedPreferences sp = getSharedPreferences("persistence.txt", Context.MODE_PRIVATE);
        if (!sp.getBoolean("inicializado", false)) {
            List<Auto> listaAutos = leerArchivoDeAssets();
            for (Auto auto : listaAutos) {
                insertarAuto(auto);
            }
            sp.edit().putBoolean("inicializado", true).apply();
        }


        RecyclerView autosRecyclerView = findViewById(R.id.autosRecyclerView);
        autosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        autosRecyclerView.setAdapter(new AutosRecyclerAdapter(leerDeBaseDatos()));

    }

    private List<Auto> leerDeBaseDatos() {
        MyDataBaseHelper myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        SQLiteDatabase database = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(AutoTable.TABLE_NAME, null, null, null, null, null, null);
        List<Auto> autos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Auto auto = new Auto();
                auto.setMarca(cursor.getString(cursor.getColumnIndex(AutoTable.MARCA)));
                auto.setModelo(cursor.getString(cursor.getColumnIndex(AutoTable.MODELO)));
                auto.setAnho(cursor.getInt(cursor.getColumnIndex(AutoTable.ANHO)));
                auto.setImagen(cursor.getString(cursor.getColumnIndex(AutoTable.IMAGEN)));
                autos.add(auto);
            } while (cursor.moveToNext());
        }
        return autos;
    }

    private void insertarAuto(Auto auto1) {
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

//            TextView textView = findViewById(R.id.textView);
//            textView.setText(string);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Auto> leerArchivoDeAssets() {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("autos.json"));
            reader = new BufferedReader(isr);
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

        Type listType = new TypeToken<ArrayList<Auto>>() {
        }.getType();

        List<Auto> autos = new Gson().fromJson(autosJson, listType);

        Log.e("Pablo", autosJson);

        return autos;
    }
}
