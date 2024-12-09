package com.reza.travel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reza.travel.R;
import com.reza.travel.adapter.UserDataAdapter;
import com.reza.travel.database.DatabaseHelper;
import com.reza.travel.model.BookingModel;
import com.reza.travel.model.UserDataModel;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDataAdapter userDataAdapter;
    private DatabaseHelper databaseHelper;
    private List<UserDataModel> userDataList;
    private final List<UserDataModel> filteredList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        recyclerView = findViewById(R.id.listItemsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        databaseHelper = new DatabaseHelper(this);
        userDataList = new ArrayList<>();


        userDataAdapter = new UserDataAdapter(userDataList, new UserDataAdapter.onUserDataActionListener() {
            @Override
            public void onDeleteClicked(UserDataModel userData, int position) {
                deleteUser(userData, position);
            }

            @Override
            public void onEditClicked(UserDataModel userData, int position) {
                editUser(userData);
            }
        });
        loadUserData();
        recyclerView.setAdapter(userDataAdapter);
        userDataAdapter.notifyDataSetChanged();
    }

    @SuppressLint({"Range", "NotifyDataSetChanged"})
    private void loadUserData() {
        Cursor cursor = databaseHelper.getAllUsers();
        userDataList.clear();
        if (cursor != null) {
            Log.d("Database Rows", "Jumlah Baris: " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    Log.d("Database Data", "Username: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME)));
                    Log.d("Database Data", "Password: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PASSWORD)));
                    Log.d("Database Data", "Name: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                    Log.d("Database Data", "No Hp: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HP)));
                } while (cursor.moveToNext());
            } else {
                Log.d("Database Data", "Cursor kosong");
            }
        } else {
            Log.d("Database Query", "Cursor null");
        }
        if(cursor.moveToFirst()){
            do {
                userDataList.add(new UserDataModel(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HP)))

                );
                } while (cursor.moveToNext());
            }
        cursor.close();
        userDataAdapter.notifyDataSetChanged();
    }

    private void deleteUser(UserDataModel userData, int position) {
        // Membuat dialog konfirmasi
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus pengguna " + userData.getUsername() + "?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    // Jika pengguna memilih "Hapus", lanjutkan untuk menghapus pengguna
                    boolean isDeleted = databaseHelper.deleteUser(userData.getUsername());
                    if (isDeleted) {
                        userDataList.remove(position);
                        userDataAdapter.notifyItemRemoved(position);
                        Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", (dialog, which) -> {
                    // Jika pengguna memilih "Batal", hanya tutup dialog
                    dialog.dismiss();
                })
                .create()
                .show();
    }


    private void editUser(UserDataModel userData) {
        Intent intent = new Intent(UserManagementActivity.this, EditUserActivity.class);
        intent.putExtra("username", userData.getUsername());
        intent.putExtra("name", userData.getNameUser());
        intent.putExtra("hp", userData.getHp());
        intent.putExtra("password", userData.getPassword());
        startActivity(intent);
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
