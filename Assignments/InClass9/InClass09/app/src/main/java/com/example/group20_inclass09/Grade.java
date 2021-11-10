package com.example.group20_inclass09;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "grade")
public class Grade implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "course_title")
    public String courseTitle;

    @ColumnInfo(name = "course_description")
    public String courseDescription;

    @ColumnInfo(name = "course_grade")
    public String courseGrade;

    @ColumnInfo(name = "credit_hours")
    public double creditHours;

    public Grade(String courseTitle, String courseDescription, String courseGrade, double creditHours) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseGrade = courseGrade;
        this.creditHours = creditHours;
    }

    public Grade() {
        //empty constructor
    }

    public long getId() {
        return id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public double getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseGrade='" + courseGrade + '\'' +
                ", creditHours=" + creditHours +
                '}';
    }
}