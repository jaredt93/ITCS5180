package com.example.group20_inclass07;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsListRecyclerViewAdapter extends RecyclerView.Adapter<ContactsListRecyclerViewAdapter.UserViewHolder> {
    ArrayList<Contact> contacts;
    IContactsListRecycler mListener;

    public ContactsListRecyclerViewAdapter(ArrayList<Contact> contacts, IContactsListRecycler mListener) {
        this.contacts = contacts;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view, mListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contact = contact;

        holder.textViewName.setText(contact.name);
        holder.textViewEmail.setText(contact.email);
        holder.textViewPhoneNumber.setText(contact.phoneNumber);
        holder.textViewType.setText(contact.type);
    }

    public void remove(int position) {
        contacts.remove(position);
        notifyItemChanged(position);
        notifyItemRangeRemoved(position, 1);
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewEmail;
        TextView textViewPhoneNumber;
        TextView textViewType;

        View rootView;
        int position;
        Contact contact;
        IContactsListRecycler mListener;

        public UserViewHolder(@NonNull View itemView, IContactsListRecycler mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            textViewType = itemView.findViewById(R.id.textViewType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.gotoContactDetails(contact);
                }
            });

            itemView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteContact(contact);
                }
            });
        }
    }

    interface IContactsListRecycler {
        void gotoContactDetails(Contact contact);
        void deleteContact(Contact contact);
    }
}