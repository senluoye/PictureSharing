package com.wkh.picturesharingapplication.bean.entity;

public class Star {

    private String id;
    private String postId;
    private String userId;

    @Override
    public String toString() {
        return "Star{" +
                "id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
