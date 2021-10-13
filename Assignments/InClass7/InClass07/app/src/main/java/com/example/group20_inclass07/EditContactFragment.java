package com.example.group20_inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.group20_inclass07.databinding.FragmentContactDetailsBinding;
import com.example.group20_inclass07.databinding.FragmentEditContactBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EditContactFragment extends Fragment {
    FragmentEditContactBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private Contact contact;

    public EditContactFragment() {
        // Required empty public constructor
    }

    public static EditContactFragment newInstance(Contact contact) {
        EditContactFragment fragment = new EditContactFragment();
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
        binding = FragmentEditContactBinding.inflate(inflater, container, false);
        getActivity().setTitle("Edit Contact");
        binding.editTextName.setText(contact.getName());
        binding.editTextPhoneNumber.setText(contact.getPhoneNumber());
        binding.editTextEmail.setText(contact.getEmail());
        String type = contact.getType();

        if(type.equals("CELL")) {
            binding.radioGroupType.check(R.id.radioButtonCell);
        } else if(type.equals("OFFICE")) {
            binding.radioGroupType.check(R.id.radioButtonOffice);
        } else {
            binding.radioGroupType.check(R.id.radioButtonHome);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
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
                    updateContact(name, email, phoneNumber, type);
                }
            }
        });

    }

    void updateContact(String name, String email, String phone, String type) {
        FormBody formBody = new FormBody.Builder()
                .add("id", contact.getId())
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("type", type)
                .build();

        Contact updatedContact = new Contact(name, email, phone, type, contact.getId());

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/update")
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
                            mListener.updateContact(updatedContact);
                        }
                    });
                }
            }
        });
    }

    // Listener
    EditContactFragment.EditContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof EditContactFragment.EditContactListener) {
            mListener = (EditContactFragment.EditContactListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement EditContactListener"));
        }
    }

    public interface EditContactListener {
        void updateContact(Contact contact);
    }
}