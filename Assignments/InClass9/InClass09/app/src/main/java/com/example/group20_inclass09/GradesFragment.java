package com.example.group20_inclass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_inclass09.databinding.FragmentGradesBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GradesFragment extends Fragment implements GradesRecyclerViewAdapter.IGradesRecycler {
    FragmentGradesBinding binding;
    LinearLayoutManager layoutManager;
    GradesRecyclerViewAdapter adapter;

    double GPA = 4.0;
    double credits = 0;

    private static final String ARG_GRADES = "ARG_GRADES";
    private ArrayList<Grade> mGrades;

    public static GradesFragment newInstance(ArrayList<Grade> mGrades) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GRADES, mGrades);
        fragment.setArguments(args);
        return fragment;
    }

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGrades = (ArrayList<Grade>) getArguments().getSerializable(ARG_GRADES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new GradesRecyclerViewAdapter(mGrades, this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Grades");
        calculateGPA();

        DecimalFormat dfGPA = new DecimalFormat("0.00");
        DecimalFormat dfHours = new DecimalFormat("0.0");

        binding.textViewGPA.setText("GPA: " + dfGPA.format(GPA));
        binding.textViewHours.setText("Hours: " + dfHours.format(credits));
    }

    public void calculateGPA() {
        double gradePoints = 0;
        credits = 0;

        for(Grade grade: mGrades) {
            credits = credits + grade.getCreditHours();
            String letter = grade.getCourseGrade();

            if(letter.equals("A")) {
                gradePoints = gradePoints + (4 * grade.getCreditHours());
            } else if(letter.equals("B")) {
                gradePoints = gradePoints + (3 * grade.getCreditHours());
            } else if(letter.equals("C")) {
                gradePoints = gradePoints + (2 * grade.getCreditHours());
            } else if(letter.equals("D")) {
                gradePoints = gradePoints + (1 * grade.getCreditHours());
            }
        }

        if(credits == 0) {
            GPA = 4.0;
        } else {
            GPA = gradePoints / credits;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.plus_btn) {
            mListener.gotoAddCourseFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deleteGrade(long id) {
        mListener.deleteGrade(id);
    }

    GradesFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (GradesFragmentListener) context;
    }

    interface GradesFragmentListener {
        void gotoAddCourseFragment();
        void deleteGrade(long id);
    }

}