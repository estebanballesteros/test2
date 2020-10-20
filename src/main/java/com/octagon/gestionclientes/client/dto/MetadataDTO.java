package com.octagon.gestionclientes.client.dto;
import java.io.Serializable;

public class MetadataDTO implements Serializable {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
