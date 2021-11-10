package com.example.group20_inclass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.group20_inclass09.databinding.FragmentAddCourseBinding;
import com.example.group20_inclass09.databinding.FragmentGradesBinding;

public class AddCourseFragment extends Fragment {
    FragmentAddCourseBinding binding;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCourseBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Course");

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });



        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNumber = binding.editTextCourseNumber.getText().toString();
                String courseName = binding.editTextCourseName.getText().toString();
                String creditHours = binding.editTextCreditHours.getText().toString();
                String grade = "";

                int gradeChecked = binding.radioGroup.getCheckedRadioButtonId();

                if(gradeChecked == R.id.radioButtonA) {
                    grade = "A";
                } else if(gradeChecked == R.id.radioButtonB) {
                    grade = "B";
                } else if(gradeChecked == R.id.radioButtonC) {
                    grade = "C";
                } else if(gradeChecked == R.id.radioButtonD) {
                    grade = "D";
                } else {
                    grade = "F";
                }

                if(courseNumber.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Course Number", Toast.LENGTH_SHORT).show();
                } else if(courseName.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Course Name", Toast.LENGTH_SHORT).show();
                } else if (creditHours.isEmpty() || Integer.valueOf(creditHours) == 0) {
                    Toast.makeText(getActivity(), "Enter Valid Credit Hours", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.submitGrade(new Grade(courseNumber, courseName, grade, Integer.valueOf(creditHours)));
                }

            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoGradesFragment();
            }
        });
    }


    AddCourseFragment.AddCourseFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddCourseFragment.AddCourseFragmentListener) context;
    }

    interface AddCourseFragmentListener {
        void submitGrade(Grade grade);
        void gotoGradesFragment();
    }
}