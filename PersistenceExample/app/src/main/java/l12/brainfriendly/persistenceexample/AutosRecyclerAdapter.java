package l12.brainfriendly.persistenceexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author pjohnson on 19/03/18.
 */

public class AutosRecyclerAdapter extends RecyclerView.Adapter<AutosRecyclerAdapter.ViewHolder> {

    private final List<Auto> autos;

    public AutosRecyclerAdapter(List<Auto> autoList) {
        this.autos = autoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_auto, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Auto auto = autos.get(position);
        holder.marcaAutoTextView.setText(auto.getMarca());
        holder.modeloAutoTextView.setText(auto.getModelo());

        Glide.with(holder.autoImageView.getContext())
                .load(auto.getImagen())
                .into(holder.autoImageView);
    }

    @Override
    public int getItemCount() {
        return autos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView marcaAutoTextView;
        TextView modeloAutoTextView;
        ImageView autoImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            marcaAutoTextView = itemView.findViewById(R.id.marcaAutoTextView);
            modeloAutoTextView = itemView.findViewById(R.id.modeloAutoTextView);
            autoImageView = itemView.findViewById(R.id.autoImageView);
        }
    }
}
