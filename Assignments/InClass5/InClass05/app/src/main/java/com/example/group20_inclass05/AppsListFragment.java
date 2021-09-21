package com.example.group20_inclass05;

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

import com.example.group20_inclass05.databinding.FragmentAppCategoriesBinding;
import com.example.group20_inclass05.databinding.FragmentAppsListBinding;

import java.util.ArrayList;

public class AppsListFragment extends Fragment {

    FragmentAppsListBinding binding;
    ArrayList<DataServices.App> apps = new ArrayList<>();
    AppsAdapter adapter;

    private static final String ARG_PARAM_CATEGORY = "ARG_PARAM_CATEGORY";

    private String category;

    public AppsListFragment() {
        // Required empty public constructor
    }

    public static AppsListFragment newInstance(String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_PARAM_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppsListBinding.inflate(inflater, container, false);
        getActivity().setTitle(category);

        apps = DataServices.getAppsByCategory(category);
        adapter = new AppsAdapter(getContext(), R.layout.app_row_item, apps);
        binding.listView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.gotoAppDetails(apps.get(position));
            }
        });
    }

    AppsListFragment.AppsListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AppsListFragment.AppsListListener) {
            mListener = (AppsListFragment.AppsListListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement AppsListListener"));
        }
    }

    public interface AppsListListener {
        void gotoAppDetails(DataServices.App app);
    }

}