package com.example.kabubufix.Model;

public class Puisi {

    private String gambar, judul, nama, puid, cari;

    public Puisi () {}

    public Puisi(String gambar, String judul, String nama, String puid, String cari) {
        this.gambar = gambar;
        this.judul = judul;
        this.nama = nama;
        this.puid = puid;
        this.cari = cari;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getCari() {
        return cari;
    }

    public void setCari(String cari) {
        this.cari = cari;
    }
}
