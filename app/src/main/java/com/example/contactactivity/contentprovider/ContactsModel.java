package com.example.contactactivity.contentprovider;

import android.net.Uri;

public class ContactsModel {
    String contactName;
    String contactNumber;
    Uri contactImage;
    String contactId;

    public ContactsModel(String contactName, String contactNumber, String contactId, Uri contactImage) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactId = contactId;
        this.contactImage = contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Uri getContactImage() {
        return contactImage;
    }

    public void setContactImage(Uri contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

}
