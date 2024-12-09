package com.reza.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.reza.travel.R;
import com.reza.travel.model.KatalogModel;

import java.util.ArrayList;

public class KatalogAdapter extends RecyclerView.Adapter<KatalogAdapter.KatalogViewHolder> {

    private Context context;
    private ArrayList<KatalogModel> katalogList;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public KatalogAdapter(Context context, ArrayList<KatalogModel> katalogList) {
        this.context = context;
        this.katalogList = katalogList;
    }

    // ViewHolder untuk meng-handle item layout
    @Override
    public KatalogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_katalog, parent, false);
        return new KatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KatalogViewHolder holder, int position) {
        KatalogModel katalog = katalogList.get(position);
        holder.title.setText(katalog.getTitle());
        holder.price.setText(katalog.getPrice());
        holder.description.setText(katalog.getDescription());
        holder.imageView.setImageResource(katalog.getImageResource());
    }

    @Override
    public int getItemCount() {
        return katalogList.size();
    }

    // Menambahkan method untuk set listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Interface untuk item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // ViewHolder class untuk item
    public class KatalogViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, description;
        ImageView imageView;

        public KatalogViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            price = itemView.findViewById(R.id.tvPrice);
            description = itemView.findViewById(R.id.tvDescription);
            imageView = itemView.findViewById(R.id.ivImage);

            // Set click listener pada item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
