package com.example.group20_hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_hw05.databinding.FragmentForumsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ForumsFragment extends Fragment implements ForumsRecyclerViewAdapter.IForumsRecycler {
    FragmentForumsBinding binding;
    FirebaseAuth mAuth;
    ArrayList<Forum> forums = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ForumsRecyclerViewAdapter adapter;

    public ForumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new ForumsRecyclerViewAdapter(forums, this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forums");
        getForums();

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                mListener.gotoLoginFragment();
            }
        });

        binding.buttonNewForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoCreateForumFragment();
            }
        });
    }

    private void getForums() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forums")
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();

                        for(QueryDocumentSnapshot document: value) {
                            //Forum forum = document.toObject(Forum.class)
                            forums.add(new Forum(document.getString("title"), document.getString("creator"), document.getString("creatorUid"), document.getString("description"), document.getTimestamp("timeStamp"), document.getId(), (HashMap<String, Object>) document.get("likedBy")));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void deleteForum(Forum forum) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forums").document(forum.getDocId())
                .delete();
    }

    @Override
    public void clickLikeButton(Forum forum) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> forumUpdate = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();

        HashMap<String, Object> mapUpdate = new HashMap<>();
        mapUpdate = forum.getLikedBy();

        if(mapUpdate == null || !forum.getLikedBy().containsKey(mAuth.getCurrentUser().getUid())) {
            mapUpdate.put(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getUid());
            forumUpdate.put("likedBy", mapUpdate);
        } else {
            mapUpdate.remove(mAuth.getCurrentUser().getUid());
            forumUpdate.put("likedBy", mapUpdate);
        }

        db.collection("forums").document(forum.getDocId())
                .update(forumUpdate);
    }

    @Override
    public void gotoForumDetailsFragment(Forum forum) {
        mListener.gotoForumDetailsFragment(forum);
    }


    ForumsFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumsFragmentListener) context;
    }

    interface ForumsFragmentListener {
        void gotoLoginFragment();
        void gotoCreateForumFragment();
        void gotoForumDetailsFragment(Forum forum);
    }

}