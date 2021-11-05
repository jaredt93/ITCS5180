package com.example.group20_hw05;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.group20_hw05.databinding.FragmentCreateForumBinding;
import com.example.group20_hw05.databinding.FragmentForumDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ForumDetailsFragment extends Fragment implements ForumDetailsRecyclerViewAdapter.ICommentsRecycler {
    FragmentForumDetailsBinding binding;
    FirebaseAuth mAuth;
    ArrayList<Comment> comments = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ForumDetailsRecyclerViewAdapter adapter;

    private static final String ARG_PARAM_FORUM = "ARG_PARAM_FORUM";
    private Forum forum;

    public ForumDetailsFragment() {
        // Required empty public constructor
    }

    public static ForumDetailsFragment newInstance(Forum forum) {
        ForumDetailsFragment fragment = new ForumDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_FORUM, forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (Forum)getArguments().getSerializable(ARG_PARAM_FORUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForumDetailsBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new ForumDetailsRecyclerViewAdapter(comments, this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forum Details");
        getComments();

        binding.textViewTitle.setText(forum.getTitle());
        binding.textViewCreator.setText(forum.getCreator());
        binding.textViewDescription.setText(forum.getDescription());

        binding.buttonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
            }
        });
    }

    private void getComments() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forums")
                .document(forum.getDocId())
                .collection("comments")
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        comments.clear();

                        for(QueryDocumentSnapshot document: value) {
                            comments.add(new Comment(document.getString("creator"), document.getString("creatorUid"), document.getString("description"), document.getId(), document.getTimestamp("timeStamp")));
                        }

                        binding.textViewCommentNumber.setText(comments.size() + " Comments");
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void postComment() {
        mAuth = FirebaseAuth.getInstance();
        String commentDescription = binding.editTextComment.getText().toString();

        if(commentDescription.isEmpty()) {
            Toast.makeText(getActivity(), "Enter Comment", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String id = mAuth.getCurrentUser().getUid();

            HashMap<String, Object> comment = new HashMap<>();
            comment.put("creator", mAuth.getCurrentUser().getDisplayName());
            comment.put("creatorUid", id);
            comment.put("description", commentDescription);
            comment.put("timeStamp", FieldValue.serverTimestamp());

            db.collection("forums").document(forum.getDocId()).collection("comments")
                    .add(comment);

            binding.editTextComment.setText("");
        }
    }

    @Override
    public void deleteComment(Comment comment) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forums").document(forum.getDocId())
                .collection("comments")
                .document(comment.getDocId())
                .delete();
    }
}