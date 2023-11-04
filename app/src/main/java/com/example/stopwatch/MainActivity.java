package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView timerTv;
    private int milliseconds;
    private boolean running, wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            milliseconds = savedInstanceState.getInt("milliseconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    public void onStart(View view) {
        running = true;
    }

    public void onStop(View view) {
        running = false;
    }

    public void onReset(View view) {
        running = false;
        milliseconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("milliseconds", milliseconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    private void runTimer() {
        timerTv = findViewById(R.id.timerTv);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (milliseconds / 60000) % 60;
                int seconds = (milliseconds / 1000) % 60;
                int millis = milliseconds % 1000;

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, millis/10);
                timerTv.setText(time);

                if (running) {
                    milliseconds += 100;
                }

                handler.postDelayed(this, 100);
            }
        });
    }
}
