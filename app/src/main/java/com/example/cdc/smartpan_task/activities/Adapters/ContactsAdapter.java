package com.example.cdc.smartpan_task.activities.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Data.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>{

    private List<Contact> contactList;
    private Context mContext;

    public ContactsAdapter(List<Contact> contactList){
        //this.mContext = mContext;
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.contactNameTextView.setText(contact.getContactName());
        holder.contactNumberTextView.setText(contact.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView contactNameTextView, contactNumberTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            contactNameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            contactNumberTextView = (TextView) itemView.findViewById(R.id.contact_number);
        }
    }
}
