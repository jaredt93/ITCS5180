package com.example.group20_hw02;

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

import com.example.group20_hw02.databinding.FragmentUsersBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UsersFragment extends Fragment {

    FragmentUsersBinding binding;
    ArrayList<DataServices.User> users = new ArrayList<>();
    LinearLayoutManager layoutManager;
    UsersRecyclerViewAdapter adapter;

    private static final String ARG_PARAM_STATE = "ARG_PARAM_STATE";
    private String state;
    private static final String ARG_PARAM_SORT_ATTRIBUTE = "ARG_PARAM_SORT_ATTRIBUTE";
    private String sortAttribute;
    private static final String ARG_PARAM_ASCENDING = "ARG_PARAM_ASCENDING";
    private Boolean ascending;
    int order = 1;

    public UsersFragment() {
        // Required empty public constructor
    }

    // Create a new instance using state, sort attribute, and ascending/descending input
    public static UsersFragment newInstance(String state, String sortAttribute, Boolean ascending) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_STATE, state);
        if(sortAttribute != null && ascending != null) {
            args.putString(ARG_PARAM_SORT_ATTRIBUTE, sortAttribute);
            args.putBoolean(ARG_PARAM_ASCENDING, ascending);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            state = getArguments().getString(ARG_PARAM_STATE);
            sortAttribute = getArguments().getString(ARG_PARAM_SORT_ATTRIBUTE);
            ascending = getArguments().getBoolean(ARG_PARAM_ASCENDING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        getActivity().setTitle("Users");

        users = DataServices.getAllUsers();

        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new UsersRecyclerViewAdapter(users);
        binding.recyclerView.setAdapter(adapter);
        filterUsers(state);
        sortUsers(sortAttribute, ascending);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoFilterByState();
            }
        });


        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoSort();
            }
        });

    }

    // Filters the list of users based on state input
    private void filterUsers(String state) {
        if(!state.equals("All States") && state != null) {
            for (int position = 0; position < adapter.getItemCount();) {
                if (!users.get(position).state.equals(state)) {
                    adapter.remove(position);
                } else {
                    position++;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    // Sorts the list of users based on input
    private void sortUsers(String sortAttribute, Boolean ascending) {
        if(sortAttribute != null && ascending != null) {
            if (!ascending) {
                order = -1;
            }

            Collections.sort(users, new Comparator<DataServices.User>() {
                @Override
                public int compare(DataServices.User u1, DataServices.User u2) {
                    if (sortAttribute.equals("Age")) {
                        return order * Integer.toString(u1.age).compareTo(Integer.toString(u2.age));
                    } else if (sortAttribute.equals("Name")) {
                        return order * u1.name.compareTo(u2.name);
                    } else {
                        return order * u1.state.compareTo(u2.state);
                    }
                }
            });
        }
    }


    // Listener
    UsersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof UsersListener) {
            mListener = (UsersListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement UsersListener"));
        }
    }

    public interface UsersListener {
        void gotoFilterByState();
        void gotoSort();
    }

}