package com.example.kabubufix.Model;

public class Profile {

    private String pid, date, time, image, nama, tempat, tanggal, info;

    public Profile(){

    }

    public Profile(String pid, String date, String time, String image, String nama, String tempat, String tanggal, String info) {
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.image = image;
        this.nama = nama;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.info = info;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
