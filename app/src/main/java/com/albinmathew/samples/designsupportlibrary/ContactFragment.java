package com.albinmathew.samples.designsupportlibrary;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albinmathew.samples.designsupportlibrary.adapter.SimpleContactsAdapter;
import com.albinmathew.samples.designsupportlibrary.models.Contact;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_contact, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        new AsyncTask<Void, Void, List<Contact>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please wait....");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                super.onPostExecute(contacts);
                recyclerView.setAdapter(new SimpleContactsAdapter(getActivity(),
                        contacts));
                progressDialog.dismiss();
            }

            protected List<Contact> doInBackground(Void... params) {
                return fetchContacts();
            }
        }.execute(null,null,null);

    }

    public List<Contact> fetchContacts() {

        String phoneNumber = null;
        String email = null;
        ArrayList<Contact> list = new ArrayList<>();

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String SORT_ORDER = ContactsContract.Contacts.SORT_KEY_PRIMARY;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        String PHOTO_URI = ContactsContract.CommonDataKinds.Phone.PHOTO_URI;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, SORT_ORDER);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                String image = cursor.getString(cursor.getColumnIndex(PHOTO_URI));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    ArrayList<String> phoneNumberList = new ArrayList<>();
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        phoneNumberList.add(phoneNumber);
                    }
                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    ArrayList<String> emailList = new ArrayList<>();
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        emailList.add(email);
                    }
                    emailCursor.close();
                    if (image != null && !image.isEmpty() && image.length() > 0) {
                        Contact contact = new Contact(contact_id, name, null, name, phoneNumber, image, email, null, phoneNumberList, emailList);
                        list.add(contact);
                    }
                }
            }
        }
        cursor.close();
        return list;
    }

}
