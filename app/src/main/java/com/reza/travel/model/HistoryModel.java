package com.reza.travel.model;

public class HistoryModel {

    private String mIdBook;
    private String mTanggal;
    private String mRiwayat;
    private String mTotal,mAcara;
    private int mImageResourceId;
    private static final int NO_IMAGE_PROVIDED = -1;

    public HistoryModel(String idBook, String tanggal, String acara, String riwayat, String total, int imageResourceId) {
        mIdBook = idBook;
        mTanggal = tanggal;
        mAcara = acara;
        mRiwayat = riwayat;
        mTotal = total;
        mImageResourceId = imageResourceId;
    }

    public String getIdBook() {
        return mIdBook;
    }

    public String getTanggal() {
        return mTanggal;
    }
    public  String getAcara(){return mAcara;}

    public String getRiwayat() {
        return mRiwayat;
    }

    public String getTotal() {
        return mTotal;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}