package com.example.pingpongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SetupScreen extends AppCompatActivity {

    public SeekBar speedBar;
    public SeekBar angleHorizontalBar;
    public SeekBar angleVerticalBar;

    public TextView speedVal;
    public TextView angleHorizontalVal;
    public TextView angleVerticalVal;

    SeekBar.OnSeekBarChangeListener sbListener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen);

        speedBar = (SeekBar) findViewById(R.id.speedControl);
        speedVal = (TextView) findViewById(R.id.speedNum);

        angleHorizontalBar = (SeekBar) findViewById(R.id.angleHorizontalControl);
        angleHorizontalVal = (TextView) findViewById(R.id.angleHorizontalNum);

        angleVerticalBar = (SeekBar) findViewById(R.id.angleVerticalControl);
        angleVerticalVal = (TextView) findViewById(R.id.angleVerticalNum);

        sbListener = new SeekBar.OnSeekBarChangeListener(){ //sbListener is used to determine which SeekBar is being changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch(seekBar.getId()){
                    case R.id.speedControl: speedVal.setText("" + progress + "");
                    break;
                    case R.id.angleHorizontalControl: progress-=6;
                    angleHorizontalVal.setText("" + progress + "");
                    break;
                    case R.id.angleVerticalControl: progress-=3;
                    angleVerticalVal.setText("" + progress + "");
                    break;

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        angleVerticalBar.setOnTouchListener(new SeekBar.OnTouchListener(){ //Used to make vertical bar draggable
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int userAction = event.getAction();
                switch(userAction){
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }

                view.onTouchEvent(event);
                return true;
            }
        });

        speedBar.setOnSeekBarChangeListener(sbListener);
        angleHorizontalBar.setOnSeekBarChangeListener(sbListener);
        angleVerticalBar.setOnSeekBarChangeListener(sbListener);
    }

    public void start(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.startButtonToast), Toast.LENGTH_LONG).show();
        //Code to start machine
    }

    public void stop(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.stopButtonToast), Toast.LENGTH_LONG).show();
        //Code to stop machine
    }
}
