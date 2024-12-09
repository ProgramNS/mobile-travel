package com.reza.travel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reza.travel.R;
import com.reza.travel.model.BookingModel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<BookingModel> bookingList;
    private OnBookingActionListener actionListener;

    public interface OnBookingActionListener {
        void onSelesaiClicked(BookingModel booking, int position);
        void onCancelClicked(BookingModel booking, int position);
    }

    public BookingAdapter(List<BookingModel> bookingList, OnBookingActionListener actionListener) {
        this.bookingList = bookingList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingModel booking = bookingList.get(position);

        holder.tvUsername.setText(String.format("Username: %s", booking.getUsername()));
        holder.tvAcara.setText(String.format("Acara : %s",booking.getAcara()));
        holder.tvTitle.setText(String.format("Tur : %s",booking.getCity()));
        holder.tvNoHp.setText(String.format("No Hp: %s", booking.getNoHp()));
        holder.tvTGL.setText(String.format("Tanggal: %s", booking.getTanggal()));
        holder.tvPrice.setText(String.format("Harga: Rp %s", booking.getHargaTotal()));
        holder.tvStatus.setText(String.format("Status: %s", booking.getStatus()));
        holder.btnSelesai.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onSelesaiClicked(booking, position);
        });

        holder.btnCancel.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onCancelClicked(booking, position);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvTitle, tvPrice, tvStatus, tvNoHp, tvTGL,tvAcara;
        Button btnSelesai, btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAcara = itemView.findViewById(R.id.tvAcara);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNoHp = itemView.findViewById(R.id.tvNoHp);
            tvTGL = itemView.findViewById(R.id.tvTGL);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            btnSelesai = itemView.findViewById(R.id.btnSelesai);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}

