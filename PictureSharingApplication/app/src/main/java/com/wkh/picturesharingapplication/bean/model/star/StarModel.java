package com.wkh.picturesharingapplication.bean.model.star;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StarModel implements Serializable {

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("code")
    private int code;

    @JsonProperty("error")
    private String error;

    @JsonProperty("data")
    private DataDTO data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class DataDTO implements Serializable{

        @JsonProperty("id")
        private String id;

        @JsonProperty("postId")
        private String postId;

        @JsonProperty("userId")
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }
}
