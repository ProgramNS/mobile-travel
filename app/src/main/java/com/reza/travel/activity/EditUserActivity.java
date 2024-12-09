package com.reza.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.reza.travel.R;
import com.reza.travel.database.DatabaseHelper;

public class EditUserActivity extends AppCompatActivity {
    private EditText etUsername, etName, etPhone, etPassword;
    private Button btnSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit User");
        }

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get data from Intent
        String username = getIntent().getStringExtra("username");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("hp");
        String password = getIntent().getStringExtra("password");

        // Set data to EditText
        etUsername.setText(username);
        etName.setText(name);
        etPhone.setText(phone);
        etPassword.setText(password);

        // Make non-password fields read-only
        etUsername.setEnabled(false);
        etName.setEnabled(false);
        etPhone.setEnabled(false);

        // Save button listener (Update only password if changed)
        btnSave.setOnClickListener(v -> {
            String newPassword = etPassword.getText().toString();

            // Validate new password
            if (newPassword.isEmpty()) {
                Toast.makeText(this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tampilkan dialog konfirmasi sebelum menyimpan perubahan
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin mengubah password?")
                    .setPositiveButton("Ubah", (dialog, which) -> {
                        // Update password in database
                        boolean isUpdated = databaseHelper.updateUserPassword(username, newPassword);
                        if (isUpdated) {
                            Toast.makeText(this, "Password berhasil diubah!", Toast.LENGTH_SHORT).show();
                            // Navigate back to User Management Activity
                            Intent intent = new Intent(EditUserActivity.this, UserManagementActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Gagal mengubah password!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Batal", (dialog, which) -> {
                        // Tutup dialog jika pengguna memilih "Batal"
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Mengatur tombol up untuk kembali
        return true;
    }
}
