package com.wkh.picturesharingapplication.bean.entity;

public class Picture {

    private String id;
    private String base64;

    @Override
    public String toString() {
        return "Picture{" +
                "id='" + id + '\'' +
                ", base64='" + base64 + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
