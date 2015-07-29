package com.boha.ericssen.library.util;

import java.util.ArrayList;

/**
 * Created by Sipho on 2014/11/11.
 */
public class BohaContactList {
    private ArrayList<BohaContact> contacts = new ArrayList<BohaContact>();

    public BohaContactList() {

    }

    public ArrayList<BohaContact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<BohaContact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(BohaContact contact) {
        this.contacts.add(contact);
    }
}
