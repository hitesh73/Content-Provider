package com.example.contactactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.contactactivity.contentprovider.ContactAdapter;
import com.example.contactactivity.contentprovider.ContactsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContact;
    private ArrayList<ContactsModel> contactsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        rvContact = findViewById(R.id.rvContacts);
        rvContact.setLayoutManager(new LinearLayoutManager(this));

        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            getContactList();
        }
    }

    @SuppressLint("Range")
    private void getContactList() {
        contactsModels = new ArrayList<>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactCursor = getContentResolver().query(uri, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        if (contactCursor.getCount() > 0) {
            while (contactCursor.moveToNext()) {
                String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //String contactImage = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

                Uri dataUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);

                if (dataCursor.moveToNext()) {
                    String contactNumber = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Uri myImage = getPhotoUri(Long.parseLong(contactId));
                    contactsModels.add(new ContactsModel(contactName, contactNumber, contactId, myImage));
                    dataCursor.close();
                }
            }
            contactCursor.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this, contactsModels);
        rvContact.setAdapter(contactAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContactList();
        } else {
            checkPermission();
        }
    }

    public Uri getPhotoUri(long contactId) {
        try {
            Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
                    ContactsContract.Data.CONTACT_ID + "=" + contactId + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null, null);
            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }
}