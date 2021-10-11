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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContact();
                mListener.gotoContactsList();
            }
        });

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoEditContact(contact);
            }
        });

    }

    void deleteContact() {
        FormBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/delete")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            // Child thread
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("demo", "onResponse: " + body);
                }
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
        void gotoContactsList();
        void gotoEditContact(Contact contact);
    }

}