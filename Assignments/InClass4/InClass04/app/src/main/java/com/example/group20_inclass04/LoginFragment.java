package com.example.group20_inclass04;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    EditText editTextEmail;
    EditText editTextPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login");

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);


        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.isEmpty() || email == null) {
                    Toast.makeText(view.getContext(), "Missing email input!", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty() || password == null) {
                    Toast.makeText(view.getContext(), "Missing password input!", Toast.LENGTH_SHORT).show();
                } else {
                    DataServices.AccountRequestTask task = DataServices.login(email, password);

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


        view.findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoRegister();
            }
        });

        return view;
    }

    LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof LoginListener) {
            mListener = (LoginListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement LoginListener"));
        }
    }

    public interface LoginListener {
        void gotoRegister();
        void gotoAccount(DataServices.Account account);
    }

}