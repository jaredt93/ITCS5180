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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.group20_inclass07.databinding.FragmentContactsListBinding;
import com.example.group20_inclass07.databinding.FragmentNewContactBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NewContactFragment extends Fragment {
    FragmentNewContactBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    public NewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewContactBinding.inflate(inflater, container, false);
        getActivity().setTitle("New Contact");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoContactsList();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String phoneNumber = binding.editTextPhoneNumber.getText().toString();
                String type = "CELL";

                int id = binding.radioGroupType.getCheckedRadioButtonId();

                if(id == R.id.radioButtonOffice) {
                    type = "OFFICE";
                } else if(id == R.id.radioButtonHome) {
                    type = "HOME";
                }

                if(name.isEmpty() || name == null) {
                    Toast.makeText(view.getContext(), "Invalid name entry.", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty() || email == null) {
                    Toast.makeText(view.getContext(), "Invalid email entry.", Toast.LENGTH_SHORT).show();
                } else if(phoneNumber.isEmpty() || phoneNumber == null) {
                    Toast.makeText(view.getContext(), "Invalid phone number entry.", Toast.LENGTH_SHORT).show();
                } else {
                    createContact(name, email, phoneNumber, type);
                }
            }
        });
    }

    // Create a contact
    void createContact(String name, String email, String phone, String type) {
        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("type", type)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/create")
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

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListener.gotoContactsList();
                        }
                    });
                }
            }
        });
    }


    // Listener
    NewContactFragment.NewContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof NewContactFragment.NewContactListener) {
            mListener = (NewContactFragment.NewContactListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement NewContactListener"));
        }
    }

    public interface NewContactListener {
        void gotoContactsList();
    }

}