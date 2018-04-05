package l12.brainfriendly.networkconnection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import l12.brainfriendly.networkconnection.R;
import l12.brainfriendly.networkconnection.connection.NotasApi;
import l12.brainfriendly.networkconnection.connection.NotasService;
import l12.brainfriendly.networkconnection.model.Nota;
import l12.brainfriendly.networkconnection.model.NotasResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity implements NotesAdapter.NotesInterface {

    private NotesAdapter adapter;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        RecyclerView recyclerView = findViewById(R.id.notasRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(new ArrayList<Nota>());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        final NotasApi notasApi = NotasService.getNotasApi();
        obtenerNotas(notasApi);

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        obtenerNotas(notasApi);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

    }


    private void obtenerNotas(NotasApi service) {
        Call<NotasResponse> notasResponse = service.obtenerNotas();
        notasResponse.enqueue(new Callback<NotasResponse>() {
            @Override
            public void onResponse(Call<NotasResponse> call, Response<NotasResponse> response) {
                //esconder spinner
                if (response.body().getNotas() != null) {
                    adapter.setNotas(response.body().getNotas());
                }
            }

            @Override
            public void onFailure(Call<NotasResponse> call, Throwable t) {
                //esconder spinner
                Log.e("Brainfriendly", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.newNote) {
            Intent intent = new Intent(this, RegisterNoteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClicked(Nota note) {
        Intent intent = new Intent(this, NoteDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tituloNota", note.getName());
        bundle.putString("descripcionNota", note.getDescription());
        bundle.putString("idNota", note.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
