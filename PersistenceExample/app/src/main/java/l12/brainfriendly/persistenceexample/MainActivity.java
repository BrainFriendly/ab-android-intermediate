package l12.brainfriendly.persistenceexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences("persistence.txt", Context.MODE_PRIVATE);
        if (settings.getBoolean("estaLogeado", false)) {
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
            startActivity(intent);
        }


        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("persistence.txt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("estaLogeado", true);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                startActivity(intent);
            }
        });

//        EditText passwordEditText = findViewById(R.id.passwordEditText);
//        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
//                int result = actionId & EditorInfo.IME_MASK_ACTION;
//                switch (result) {
//                    case EditorInfo.IME_ACTION_DONE:
//                        Toast.makeText(MainActivity.this, "Hola", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return false;
//            }
//
//        });
    }
}
