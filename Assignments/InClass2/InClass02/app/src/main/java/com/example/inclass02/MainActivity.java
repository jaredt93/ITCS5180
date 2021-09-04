package com.example.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ticketPriceEntry = findViewById(R.id.ticketPriceEntry);
        TextView discountedPriceResult = (TextView) findViewById(R.id.discountedPriceResult);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                double ticketPrice = Double.parseDouble(ticketPriceEntry.getText().toString());

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
            }
        });

    }

    // Calculates the discount and converts to a String
    public String discount(double ticketPrice, double percentage){
        double result = ticketPrice - (ticketPrice * percentage);
        return String.valueOf(result);
    }
}