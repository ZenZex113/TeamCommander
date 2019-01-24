
package com.example.zenitka.teamcommander.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeID {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

}