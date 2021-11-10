package com.example.group20_inclass09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

/*
 * Assignment: In Class Assignment #09
 * File Name: Group20_InClass09
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity implements GradesFragment.GradesFragmentListener, AddCourseFragment.AddCourseFragmentListener {
    AppDatabase db;
    ArrayList<Grade> grades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(this, AppDatabase.class, "grades.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        getGrades();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, GradesFragment.newInstance(grades))
                .commit();
    }

    void getGrades() {
        grades.clear();
        grades.addAll(db.GradeDAO().getAll());
    }

    @Override
    public void gotoAddCourseFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddCourseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteGrade(long id) {
        db.GradeDAO().delete(id);
        getGrades();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, GradesFragment.newInstance(grades))
                .commit();
    }

    @Override
    public void submitGrade(Grade grade) {
        db.GradeDAO().insert(grade);
        getGrades();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoGradesFragment() {
        getSupportFragmentManager().popBackStack();
    }
}