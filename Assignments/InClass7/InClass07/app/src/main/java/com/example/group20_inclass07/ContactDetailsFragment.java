package com.example.group20_inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_inclass07.databinding.FragmentContactDetailsBinding;
import com.example.group20_inclass07.databinding.FragmentNewContactBinding;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ContactDetailsFragment extends Fragment {
    FragmentContactDetailsBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private Contact contact;

    public void updateContact(String name, String phoneNumber, String email, String type) {
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(email);
        contact.setType(type);
    }

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    public static ContactDetailsFragment newInstance(Contact contact) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = (Contact) getArguments().getSerializable(ARG_PARAM_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false);
        getActivity().setTitle("Contact Details");
        binding.textViewName.setText(contact.getName());
        binding.textViewPhoneNumber.setText(contact.getPhoneNumber());
        binding.textViewEmail.setText(contact.getEmail());
        binding.textViewType.setText(contact.getType());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoContactsList(contact);
            }
        });

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoEditContact(contact);
            }
        });

    }

    // Listener
    ContactDetailsFragment.ContactDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ContactDetailsFragment.ContactDetailsListener) {
            mListener = (ContactDetailsFragment.ContactDetailsListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement ContactDetailsListener"));
        }
    }

    public interface ContactDetailsListener {
        void gotoContactsList(Contact contact);
        void gotoEditContact(Contact contact);
    }

}