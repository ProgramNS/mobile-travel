package com.reza.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reza.travel.R;
import com.reza.travel.adapter.KatalogAdapter;
import com.reza.travel.model.KatalogModel;

import java.util.ArrayList;
import java.util.Objects;

public class KatalogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KatalogAdapter katalogAdapter;
    private ArrayList<KatalogModel> katalogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);

        setupToolbar();
        recyclerView = findViewById(R.id.recycler_view_katalog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mengisi data katalog kendaraan
        katalogList = new ArrayList<>();
        katalogList.add(new KatalogModel("Hiace Commuter", "Rp 1.200.000", "standar 15 seat + driver", R.drawable.hiace_commuter));
        katalogList.add(new KatalogModel("Hiace Premio", "Rp 1.400.000", "standar 15 seat + driver", R.drawable.hiego_premio));
        katalogList.add(new KatalogModel("Honda Brio", "Rp 500.000", "standar 5 seat + driver", R.drawable.honda_brio));
        katalogList.add(new KatalogModel("Toyota Innova", "Rp 800.000", "standar 7 seat", R.drawable.toyota));

        // Menyiapkan adapter dan menghubungkannya ke RecyclerView
        katalogAdapter = new KatalogAdapter(this, katalogList);
        recyclerView.setAdapter(katalogAdapter);

        // Menambahkan listener pada item RecyclerView
        katalogAdapter.setOnItemClickListener(new KatalogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Mengambil title dan price yang dipilih
                KatalogModel selectedKatalog = katalogList.get(position);
                String title = selectedKatalog.getTitle();
                String price = selectedKatalog.getPrice();
                String description = selectedKatalog.getDescription();

                // Mengirim data ke BookKeretaActivity
                Intent intent = new Intent(KatalogActivity.this, BookKeretaActivity.class);
                intent.putExtra("katalog_title", title);
                intent.putExtra("katalog_price", price);
                intent.putExtra("katalog_description",description);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbKatalog);
        toolbar.setTitle("Katalog");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
