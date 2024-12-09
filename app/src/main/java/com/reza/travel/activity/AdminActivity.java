package com.reza.travel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.reza.travel.R;
import com.reza.travel.session.SessionManager;
import com.google.android.material.card.MaterialCardView;

public class AdminActivity extends AppCompatActivity {

    private SessionManager session;
    private MaterialCardView cardDataBooking,cardDataUser;
    private LinearLayout menuHome, menuSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        cardDataBooking = findViewById(R.id.card_data_booking);
        cardDataUser = findViewById(R.id.card_data_user);

        menuHome = findViewById(R.id.menu_home);
        menuSignOut = findViewById(R.id.menu_signout);


        cardDataBooking.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, DataBookActivity.class);
            startActivity(i);
            finish();
        });
        cardDataUser.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, UserManagementActivity.class);
            startActivity(i);
            finish();
        });

        // Set OnClickListeners for bottom menu items
        menuHome.setOnClickListener(v -> navigateHome());
        menuSignOut.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    // Navigate to Home logic
    private void navigateHome() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // Show logout confirmation dialog
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setCancelable(false) // Make it not cancellable by tapping outside
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Perform logout logic
                        session.logoutUser();
                    }
                })
                .setNegativeButton("Tidak", null) // Do nothing when "No" is clicked
                .create()
                .show();
    }
    @Override
    public void onBackPressed() {
        // Tampilkan dialog konfirmasi logout
        new AlertDialog.Builder(this)
                .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
                .setCancelable(false) // Dialog tidak bisa ditutup dengan tombol di luar
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Logout user dan arahkan ke login
                        session.logoutUser(); // Fungsi ini sudah ada di SessionManager Anda
                        finish(); // Tutup MainMenuActivity
                    }
                })
                .setNegativeButton("Tidak", null) // Tutup dialog tanpa aksi
                .create()
                .show();
    }
}
