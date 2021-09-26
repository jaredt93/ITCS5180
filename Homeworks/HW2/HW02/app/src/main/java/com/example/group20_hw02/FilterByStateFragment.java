package com.example.group20_hw02;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.group20_hw02.databinding.FragmentFilterByStateBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class FilterByStateFragment extends Fragment {

    FragmentFilterByStateBinding binding;
    ArrayList<DataServices.User> users = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public FilterByStateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterByStateBinding.inflate(inflater, container, false);
        getActivity().setTitle("Filter By State");

        retrieveStates();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, states);
        binding.listView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.filterUsers(states.get(position));
            }
        });
    }

    // Retrieves the list of states
    private void retrieveStates() {
        users = DataServices.getAllUsers();

        for(DataServices.User user: users) {
            String state = user.state;

            if(!states.contains(state)) {
                states.add(state);
            }
        }

        Collections.sort(states, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });

        states.add(0, "All States");
    }


    // Listener
    FilterByStateFragment.FilterByStateListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof FilterByStateFragment.FilterByStateListener) {
            mListener = (FilterByStateFragment.FilterByStateListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement FilterByStateListener"));
        }
    }

    public interface FilterByStateListener {
        void filterUsers(String state);
    }
}