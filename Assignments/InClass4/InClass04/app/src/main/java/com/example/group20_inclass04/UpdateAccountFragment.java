package com.example.group20_inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();

                if(name.isEmpty() || name == null) {
                    Toast.makeText(view.getContext(), "Missing name input!", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty() || password == null) {
                    Toast.makeText(view.getContext(), "Missing password input!", Toast.LENGTH_SHORT).show();
                } else {
                    DataServices.AccountRequestTask task = DataServices.update(account, name, password);

                    if (task.isSuccessful()) { //successful
                        DataServices.Account account = task.getAccount();
                        mListener.setAccount(account);
                        mListener.popBack();
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
                mListener.popBack();
            }
        });

        return view;
    }

    UpdateAccountFragment.UpdateAccountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof UpdateAccountFragment.UpdateAccountListener) {
            mListener = (UpdateAccountFragment.UpdateAccountListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement UpdateAccountListener"));
        }
    }

    public interface UpdateAccountListener {
        void popBack();
        void setAccount(DataServices.Account account);
    }

}