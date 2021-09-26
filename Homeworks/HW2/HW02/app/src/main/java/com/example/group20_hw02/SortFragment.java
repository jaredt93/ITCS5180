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

import com.example.group20_hw02.databinding.FragmentSortBinding;

public class SortFragment extends Fragment implements SortRecyclerViewAdapter.ISortRecycler {

    FragmentSortBinding binding;
    private static final String[] attributes = new String[] {"Age", "Name", "State"};
    LinearLayoutManager layoutManager;
    SortRecyclerViewAdapter adapter;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSortBinding.inflate(inflater, container,false);
        getActivity().setTitle("Sort");

        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new SortRecyclerViewAdapter(attributes, this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }


    @Override
    public void sortUsers(String sortAttribute, Boolean ascending) {
        mListener.sortUsers(sortAttribute, ascending);
    }

    SortFragment.SortListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof SortFragment.SortListener) {
            mListener = (SortFragment.SortListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement SortListener"));
        }
    }

    public interface SortListener {
        void sortUsers(String sortAttribute, Boolean ascending);
    }

}