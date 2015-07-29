package com.boha.ericssen.library.util;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by aubreyM on 2014/11/08.
 */
public class BohaUtil {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    public static List<BohaContact> getContacts(Context ctx) {
        ContentResolver cr = ctx.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            Log.i("COUNT", cur.getCount() + "");
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //Log.v("BohaUtil", "ALL CONTACTS");
                //Log.e("BohaUtil", "ID: " + id + ", Name: " + name);

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Log.v("BohaUtil", "CONTACT WITH NUMBER");
                    Log.e("BohaUtil", "ID: " + id + ", Name: " + name);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.v("BohaUtil", "PHONE");
                        Log.e("BohaUtil", "Phone: " + phone);
                    }
                    pCur.close();


                    // get email and type

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (emailCur.getCount() > 0) {
                        while (emailCur.moveToNext()) {
                            // This would allow you get several email addresses
                            // if the email addresses were stored in an array
                            String email = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            String emailType = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                            Log.v("BohaUtil", "EMAILS");
                            Log.e("BohaUtil", "EMAIL: " + email);
                        }
                    }
                    emailCur.close();

                    // Get note.......
                    /*
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                    if (noteCur.moveToFirst()) {
                        String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                        System.out.println("Note " + note);
                    }
                    noteCur.close();*/

                    //Get Postal Address....

                    /*String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, null, null, null);
                    while (addrCur.moveToNext()) {
                        String poBox = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        String street = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String city = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....

                    }
                    addrCur.close();*/

                    // Get Instant <span class="IL_AD" id="IL_AD12">Messenger</span>.........

                    /*
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();*/

                    // Get Organizations.........
                    /*
                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                    }
                    orgCur.close();*/
                }
            }
        }
        return null;
    }

    public static List<BohaCallLog> getCallDetails(Context ctx) {
        //getContacts(ctx);
        ContentValues values = new ContentValues();
        List<BohaCallLog> list = new ArrayList<>();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        ContentResolver cr = ctx.getContentResolver();
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor managedCursor = cr.query(callUri, null, null, null, strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int cachedName = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int newCall = managedCursor.getColumnIndex(CallLog.Calls.NEW);
        int nType = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NUMBER_TYPE);
        int nLabel = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL);
        while (managedCursor.moveToNext()) {
            BohaCallLog log = new BohaCallLog();
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            //Log.e("DATE", callDate);
            Date callDayTime = null;
            //Log.d("111", callDate);
            //Log.d("222", Long.parseLong(callDate) + "");
            callDayTime = new Date(Long.parseLong(callDate));
            //Log.d("333", callDayTime.toString());
            String callDuration = managedCursor.getString(duration);
            String name = managedCursor.getString(cachedName);
            String stringNew = managedCursor.getString(newCall);
            String numberType = managedCursor.getString(nType);
            String numberLabel = managedCursor.getString(nLabel);

            //Clearing the call log...
            if (phNumber.length() > 3) {
                //Log.d("BohaUtil", "SUBSTR: " + log.getNumber().substring(0, 3));
                if (phNumber.substring(0, 3).equals("127")) {
                    //Log.e("ERROR", "PAY-4-ME NUMBER: " + phNumber);
                    //Log.d("BohaUtil", "...***Deleting***...");
                    ctx.getContentResolver().delete(callUri, CallLog.Calls.NUMBER + "=?", new String[]{phNumber});
                    //Log.d("BohaUtil", "...###Deleted###...");
                    //Log.i("BohaUtil", "...###Inserting###...");
                    values.put(CallLog.Calls.NUMBER, phNumber.substring(3, phNumber.length()));
                    Log.i("SUBSTRING","FIRST 3: "+phNumber.substring(3, phNumber.length()));
                    if (callDate != null) {
                        values.put(CallLog.Calls.DATE, callDayTime.getTime());
                        //values.put(CallLog.Calls.DATE, callDate.toString());
                        //Log.v("BohaUtil", "call date is not != null");
                    } else {
                        Log.v("BohaUtil", "call date is null");
                        //values.put(CallLog.Calls.DATE, new Date().toString());
                    }
                    values.put(CallLog.Calls.DURATION, 0);
                    values.put(CallLog.Calls.TYPE, callType);
                    values.put(CallLog.Calls.NEW, 1);
                    values.put(CallLog.Calls.CACHED_NAME, name);
                    values.put(CallLog.Calls.CACHED_NUMBER_TYPE, numberType);
                    values.put(CallLog.Calls.CACHED_NUMBER_LABEL, numberLabel);
                    ctx.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
                    //Log.i("BohaUtil", "...###Inserted###... " + values.toString());
                } else {
                    //Log.e("ERROR", "NORMAL NUMBER: " + phNumber);
                }
            } else {
                //Log.e("ERROR", "VERY SHORT NUMBER: " + phNumber);
            }
            log.setDate(callDayTime);
            log.setDuration(Long.parseLong(callDuration));
            if(name == null) {
                log.setName("Unknown");
            }else {
                log.setName(name);
            }
            log.setNumber(phNumber);
            log.setType(Integer.parseInt(callType));
            log.setNumberLabel(numberLabel);
            log.setNumberType(numberType);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    log.setStringType(dir);
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    log.setStringType(dir);
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    log.setStringType(dir);
                    break;
            }
            list.add(log);
            String no = "";
            //Log.e("BohaUtil", no + ", " + log.getName() + ", " + log.getDuration() + ", " + log.getDate());
        }
        managedCursor.close();
        Log.e("BohaUtil", "Found calls in log: " + list.size());
        //myContacts(ctx);
        return list;
    }
    public static void myContacts(Context ctx) {
        Log.e("myContacts", ".......Just got in!!...........");
        ContentResolver cr = ctx.getContentResolver();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        Log.e("myContacts Count", "" + phones.getCount());
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.e("myContacts", ".................." + name + " " + phoneNumber);
        }
        phones.close();
    }

    /*public static List<Phone> getContacts(Context ctx) {
        Log.e("myContacts", ".......Just got in!!...........");
        List<Phone> belas = new ArrayList<>();
        ContentResolver cr = ctx.getContentResolver();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        Log.e("myContacts Count", "" + phones.getCount());
        while (phones.moveToNext()) {
            Phone p = new Phone();
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.e("myContacts", ".................." + name + " " + phoneNumber);
            p.setName(name);
            p.setNumber(phoneNumber);
            belas.add(p);
        }
        phones.close();
        return belas;
    }*/

}
