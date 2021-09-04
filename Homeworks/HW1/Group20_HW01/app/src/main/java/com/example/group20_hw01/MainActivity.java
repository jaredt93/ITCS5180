package com.example.group20_hw01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

/*
 * Assignment: Homework #01
 * File Name: Group20_HW01
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity {

    final static String TAG = "HW01";

    // Declare variables
    EditText billTotalEntry;
    RadioGroup radioGroupTip;
    SeekBar seekBarCustom;
    TextView customText;
    TextView tip;
    TextView total;
    RadioGroup radioGroupSplitBy;
    TextView totalPerPerson;
    Button buttonClear;
    DecimalFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findViewById initializers
        billTotalEntry = findViewById(R.id.billTotalEntry);
        radioGroupTip = findViewById(R.id.radioGroupTip);
        seekBarCustom = findViewById(R.id.seekBarCustom);
        customText = findViewById(R.id.customText);
        tip = findViewById(R.id.tip);
        total = findViewById(R.id.total);
        radioGroupSplitBy = findViewById(R.id.radioGroupSplitBy);
        totalPerPerson = findViewById(R.id.totalPerPerson);
        buttonClear = findViewById(R.id.buttonClear);
        numberFormat = new DecimalFormat("#.00");

        // billTotalEntry listener
        billTotalEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not used
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateUI(null);
            }
        });

        // radioGroupTip listener
        radioGroupTip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                updateUI(null);
            }
        });

        // seekBarCustom listener
        seekBarCustom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                StringBuilder progressText = new StringBuilder(String.valueOf(progress));
                progressText.append("%");
                customText.setText(progressText);
                updateUI(null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //not used
            }
        });

        // radioGroupSplitBy listener
        radioGroupSplitBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                updateUI(null);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources res = getResources();

                billTotalEntry.setText(null);
                radioGroupTip.check(R.id.radioGroupTipButton10);
                seekBarCustom.setProgress(40);
                tip.setText(res.getString(R.string.tip));
                total.setText(res.getString(R.string.total));
                radioGroupSplitBy.check(R.id.radioGroupSplitByButtonOne);
                totalPerPerson.setText(res.getString(R.string.totalPerPerson));
            }
        });

    }

    /*
     * Updates all UI elements to reflect any change.
     */
    public void updateUI(View view) {
        int radioButton = radioGroupTip.getCheckedRadioButtonId();
        double tipPercentage = checkTipId(radioButton);
        double billTotal = 0;

        try {
            billTotal = Double.parseDouble(billTotalEntry.getText().toString());
        } catch (Exception e) {
            Log.d(TAG, "updateUI: Invalid bill total.");
        }

        double tipAmount = calculateTip(billTotal, tipPercentage);

        Resources res = getResources();

        if(tipAmount == 0) {
            tip.setText(res.getString(R.string.tip));
        } else {
            StringBuilder tipText = new StringBuilder("$");
            tipText.append(numberFormat.format(tipAmount));
            tip.setText(tipText);
        }

        double totalPlusTip = calculateTotal(billTotal, tipAmount);

        if(totalPlusTip == 0) {
            total.setText(res.getString(R.string.total));
        } else {
            StringBuilder totalText = new StringBuilder("$");
            totalText.append(numberFormat.format(totalPlusTip));
            total.setText(totalText);
        }

        radioButton = radioGroupSplitBy.getCheckedRadioButtonId();
        int splitBy = checkSplitById(radioButton);
        double totalPerPersonValue = calculateTotalPerPerson(totalPlusTip, splitBy);

        if(totalPerPersonValue == 0) {
            totalPerPerson.setText(res.getString(R.string.totalPerPerson));
        } else {
            StringBuilder totalPerPersonText = new StringBuilder("$");
            totalPerPersonText.append(numberFormat.format(totalPerPersonValue));
            totalPerPerson.setText(totalPerPersonText);
        }
    }

    /*
     * Returns a tip percentage based on checkID value.
     */
    public double checkTipId(int checkedId) {
        SeekBar seekBarCustom = findViewById(R.id.seekBarCustom);
        double tipPercentage = .10;

        if (checkedId == R.id.radioGroupTipButton15) {
            tipPercentage = .15;
        } else if (checkedId == R.id.radioGroupTipButton18) {
            tipPercentage = .18;
        } else if (checkedId == R.id.radioGroupTipButtonCustom) {
            tipPercentage = (double)seekBarCustom.getProgress() / 100;
        }

        return tipPercentage;
    }

    /*
     * Returns an integer to split check by based on checkID value.
     */
    public int checkSplitById(int checkedId) {
        int splitBy = 1;

        if (checkedId == R.id.radioGroupSplitByButtonTwo) {
            splitBy = 2;
        } else if (checkedId == R.id.radioGroupSplitByButtonThree) {
            splitBy = 3;
        } else if (checkedId == R.id.radioGroupSplitByButtonFour) {
            splitBy = 4;
        }

        return splitBy;
    }

    /*
     * Calculates tip amount and returns the value as a string.
     */
    public double calculateTip(double billTotal, double tipPercentage) {
        return billTotal * tipPercentage;
    }

    /*
     * Calculates total amount after including a tip and returns the value as a string.
     */
    public double calculateTotal(double billTotal, double tipAmount) {
        return billTotal + tipAmount;
    }

    /*
     * Calculates total amount per person and returns the value as a string.
     */
    public double calculateTotalPerPerson(double total, int splitBy) {
        return total / splitBy;
    }

}