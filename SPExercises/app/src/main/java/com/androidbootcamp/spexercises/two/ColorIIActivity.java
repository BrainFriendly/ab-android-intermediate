

package com.androidbootcamp.spexercises.two;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidbootcamp.spexercises.R;

public class ColorIIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_02);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
