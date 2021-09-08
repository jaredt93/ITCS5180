package com.example.group20_inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * Assignment: In Class Assignment #03
 * File Name: Group20_InClass03
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity {

    // codes
    final String TAG = "InClass03 Log";
    final static public int REQ_CODE = 100;

    // Declare variables
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextID;
    TextView departmentValue;
    Button buttonSelect;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Registration");

        // Initialize variables with findViewById's
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextTextEmail);
        editTextID = findViewById(R.id.editTextID);
        departmentValue = findViewById(R.id.departmentValue);
        buttonSelect = findViewById(R.id.buttonSelect);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Select Button Actions
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectDepartmentActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        // Submit Button Actions
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String id = editTextID.getText().toString();
                String department = departmentValue.getText().toString();



            }
        });

    }

    // receive activity from SelectDepartmentActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra(SelectDepartmentActivity.DATA_ENTERED)) {
                    String dataEntered = data.getStringExtra(SelectDepartmentActivity.DATA_ENTERED);
                    departmentValue.setText(dataEntered);
                }

                Log.d(TAG, "onActivityResult: RESULT_OK");
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: RESULT_CANCELED");
            }
        }
    }
}