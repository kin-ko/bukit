package com.bukit.android.responsemodel;

import org.codehaus.jackson.annotate.JsonIgnore;
import java.io.Serializable;

public class ListingBusinessLogo implements Serializable{

    private String url;
    private String altText;

    public ListingBusinessLogo() {
    }

    public ListingBusinessLogo(Logo logo) {
        this.url = logo.getPath();
        this.altText = logo.getAltText();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return equals(new ListingBusinessLogo());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

}
