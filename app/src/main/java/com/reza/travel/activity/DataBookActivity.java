package com.reza.travel.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reza.travel.R;
import com.reza.travel.adapter.BookingAdapter;
import com.reza.travel.database.DatabaseHelper;
import com.reza.travel.model.BookingModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBookActivity extends AppCompatActivity {
    private BookingAdapter adapter;
    private final List<BookingModel> bookingList = new ArrayList<>();
    private final List<BookingModel> filteredList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_book); // Mengatur layout XML

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dbHelper = new DatabaseHelper(this);

        adapter = new BookingAdapter(Collections.unmodifiableList(filteredList), new BookingAdapter.OnBookingActionListener() {
            @Override
            public void onSelesaiClicked(BookingModel booking, int position) {
                new AlertDialog.Builder(DataBookActivity.this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah Anda ingin menyelesaikan order booking ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            dbHelper.updateBookingStatus(booking.getId(), "Selesai");
                            Toast.makeText(DataBookActivity.this, "Status booking diubah menjadi Selesai!", Toast.LENGTH_SHORT).show();
                            loadBookingData();
                        })
                        .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss())
                        .show();
            }



            @Override
            public void onCancelClicked(BookingModel booking, int position) {
                new AlertDialog.Builder(DataBookActivity.this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah anda ingin membatalkan pesanan ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            dbHelper.deleteBooking(booking.getId());
                            filteredList.remove(position);
                            adapter.notifyItemRemoved(position);
                        })
                        .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);

        loadBookingData();
    }

    @SuppressLint({"Range", "NotifyDataSetChanged"})
    private void loadBookingData() {
        Cursor cursor = dbHelper.getAllBookings();
        bookingList.clear();

        if (cursor.moveToFirst()) {
            do {
                bookingList.add(new BookingModel(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID_BOOK)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ACARA)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CITY)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HP)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TANGGAL)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HARGA_TOTAL)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STATUS))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        filteredList.clear();
        filteredList.addAll(bookingList);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Mengatur tombol up untuk kembali
        return true;
    }

    @Override
    public void onBackPressed() {
        // Kembali ke AdminActivity saat tombol back ditekan
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }
}
