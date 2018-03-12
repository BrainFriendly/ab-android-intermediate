package com.androidbootcamp.androidtemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by emedinaa on 15/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void next(Class<?> activityClass, Bundle bundle, boolean destroy){
        Intent intent= new Intent(this, activityClass);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (destroy) {
            finish();
        }
    }

    protected void showMessage(String message){
        Log.d("CONSOLE",String.format("> %s",message));
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
