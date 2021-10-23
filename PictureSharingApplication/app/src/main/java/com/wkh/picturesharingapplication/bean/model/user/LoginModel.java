package com.wkh.picturesharingapplication.bean.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginModel implements Serializable {

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("code")
    private int code;

    @JsonProperty("error")
    private String error;

    @JsonProperty("data")
    private DataDTO data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DataDTO implements Serializable{

        @JsonProperty("token")
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
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
