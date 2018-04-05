package l12.brainfriendly.networkconnection.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import l12.brainfriendly.networkconnection.R;
import l12.brainfriendly.networkconnection.model.Nota;

/**
 * @author pjohnson on 26/03/18.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Nota> notas;
    private NotesInterface listener;

    public NotesAdapter(List<Nota> notas) {
        this.notas = notas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notas, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Nota nota = notas.get(position);
        holder.tituloNota.setText(nota.getName());
        holder.descripcionNota.setText(nota.getDescription());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClicked(nota);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
        notifyDataSetChanged();
    }

    public void setListener(NotesInterface listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tituloNota;
        TextView descripcionNota;
        View parent;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            tituloNota = itemView.findViewById(R.id.tituloNota);
            descripcionNota = itemView.findViewById(R.id.descripcionNota);
        }
    }

    public interface NotesInterface {
        void onNoteClicked(Nota note);
    }
}
