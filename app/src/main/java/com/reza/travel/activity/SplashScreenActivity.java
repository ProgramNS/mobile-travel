package com.reza.travel.activity;

import com.reza.travel.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);

        // Menggunakan Glide untuk membuat gambar rounded
        Glide.with(this)
                .load(R.drawable.logo) // Gambar logo
                .apply(RequestOptions.circleCropTransform()) // Membulatkan gambar
                .into(logo);

        // Timer untuk berpindah ke MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
