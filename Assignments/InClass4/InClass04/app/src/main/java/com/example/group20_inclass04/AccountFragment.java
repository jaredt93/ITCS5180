package com.example.group20_inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

public class AccountFragment extends Fragment {

    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";

    private DataServices.Account account;

    TextView textViewName;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(DataServices.Account account) {
        AccountFragment fragment = new AccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        getActivity().setTitle("Account");
        textViewName = view.findViewById(R.id.textViewName);
        textViewName.setText(this.account.getName());

        view.findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoUpdateAccount(account);
            }
        });

        view.findViewById(R.id.buttonLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoLogin();
            }
        });

        return view;
    }


    AccountFragment.AccountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof RegisterFragment.RegisterListener) {
            mListener = (AccountFragment.AccountListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement AccountListener"));
        }
    }

    public interface AccountListener {
        void gotoLogin();
        void gotoUpdateAccount(DataServices.Account account);
    }
}