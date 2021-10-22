package com.wkh.picturesharingapplication.bean.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class getAllPostModel implements Serializable {

    @JsonProperty("code")
    private int code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataDTO implements Serializable {
        @JsonProperty("id")
        private String id;
        @JsonProperty("content")
        private String content;
        @JsonProperty("date")
        private long date;
        @JsonProperty("userId")
        private String userId;
        @JsonProperty("username")
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @JsonProperty("pictures")
        private List<String> pictures;
        @JsonProperty("isStar")
        private int isStar;

        private int collectionCount;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id='" + id + '\'' +
                    ", content='" + content + '\'' +
                    ", date=" + date +
                    ", userId='" + userId + '\'' +
                    ", username='" + username + '\'' +
                    ", pictures=" + pictures +
                    ", isStar=" + isStar +
                    ", collectionCount=" + collectionCount +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<String> getPictures() {
            return pictures;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }

        public int getIsStar() {
            return isStar;
        }

        public void setIsStar(int isStar) {
            this.isStar = isStar;
        }

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

    }
}
