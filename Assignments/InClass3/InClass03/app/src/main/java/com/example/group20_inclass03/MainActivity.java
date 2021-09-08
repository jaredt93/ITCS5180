package com.example.group20_inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Assignment: In Class Assignment #03
 * File Name: Group20_InClass03
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity {

    // codes, keys, and tags
    final String TAG = "InClass03 Log";
    final static public int REQ_CODE = 100;
    final static public String PROFILE_KEY = "PROFILE";

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

        // Select Button Actions, use start activity for result
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectDepartmentActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        // Submit Button Actions, using explicit intent
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String id = editTextID.getText().toString();
                String department = departmentValue.getText().toString();

                if(name.trim().isEmpty() || email.trim().isEmpty() || id.trim().isEmpty() || department.isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Invalid input.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                    toast.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(PROFILE_KEY, new Profile(name, email, id, department));

                    startActivity(intent);
                }
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