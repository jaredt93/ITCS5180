package com.example.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/*
 * Assignment: In Class #02
 * File Name: Group20_InClass02
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity {
    DecimalFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ticketPriceEntry = findViewById(R.id.ticketPriceEntry);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        TextView discountedPriceResult = (TextView) findViewById(R.id.discountedPriceResult);
        numberFormat = new DecimalFormat("#.00");

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                double ticketPrice = 0;

                if(!ticketPriceEntry.getText().toString().isEmpty()) {
                    try {
                        ticketPrice = Double.parseDouble(ticketPriceEntry.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Number must be a valid positive number!", Toast.LENGTH_SHORT).show();
                    }

                    if(checkedId == R.id.radioButton5) {
                        discountedPriceResult.setText(discount(ticketPrice, .05));
                    } else if(checkedId == R.id.radioButton10) {
                        discountedPriceResult.setText(discount(ticketPrice, .10));
                    } else if(checkedId == R.id.radioButton15) {
                        discountedPriceResult.setText(discount(ticketPrice, .15));
                    } else if(checkedId == R.id.radioButton20) {
                        discountedPriceResult.setText(discount(ticketPrice, .20));
                    } else if(checkedId == R.id.radioButton50) {
                        discountedPriceResult.setText(discount(ticketPrice, .50));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Number must be a valid positive number!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketPriceEntry.setText(null);
                radioGroup.check(R.id.radioButton5);
                discountedPriceResult.setText("");
            }
        });

    }

    // Calculates the discount and converts to a String
    public String discount(double ticketPrice, double percentage){
        return numberFormat.format(ticketPrice - (ticketPrice * percentage));
    }
}