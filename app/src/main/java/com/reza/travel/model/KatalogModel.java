package com.reza.travel.model;

public class KatalogModel {

    private String title;        // Judul atau nama kendaraan
    private String price;        // Harga kendaraan
    private String description;  // Deskripsi kendaraan
    private int imageResource;   // ID resource gambar kendaraan

    // Constructor yang mencakup deskripsi
    public KatalogModel(String title, String price, String description, int imageResource) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageResource = imageResource;
    }

    // Getter untuk title
    public String getTitle() {
        return title;
    }

    // Getter untuk price
    public String getPrice() {
        return price;
    }

    // Getter untuk description
    public String getDescription() {
        return description;
    }

    // Getter untuk imageResource
    public int getImageResource() {
        return imageResource;
    }
}
