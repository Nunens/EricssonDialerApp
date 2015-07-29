package com.boha.ericssen.library.util;


import java.util.ArrayList;

/**
 * Created by Sipho on 2014/11/11.
 */
public class BohaContact {
    private String id;
    private String displayName;
    private ArrayList<Phone> phone;
    private ArrayList<Email> email;
    private ArrayList<String> notes;
    private ArrayList<Address> addresses = new ArrayList<Address>();
    private ArrayList<IM> imAddresses;
    private Organization organization;

    public BohaContact() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ArrayList<Phone> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<Phone> phone) {
        this.phone = phone;
    }

    public ArrayList<Email> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<Email> email) {
        this.email = email;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public ArrayList<IM> getImAddresses() {
        return imAddresses;
    }

    public void setImAddresses(ArrayList<IM> imAddresses) {
        this.imAddresses = imAddresses;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
