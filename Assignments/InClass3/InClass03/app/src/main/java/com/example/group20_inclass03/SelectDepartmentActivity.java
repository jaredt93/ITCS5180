package com.example.group20_inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SelectDepartmentActivity extends AppCompatActivity {

    final static public String DATA_ENTERED = "DATA_ENTERED";

    // Declare variables
    RadioGroup radioGroupDepartments;
    Button buttonCancel;
    Button buttonSelect;
    String department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department);
        setTitle("Department");

        // Initialize variables with findViewById's
        radioGroupDepartments = findViewById(R.id.radioGroupDepartments);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSelect = findViewById(R.id.buttonSelect);
        department = "Computer Science";

        // Radio group to set department
        radioGroupDepartments.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.radioButtonCS) {
                    department = "Computer Science";
                } else if(checkedId == R.id.radioButtonSIS) {
                    department = "Software Info. Systems";
                } else if(checkedId == R.id.radioButtonBI) {
                    department = "Bio Informatics";
                } else if(checkedId == R.id.radioButtonDS) {
                    department = "Data Science";
                }
            }
        });

        // button cancel actions
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // button select actions, sends department back to main activity
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(DATA_ENTERED, department);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}