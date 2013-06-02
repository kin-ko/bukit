package com.bukit.android.responsemodel;

import java.io.Serializable;

public class ExternalCoupon implements Serializable{

    private String link;
    private String image;

    public ExternalCoupon(String link, String image) {
        this.link = link;
        this.image = image;
    }

    public ExternalCoupon() {
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

}
