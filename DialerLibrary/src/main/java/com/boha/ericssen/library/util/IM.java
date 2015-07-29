package com.boha.ericssen.library.util;

/**
 * Created by Sipho on 2014/11/11.
 */
public class IM {
    String name, type;

    public IM() {

    }

    public IM(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
