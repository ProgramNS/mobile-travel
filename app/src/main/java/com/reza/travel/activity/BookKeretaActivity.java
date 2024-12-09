package com.reza.travel.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.reza.travel.R;
import com.reza.travel.database.DatabaseHelper;
import com.reza.travel.session.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class BookKeretaActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Spinner spinCity;
    SessionManager session;
    String email;
    int id_book, quantity;
    public String sCity, sTanggal, sMobil, sDeskripsi, sAcara;

    private final String status = "Pending";
    int hargaTotal;
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    private EditText etKatalog;
    private TextView textQuantity;
    private Button buttonDecrease, buttonIncrease;
    private int hargaKatalog = 0;
    Calendar newCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_kereta);

        // Menghubungkan komponen dengan layout
        EditText edtKatalogTitle = findViewById(R.id.edtKatalogTitle);
        EditText edtKatalogPrice = findViewById(R.id.edtKatalogPrice);
        EditText edtKatalogDescription = findViewById(R.id.edtKatalogDescription);
        textQuantity = findViewById(R.id.text_quantity);
        buttonDecrease = findViewById(R.id.button_decrease);
        buttonIncrease = findViewById(R.id.button_increase);

        // Interaksi button + dan -
        buttonDecrease.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                updateQuantityAndPrice(edtKatalogPrice);
            }
        });

        buttonIncrease.setOnClickListener(v -> {
            if (quantity < 10) {
                quantity++;
                updateQuantityAndPrice(edtKatalogPrice);
            } else {
                Toast.makeText(BookKeretaActivity.this, "Jumlah maksimal adalah 10", Toast.LENGTH_SHORT).show();
            }
        });

        // Mendapatkan data yang dikirim dari KatalogActivity
        Intent intent = getIntent();
        String title = intent.getStringExtra("katalog_title");
        String price = intent.getStringExtra("katalog_price");
        String description = intent.getStringExtra("katalog_description");

        // Mengatur teks ke EditText
        if (edtKatalogTitle != null && title != null) {
            edtKatalogTitle.setText(title);
            sMobil = edtKatalogTitle.getText().toString().trim();
        }

        if (edtKatalogDescription != null && description != null) {
            edtKatalogDescription.setText(description);
            sDeskripsi = edtKatalogDescription.getText().toString().trim();
        }

        if (price != null && !price.isEmpty()) {
            price = price.replace("Rp", "").replace(".", "").trim();
            hargaKatalog = Integer.parseInt(price);
        }

        dbHelper = new DatabaseHelper(BookKeretaActivity.this);
        db = dbHelper.getReadableDatabase();

        final String[] asal = {"Yogyakarta", "Luar Kota"};
        spinCity = findViewById(R.id.asal);

        ArrayAdapter<CharSequence> adapterAsal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, asal);
        adapterAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(adapterAsal);

        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button btnBook = findViewById(R.id.book);

        etTanggal = findViewById(R.id.tanggal_berangkat);
        EditText edtAcara = findViewById(R.id.acara);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        setDateTimeField();

        btnBook.setOnClickListener(v -> {
            String totalHarga = edtKatalogPrice.getText().toString();
            sAcara = edtAcara.getText().toString();
            if (sCity != null && sAcara != null && sTanggal != null && sMobil != null && sDeskripsi != null) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Ingin booking nila transport sekarang?")
                        .setPositiveButton("Ya", (dialog1, which) -> {
                            try {
                                long idBook = dbHelper.insertBooking(sCity, sAcara, sTanggal, sMobil, sDeskripsi);
                                dbHelper.insertHarga(email, idBook, totalHarga, sCity, sTanggal, sMobil, status);
                                Log.d("Booking Data", "Booking berhasil: id_book = " + idBook + ", City = " + sCity + ", Tanggal = " + sTanggal + ", Mobil = " + sMobil);

                                Toast.makeText(this, "Booking berhasil silahkan hub admin, no admin dapat dilihat di menu kontak", Toast.LENGTH_LONG).show();
                                Intent mainMenuIntent = new Intent(BookKeretaActivity.this, MainMenuActivity.class);
                                startActivity(mainMenuIntent);
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            } else {
                Toast.makeText(this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_LONG).show();
            }
        });

        etKatalog = findViewById(R.id.katalog_edit_text);
        etKatalog.setOnClickListener(v -> {
            Intent katalogIntent = new Intent(BookKeretaActivity.this, KatalogActivity.class);
            startActivityForResult(katalogIntent, 1);
        });

        setupToolbar();
    }

    private void updateQuantityAndPrice(EditText edtKatalogPrice) {
        textQuantity.setText(String.valueOf(quantity));
        hargaTotal = quantity * hargaKatalog;
        edtKatalogPrice.setText(formatRupiah(hargaTotal));
    }

    private String formatRupiah(int number) {
        return String.format("Rp %,d", number).replace(",", ".");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String price = data.getStringExtra("price");
            if (price != null && !price.isEmpty()) {
                price = price.replace("Rp", "").replace(".", "").trim();
                hargaKatalog = Integer.parseInt(price);
            }
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbKrl);
        toolbar.setTitle("Form Booking");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setDateTimeField() {
        etTanggal.setOnClickListener(v -> dpTanggal.show());

        dpTanggal = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                    "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
            sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
            etTanggal.setText(sTanggal);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(BookKeretaActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BookKeretaActivity.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

}
