package com.example.kabubufix.Model;

public class Kutipan {

    private String name, image, kid;

    public Kutipan () {}

    public Kutipan(String name, String image, String kid) {
        this.name = name;
        this.image = image;
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

}
