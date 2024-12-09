package com.reza.travel.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kotlin.contracts.Returns;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_travel";
    // Table User
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String COL_HP = "hp";

    //Table Admin
    public static final String TABLE_ADMIN = "tb_admin";
    public static final String COL_USERNAME_ADMIN = "username";
    public static final String COL_PASSWORD_ADMIN = "password";
    public static final String COL_NAME_ADMIN = "name";

    // Table Booking
    public static final String TABLE_BOOK = "tb_book";
    public static final String COL_ID_BOOK = "id_book";
    public static final String COL_CITY = "city";
    public static final String COL_ACARA = "acara";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_MOBIL = "mobil";
    public static final String COL_DESKRIPSI = "deskripsi";


    //Table Harga
    public static final String TABLE_HARGA = "tb_harga";
    public static final String COL_HARGA_TOTAL = "harga_total";
    public static final String COL_STATUS = "status";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        // Pembuatan Tabel User
        db.execSQL("create table " + TABLE_USER + " (" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD +
                " TEXT, " + COL_NAME + " TEXT,"+ COL_HP +" TEXT)");

        // Pembuatan Tabel Booking
        db.execSQL("create table " + TABLE_BOOK + " (" + COL_ID_BOOK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CITY + " TEXT," + COL_ACARA + " TEXT," + COL_TANGGAL + " TEXT, " + COL_MOBIL + " TEXT, " + COL_DESKRIPSI + " TEXT)");

        // Pembuatan Tabel Harga
        db.execSQL("CREATE TABLE " + TABLE_HARGA + " (" +
                COL_USERNAME + " TEXT, " +
                COL_ID_BOOK + " INTEGER, " +
                COL_HARGA_TOTAL + " TEXT, " +
                COL_CITY + " TEXT, " +
                COL_TANGGAL + " TEXT, " +
                COL_MOBIL + " TEXT, " +
                COL_STATUS + " TEXT, " +
                "FOREIGN KEY(" + COL_USERNAME + ") REFERENCES " + TABLE_USER + ", " +
                "FOREIGN KEY(" + COL_ID_BOOK + ") REFERENCES " + TABLE_BOOK + ")");

        db.execSQL("create table " + TABLE_ADMIN + "(" + COL_NAME_ADMIN + " TEXT, " + COL_USERNAME_ADMIN + " TEXT," +
               COL_PASSWORD_ADMIN + " TEXT)");

        // Pembuatan data untuk tabel user
        db.execSQL("insert into " + TABLE_USER + " values ('azhar@gmail.com','azhar','Azhar Rivaldi','0895111');");
        db.execSQL("insert into " + TABLE_ADMIN + " values ('Azhar Rivaldi', 'admin@admin.com','admin1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HARGA);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public boolean Register(String username, String password, String name, String hp) throws SQLException {

        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("INSERT INTO " + TABLE_USER + "(" + COL_USERNAME + ", " + COL_PASSWORD + ", " + COL_NAME + ", " + COL_HP + ") VALUES (?,?,?,?)", new String[]{username, password, name, hp});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public boolean Login(String username, String password) throws SQLException {
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public boolean LoginAdmin(String username, String password) throws SQLException{
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_ADMIN + " WHERE " + COL_USERNAME_ADMIN + "=? AND " + COL_PASSWORD_ADMIN + "=?", new String[] {username, password});
        if(mCursor != null){
            return mCursor.getCount() > 0;
        }
        return false;
    }
    public long insertBooking(String city, String acara, String tanggal, String mobil, String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CITY, city);
        contentValues.put(COL_ACARA, acara);
        contentValues.put(COL_TANGGAL, tanggal);
        contentValues.put(COL_MOBIL, mobil);
        contentValues.put(COL_DESKRIPSI, deskripsi);

        long id = db.insert(TABLE_BOOK, null, contentValues);
        db.close();
        return id;
    }

    public long insertHarga(String username, long idBook, String hargaTotal, String city, String tanggal, String mobil, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_ID_BOOK, idBook);
        contentValues.put(COL_HARGA_TOTAL, hargaTotal);
        contentValues.put(COL_CITY, city);
        contentValues.put(COL_TANGGAL, tanggal);
        contentValues.put(COL_MOBIL, mobil);
        contentValues.put(COL_STATUS, status);

        long id = db.insert(TABLE_HARGA, null, contentValues);
        db.close();
        return id;
    }


    // Update Booking Status
    public void updateBookingStatus(String bookingId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);

        // Mengupdate status berdasarkan ID booking
        db.update(TABLE_HARGA, contentValues, COL_ID_BOOK + " = ?", new String[]{bookingId});
        db.close();
    }


    // Delete Booking
    public void deleteBooking(String idBook) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HARGA + " WHERE " + COL_ID_BOOK + " = ?", new String[]{idBook});
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT h." + COL_ID_BOOK + ", h." + COL_USERNAME + ", b." + COL_ACARA + ", b." + COL_CITY + ", u." + COL_HP + ", b." + COL_TANGGAL + ", h." + COL_HARGA_TOTAL + ", h." + COL_STATUS + " " +
                "FROM " + TABLE_HARGA + " h " +
                "JOIN " + TABLE_BOOK + " b ON h." + COL_ID_BOOK + " = b." + COL_ID_BOOK + " " +
                "JOIN " + TABLE_USER + " u ON h." + COL_USERNAME + " = u." + COL_USERNAME;
        return db.rawQuery(query, null);
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER ;
        return  db.rawQuery(query,null);
    }
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, "username=?", new String[]{username}) > 0;
    }

    public boolean updateUserPassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASSWORD, newPassword);
        long result = db.update(TABLE_USER, contentValues, COL_USERNAME + "=?", new String[]{username});
        db.close();
        return result != -1;
    }
}