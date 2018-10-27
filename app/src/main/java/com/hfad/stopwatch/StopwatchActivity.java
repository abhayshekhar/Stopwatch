package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import java.util.Locale;
import android.os.Handler;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    // number of seconds displayed on the stopwatch
    private int seconds =0;
    //is the stopwatch running?
    private boolean running;
    //was stopwatch running before
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning=running;
        running=false;
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }
    //start the stopwatch running when the start button is clicked.
    public void onClickStart(View view){
        running =true;
    }
    // stop the stopwatch when the reset button is clicked.
    public void onClickStop(View view){
        running=false;
    }
    //reset the stopwatch when the reset button is clicked.
    public void onClickReset(View view){
        running=false;
        seconds=0;
    }

    //sets the number ofseconds on the timer
    private void runTimer(){
        final TextView timeView=(TextView)findViewById(R.id.time_view);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;

                String time =String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if (running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

}
