package com.boha.ericssen.library.util;

/**
 * Created by Sipho on 2014/11/11.
 */
public class Email {
    private String address;
    private String type;

    public Email() {

    }

    public Email(String address, String type) {
        this.address = address;
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }
}
