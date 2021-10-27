package com.example.group20_inclass08;

import android.content.Context;
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

import com.example.group20_inclass08.databinding.FragmentForumsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Set;

public class ForumsFragment extends Fragment implements ForumsRecyclerViewAdapter.IForumsRecycler {
    FragmentForumsBinding binding;
    FirebaseAuth mAuth;
    ArrayList<Forum> forums = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ForumsRecyclerViewAdapter adapter;

    String creator;

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
        getData();

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

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        creator = "";

//        db.collection("users").document(id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                creator = document.getString("name");
//                                Log.d("demo", "No such document" + creator);
//                            } else {
//                                Log.d("demo", "No such document");
//                            }
//                        } else {
//                            Log.d("demo", "get failed with ", task.getException());
//                        }
//                    }
//                });

        db.collection("forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();

                        for(QueryDocumentSnapshot document: value) {
                            forums.add(new Forum(document.getString("title"), document.getString("creator"), document.getString("creatorUid"), document.getString("description"), document.get("timeStamp")));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void deleteForum(Forum forum) {

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
    }

}