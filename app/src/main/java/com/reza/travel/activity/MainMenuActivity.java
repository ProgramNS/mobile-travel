package com.reza.travel.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.reza.travel.R;
import com.reza.travel.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity {

    private SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // Setup BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        session = new SessionManager(getApplicationContext());
        // Set up AppBarConfiguration to define top-level destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();

        // Setup NavController with the appropriate Fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main_menu);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        // Set up ActionBar with NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up BottomNavigationView with NavController
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnItemSelectedListener(item -> {
            navController.popBackStack(R.id.navigation_home, false);
            if (item.getItemId() == R.id.navigation_out) {
                // Logout logic
                showLogoutConfirmationDialog();
                return true;
            } else if (item.getItemId() == R.id.navigation_home) {
                navController.navigate(R.id.navigation_home);
                return true;
            } else if (item.getItemId() == R.id.navigation_about) {
                navController.navigate(R.id.navigation_about);
                return true;
            } else if (item.getItemId() == R.id.navigation_kontak) {
                navController.navigate(R.id.navigation_kontak);
                return true;
            } else {
                return false;
            }
        });


        // After login, make sure HomeFragment is the default fragment
        navController.navigate(R.id.navigation_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        session.checkLogin();  // Panggil pengecekan login setiap kali aktivitas dimulai
    }
    // Show the logout confirmation dialog
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
    public boolean onSupportNavigateUp() {
        NavController navController = ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main_menu)))
                .getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
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
