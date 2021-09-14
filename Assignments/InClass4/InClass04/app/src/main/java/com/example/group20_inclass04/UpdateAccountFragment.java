package com.example.group20_inclass04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class UpdateAccountFragment extends Fragment {

    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";

    private DataServices.Account account;

    TextView textViewEmail;
    EditText editTextName;
    EditText editTextPassword;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }

    public static UpdateAccountFragment newInstance(DataServices.Account account) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.account = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        getActivity().setTitle("Update Account");

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewEmail.setText(this.account.getEmail());

        editTextName = view.findViewById(R.id.editTextName);
        editTextName.setText(this.account.getName());

        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPassword.setText(this.account.getPassword());

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}