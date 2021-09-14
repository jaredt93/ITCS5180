package com.example.group20_inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle("Register Account");

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(name.isEmpty() || name == null) {
                    Toast.makeText(view.getContext(), "Missing name input!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty() || email == null) {
                    Toast.makeText(view.getContext(), "Missing email input!", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty() || password == null) {
                    Toast.makeText(view.getContext(), "Missing password input!", Toast.LENGTH_SHORT).show();
                } else {
                    DataServices.AccountRequestTask task = DataServices.register(name, email, password);

                    if (task.isSuccessful()) { //successful
                        DataServices.Account account = task.getAccount();
                        mListener.gotoAccount(account);
                    } else { //not successful
                        String error = task.getErrorMessage();
                        Toast.makeText(view.getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoLogin();
            }
        });

        return view;
    }


    RegisterFragment.RegisterListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof RegisterFragment.RegisterListener) {
            mListener = (RegisterFragment.RegisterListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement RegisterListener"));
        }
    }

    public interface RegisterListener {
        void gotoLogin();
        void gotoAccount(DataServices.Account account);
    }
}