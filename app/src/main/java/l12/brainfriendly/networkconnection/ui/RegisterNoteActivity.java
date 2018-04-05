package l12.brainfriendly.networkconnection.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import l12.brainfriendly.networkconnection.model.Nota;
import l12.brainfriendly.networkconnection.model.NotasResponse;
import l12.brainfriendly.networkconnection.R;
import l12.brainfriendly.networkconnection.connection.NotasApi;
import l12.brainfriendly.networkconnection.connection.NotasService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNoteActivity extends AppCompatActivity {

    EditText tituloEditText;
    EditText descripcionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NotasApi service = NotasService.getNotasApi();
        tituloEditText = findViewById(R.id.tituloNota);
        descripcionEditText = findViewById(R.id.descripcionNota);

        Button grabarNotaButton = findViewById(R.id.grabarNota);
        grabarNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarNota(service);
            }
        });


    }

    private void grabarNota(NotasApi service) {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.show();
        Nota nota = new Nota();
        nota.setName(tituloEditText.getText().toString());
        nota.setDescription(descripcionEditText.getText().toString());
        nota.setUserId("001");
        Call<Nota> registarNotaCall = service.registrarNota(nota);
        registarNotaCall.enqueue(new Callback<Nota>() {
            @Override
            public void onResponse(Call<Nota> call, Response<Nota> response) {
                progressBar.hide();
                Log.e("Brandfriendly", response.body().getDescription());
            }

            @Override
            public void onFailure(Call<Nota> call, Throwable t) {
                progressBar.hide();
                Log.e("Brainfriendly", t.getLocalizedMessage());
            }
        });
    }
}
