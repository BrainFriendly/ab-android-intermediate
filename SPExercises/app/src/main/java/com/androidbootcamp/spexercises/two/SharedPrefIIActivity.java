package com.androidbootcamp.spexercises.two;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidbootcamp.spexercises.R;

public class SharedPrefIIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref_02);

        findViewById(R.id.buttonLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToColorView();
            }
        });
    }

    private void goToColorView(){
        startActivity(new Intent(this,ColorIIActivity.class));
    }
}
