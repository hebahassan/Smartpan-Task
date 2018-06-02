package com.example.cdc.smartpan_task.activities.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Data.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    View view;

    ListView contactsListView;
    List<String> contactsStringList;
    ArrayAdapter<String> contactsArrayAdapter;

    Contact contact;

    Cursor cursor ;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;
    Button showContactsButton;

    public ContactsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts, container, false);

        contact = new Contact();

        //Initialize contents
        showContactsButton = (Button) view.findViewById(R.id.showContacts_Button);
        contactsListView = (ListView) view.findViewById(R.id.contacts_listView);
        contactsStringList = new ArrayList<>();

        //Check Permissions for APIs > 23
        EnableRuntimePermission();

        showContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetContactsIntoArrayList();

                contactsArrayAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.contact_row, R.id.contacts_info, contactsStringList);

                contactsListView.setAdapter(contactsArrayAdapter);

            }
        });

        return view;
    }

    private void EnableRuntimePermission(){
        /**
        * Allow Read Contacts Permission
        * */
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                getActivity(),
                Manifest.permission.READ_CONTACTS))
        {
            Toast.makeText(getActivity(), "Permission is Allowed", Toast.LENGTH_SHORT).show();
        }
        else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    /**
    * Return Phone Contacts in ListView
    * */
    private void GetContactsIntoArrayList(){

        showContactsButton.setVisibility(View.GONE);

        cursor = getActivity().getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contact.setContactName(name);
            contact.setContactNumber(phonenumber);

            System.out.println("Contact: " + contact.getContactName() + " "  + ":" + " " + contact.getContactNumber());

            contactsStringList.add(name + "\n\n" + phonenumber);

        }
        cursor.close();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestPermissionCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Permission Denied, Application Can't Access Contacts", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }
}
