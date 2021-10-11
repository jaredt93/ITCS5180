package com.example.group20_inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_inclass07.databinding.FragmentContactDetailsBinding;
import com.example.group20_inclass07.databinding.FragmentEditContactBinding;

public class EditContactFragment extends Fragment {
    FragmentEditContactBinding binding;

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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoContactDetails();
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
        void gotoContactDetails();
    }
}