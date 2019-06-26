package com.beet.model;

import java.util.ArrayList;

/**
 * AllCity
 */
public class AllCity {

    private String error_code;
    private String reason;
    private String resultcode;
    private ArrayList<City> result;

    public String getError_code() {
        return error_code;
    }

    public ArrayList<City> getResult() {
        return result;
    }

    public void setResult(ArrayList<City> result) {
        this.result = result;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

}