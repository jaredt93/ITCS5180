package com.example.group20_inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.group20_inclass08.databinding.FragmentCreateForumBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.type.DateTime;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class CreateForumFragment extends Fragment {
    FragmentCreateForumBinding binding;
    FirebaseAuth mAuth;

    public CreateForumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("New Forum");
        mAuth = FirebaseAuth.getInstance();

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.editTextTitle.getText().toString();
                String description = binding.editTextDescription.getText().toString();

                if(title.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Forum Title", Toast.LENGTH_SHORT).show();
                } else if(description.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Forum Description", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String id = mAuth.getCurrentUser().getUid();

                    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
                    Date date = new Date();
                    String strDate = dateFormat.format(date).toString();

                    HashMap<String, Object> forum = new HashMap<>();
                    forum.put("title", title);
                    forum.put("creator", mAuth.getCurrentUser().getDisplayName());
                    forum.put("creatorUid", id);
                    forum.put("description", description);
                    forum.put("timeStamp", strDate);

                    db.collection("forums")
                            .add(forum)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    mListener.goBack();
                                }
                            });
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBack();
            }
        });
    }


    CreateForumFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateForumFragmentListener) context;
    }

    interface CreateForumFragmentListener {
        void goBack();
    }

}