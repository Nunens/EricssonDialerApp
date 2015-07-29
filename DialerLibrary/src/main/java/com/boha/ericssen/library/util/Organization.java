package com.boha.ericssen.library.util;

/**
 * Created by Sipho on 2014/11/11.
 */
public class Organization {
    String title, organization;

    public Organization() {

    }

    public Organization(String title, String organization) {
        this.organization = organization;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
