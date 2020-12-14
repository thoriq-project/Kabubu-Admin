package com.example.kabubufix.Model;

public class Slider {

    String header, keterangan;
    int image;

    public Slider(String header, String keterangan, int image) {
        this.header = header;
        this.keterangan = keterangan;
        this.image = image;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public int getImage() {
        return image;
    }
}
