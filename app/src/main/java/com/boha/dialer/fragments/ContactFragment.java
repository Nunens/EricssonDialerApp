package com.boha.dialer.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.boha.dialer.R;
import com.boha.ericssen.library.util.adapter.Contact;
import com.boha.ericssen.library.util.adapter.ContactsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sipho on 2014/11/14.
 */
public class ContactFragment extends Fragment implements PageFragment {
    List<String> name1 = new ArrayList<String>();
    List<String> phno1 = new ArrayList<String>();
    List<Contact> contactList;
    TextView txt;
    ListView lv;
    EditText etxt;
    ContactsAdapter adapter;
    ArrayAdapter<String> ca;
    Contact myname;
    String name, phoneNumber;
    View view;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFields();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFields() {
        etxt = (EditText) getActivity().findViewById(R.id.etxt);
        lv = (ListView) getActivity().findViewById(R.id.list);
        etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i2, int i3) {
                adapter.filter(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        getContacts(getActivity().getContentResolver());
    }

    public void getContacts(ContentResolver cr) {
        Log.e("myContacts", ".......Just got in!!...........");
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        Log.e("myContacts Count", "" + phones.getCount());

        contactList = new ArrayList<Contact>();
        Contact con = new Contact();
        while (phones.moveToNext()) {
            con = new Contact();
            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //Log.e("myContacts", ".................." + name + " " + phoneNumber);
            con.setName(name);
            con.setPhone(phoneNumber);
            //name1.add(name);
            contactList.add(con);
        }
        // ca = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.txtName,name1);
        adapter = new ContactsAdapter(getActivity(), contactList);
        lv.setAdapter(adapter);
        phones.close();
        //lv.setAdapter(ca);
    }
}
