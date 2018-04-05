package l12.brainfriendly.networkconnection.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import l12.brainfriendly.networkconnection.R;
import l12.brainfriendly.networkconnection.connection.NotasApi;
import l12.brainfriendly.networkconnection.connection.NotasService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteDetailActivity extends AppCompatActivity {

    EditText tituloEditText;
    EditText descripcionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        tituloEditText = findViewById(R.id.tituloNota);
        descripcionEditText = findViewById(R.id.descripcionNota);

        final Bundle bundle = getIntent().getExtras();
        tituloEditText.setText(bundle.getString("tituloNota"));
        descripcionEditText.setText(bundle.getString("descripcionNota"));

        Button deleteButton = findViewById(R.id.deleteNoteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotasApi notasApi = NotasService.getNotasApi();
                Call<Void> eliminarNotaCall = notasApi.eliminarNota(bundle.getString("idNota"));
                eliminarNotaCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });
    }
}
