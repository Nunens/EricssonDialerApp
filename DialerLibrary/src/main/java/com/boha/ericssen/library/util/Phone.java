package com.boha.ericssen.library.util;

/**
 * Created by Sipho on 2014/11/11.
 */
public class Phone {
    private String number;
    private String name;

    public Phone() {

    }

    public Phone(String number, String name) {
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
