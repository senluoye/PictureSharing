package com.wkh.picturesharingapplication.bean.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterModel implements Serializable {

    @JsonProperty("data")
    private DataDTO data;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("code")
    private String code;

    private class DataDTO{

    }

}
