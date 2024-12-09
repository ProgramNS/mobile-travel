package com.reza.travel.model;

public class BookingModel {
    private final String id;
    private final String username;
    private final String city;
    private final String tanggal;
    private final String no;
    private final String hargaTotal;
    private final String status;
    private final String acara;

    // Konstruktor
    public BookingModel(String id, String username, String acara, String city, String no,String tanggal, String hargaTotal, String status) {
        this.id = id;
        this.username = username;
        this.acara = acara;
        this.city = city;
        this.tanggal = tanggal;
        this.no = no;
        this.hargaTotal = hargaTotal;
        this.status = status;

    }

    // Getter methods
    public String getId() { return id; }
    public String getUsername() { return username; }

    public String getAcara(){return acara;}
    public String getCity() { return city; }

    public String getTanggal() { return tanggal; }
    public String getNoHp() { return no; }
    public String getHargaTotal() { return hargaTotal; }
    public String getStatus(){return status;}

}
