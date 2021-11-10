package com.example.group20_inclass09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GradesRecyclerViewAdapter extends RecyclerView.Adapter<GradesRecyclerViewAdapter.GradeViewHolder> {
    ArrayList<Grade> grades;
    IGradesRecycler mListener;

    public GradesRecyclerViewAdapter(ArrayList<Grade> grades, IGradesRecycler mListener) {
        this.grades = grades;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_row_item, parent, false);
        GradeViewHolder gradeViewHolder = new GradeViewHolder(view, mListener);
        return gradeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade grade = grades.get(position);
        holder.grade = grade;

        holder.textViewGrade.setText(grade.getCourseGrade());
        holder.textViewTitle.setText(grade.getCourseTitle());
        holder.textviewDescription.setText(grade.getCourseDescription());

        DecimalFormat df = new DecimalFormat("#");
        holder.textViewCreditHours.setText(df.format(grade.getCreditHours()) + " Credit Hours");
    }

    @Override
    public int getItemCount() {
        return this.grades.size();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGrade;
        TextView textViewTitle;
        TextView textviewDescription;
        TextView textViewCreditHours;
        ImageButton imageButtonDelete;

        View rootView;
        int position;
        Grade grade;
        IGradesRecycler mListener;

        public GradeViewHolder(@NonNull View itemView, IGradesRecycler mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;

            textViewGrade = itemView.findViewById(R.id.textViewGrade);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textviewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewCreditHours = itemView.findViewById(R.id.textViewCreditHours);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);

            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteGrade(grade.getId());
                }
            });

        }
    }

    interface IGradesRecycler {
        void deleteGrade(long id);
    }
}
