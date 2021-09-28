package com.example.group20_inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.group20_inclass06.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Assignment: In Class Assignment #06
 * File Name: Group20_InClass06
 * Student Name: Jared Tamulynas
 * Student Name: Myat Win
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ExecutorService threadPool;
    Handler handler;

    // ListView initializers
    ArrayList<Double> numbers = new ArrayList<>();
    ArrayAdapter<Double> adapter;

    double average;
    StringBuilder progressText;
    int complexity = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Main Activity");

        // Listview
        adapter = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, android.R.id.text1, numbers);
        binding.listView.setAdapter(adapter);

        // Seekbar to select complexity
        binding.textViewComplexity.setText(complexity + " Times");

        binding.seekBarComplexity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progressText = new StringBuilder(String.valueOf(progress));

                if(progress == 1) {
                    progressText.append(" Time");
                } else {
                    progressText.append(" Times");
                }

                binding.textViewComplexity.setText(progressText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // not used
            }
        });

        // Generate button
        binding.buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complexity = binding.seekBarComplexity.getProgress();

                binding.progressBar.setProgress(0);
                binding.textViewGeneratedNumbers.setText("");
                average = 0;
                binding.textViewAverage.setText(String.valueOf(average));
                numbers.clear();
                adapter.notifyDataSetChanged();

                if(complexity > 0) {
                    binding.resultsView.setVisibility(View.VISIBLE);

                    // Create a thread pool of size 2
                    threadPool = Executors.newFixedThreadPool(2);
                    threadPool.execute(new GetNumber(complexity));
                } else {
                    Toast.makeText(MainActivity.this, "Complexity should be 1 or above.", Toast.LENGTH_SHORT);
                }


            }
        });

        // Thread communication using handler
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch(message.what) {
                    case GetNumber.STATUS_START:
                        binding.buttonGenerate.setEnabled(false);
                        binding.seekBarComplexity.setEnabled(false);
                        break;
                    case GetNumber.STATUS_STOP:
                        binding.buttonGenerate.setEnabled(true);
                        binding.seekBarComplexity.setEnabled(true);
                        break;
                    case GetNumber.STATUS_PROGRESS:
                        double number = message.getData().getDouble(GetNumber.NUMBER_KEY);
                        int progress = message.getData().getInt(GetNumber.PROGRESS_KEY);

                        binding.progressBar.setMax(complexity);
                        binding.progressBar.setProgress(progress);

                        binding.textViewGeneratedNumbers.setText(progress + "/" + complexity);

                        numbers.add(number);
                        adapter.notifyDataSetChanged();

                        double average = calculateAverage();
                        binding.textViewAverage.setText(String.valueOf(average));
                        break;
                }

                return false;
            }
        });



    }

    // Calculates the average
    double calculateAverage() {
        double sum = 0;
        int size = 1;

        for(int i = 0; i < numbers.size(); i++) {
            sum = sum + numbers.get(i);
        }

        size = numbers.size();
        return sum / size;
    }


    class GetNumber implements Runnable {
        int complexity;
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String NUMBER_KEY = "NUMBER_KEY";
        static final String PROGRESS_KEY = "PROGRESS_KEY";

        public GetNumber(int complexity) {
            this.complexity = complexity;
        }

        @Override
        public void run() {
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);

            for(int i = 1; i <= complexity; i++) {
                double number = HeavyWork.getNumber();

                Message progressMessage = new Message();
                progressMessage.what = STATUS_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putDouble(NUMBER_KEY, (Double)number);
                bundle.putInt(PROGRESS_KEY, (Integer)i);
                progressMessage.setData(bundle);
                handler.sendMessage(progressMessage);
            }

            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);

        }
    }

}