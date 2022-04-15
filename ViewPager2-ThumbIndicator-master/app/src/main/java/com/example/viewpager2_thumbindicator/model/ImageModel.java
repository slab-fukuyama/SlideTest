package com.example.viewpager2_thumbindicator.model;

public class ImageModel {

    public ImageModel(String url){
        setUrl(url);
    }

    private String _url;

    public void setUrl(String url) {
        this._url = url;
    }

    public String getUrl() {
        return _url;
    }
}
