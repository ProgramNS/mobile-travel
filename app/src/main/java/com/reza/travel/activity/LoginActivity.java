package com.reza.travel.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.reza.travel.R;
import com.reza.travel.adapter.AlertDialogManager;
import com.reza.travel.database.DatabaseHelper;
import com.reza.travel.session.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnRegister;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        txtUsername = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);

        btnLogin = findViewById(R.id.masuk);
        btnRegister = findViewById(R.id.ke_daftar);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                try {
                    // Check if username, password is filled
                    if (username.trim().length() > 0 && password.trim().length() > 0) {
                        dbHelper.open();

                        if (dbHelper.Login(username, password)) {
                            session.createLoginSession(username, "user");
                            Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                            Toast.makeText(LoginActivity.this , "Login Sukses", Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        } else if (dbHelper.LoginAdmin(username,password)) {
                            session.createLoginSession(username, "admin");
                            Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                            Toast.makeText(LoginActivity.this , "Login Sukses Sebagai ADMIN", Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        } else {
                            alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Email atau Password salah!", false);

                        }
                    } else {
                        alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Form tidak boleh kosong!", false);
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void setFullScreenMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Apakah Anda ingin keluar dari aplikasi?");
        builder.setCancelable(true);
        builder.setNegativeButton(getString(R.string.batal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getString(R.string.keluar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
