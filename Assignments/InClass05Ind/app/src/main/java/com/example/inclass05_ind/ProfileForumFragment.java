package com.example.inclass05_ind;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileForumFragment extends Fragment {

    EditText editTextFirst;
    EditText editTextLast;
    EditText editTextEmail;
    EditText editTextAge;

    public ProfileForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_forum, container, false);
        getActivity().setTitle("Profile Form");

        editTextFirst = view.findViewById(R.id.editTextFirst);
        editTextLast = view.findViewById(R.id.editTextLast);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextAge = view.findViewById(R.id.editTextAge);

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editTextFirst.getText().toString();
                String lastName = editTextLast.getText().toString();
                String email = editTextEmail.getText().toString();
                String age = editTextAge.getText().toString();


                if(firstName.isEmpty() || firstName == null) {
                    Toast.makeText(view.getContext(), "Missing first name input!", Toast.LENGTH_SHORT).show();
                } else if(lastName.isEmpty() || lastName == null) {
                    Toast.makeText(view.getContext(), "Missing last name input!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty() || email == null) {
                    Toast.makeText(view.getContext(), "Missing email input!", Toast.LENGTH_SHORT).show();
                } else {
                    User user;
                    user = new User(firstName, lastName, email, age);
                    mListener.gotoHome(user);
                }
            }
        });

        return view;
    }

    ProfileForumListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ProfileForumListener) {
            mListener = (ProfileForumListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement ProfileForumListener"));
        }
    }

    public interface ProfileForumListener {
        void gotoHome(User user);
    }
}