package com.example.pingpongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StatisticsScreen extends AppCompatActivity {
    String timePeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_screen);

        //All of the invisible text views.
        final TextView avgSpeedText = (TextView) findViewById(R.id.avgSpeed);
        final TextView avgSpeedValText = (TextView) findViewById(R.id.avgSpeedVal);

        final TextView minSpeedText = (TextView) findViewById(R.id.minSpeed);
        final TextView minSpeedValText = (TextView) findViewById(R.id.minSpeedVal);

        final TextView maxSpeedText = (TextView) findViewById(R.id.maxSpeed);
        final TextView maxSpeedValText = (TextView) findViewById(R.id.maxSpeedVal);

        final TextView accuracyText = (TextView) findViewById(R.id.accuracy);
        final TextView accuracyValText = (TextView) findViewById(R.id.accuracyVal);

        final TextView shotsTotalText = (TextView) findViewById(R.id.shotTotal);
        final TextView ballShotValText = (TextView) findViewById(R.id.ballShotVal);

        final TextView shotsHitText = (TextView) findViewById(R.id.shotsHit);
        final TextView ballHitValText = (TextView) findViewById(R.id.ballHitVal);

        final TextView shotsMissedText = (TextView) findViewById(R.id.shotsMissed);
        final TextView ballMissValText = (TextView) findViewById(R.id.ballMissVal);

        List<String> mSpinner = new ArrayList<String>();

        mSpinner.add(getApplicationContext().getString(R.string.selectTime));
        mSpinner.add(getApplicationContext().getString(R.string.daily)); //We would be pulling data from our server instead of hard coding the months in.
        mSpinner.add(getApplicationContext().getString(R.string.monthly));
        mSpinner.add(getApplicationContext().getString(R.string.yearly));
        mSpinner.add(getApplicationContext().getString(R.string.alltime));

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinner);

        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner userHistory = (Spinner) findViewById(R.id.spinner);
        userHistory.setAdapter(mAdapter);

        userHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adapterView, View view, int num, long id){
                timePeriod = adapterView.getItemAtPosition(num).toString();
                if(!(timePeriod.equalsIgnoreCase(getApplicationContext().getString(R.string.selectTime)))){
                   avgSpeedText.setVisibility(View.VISIBLE);
                   avgSpeedValText.setVisibility(View.VISIBLE);
                   minSpeedText.setVisibility(View.VISIBLE);
                   minSpeedValText.setVisibility(View.VISIBLE);
                   maxSpeedText.setVisibility(View.VISIBLE);
                   maxSpeedValText.setVisibility(View.VISIBLE);
                   accuracyText.setVisibility(View.VISIBLE);
                   accuracyValText.setVisibility(View.VISIBLE);
                   shotsTotalText.setVisibility(View.VISIBLE);
                   ballShotValText.setVisibility(View.VISIBLE);
                   shotsHitText.setVisibility(View.VISIBLE);
                   ballHitValText.setVisibility(View.VISIBLE);
                   shotsMissedText.setVisibility(View.VISIBLE);
                   ballMissValText.setVisibility(View.VISIBLE);
                   userData(timePeriod);
                }
               else{
                   avgSpeedText.setVisibility(View.INVISIBLE);
                   avgSpeedValText.setVisibility(View.INVISIBLE);
                   minSpeedText.setVisibility(View.INVISIBLE);
                   minSpeedValText.setVisibility(View.INVISIBLE);
                   maxSpeedText.setVisibility(View.INVISIBLE);
                   maxSpeedValText.setVisibility(View.INVISIBLE);
                   accuracyText.setVisibility(View.INVISIBLE);
                   accuracyValText.setVisibility(View.INVISIBLE);
                   shotsTotalText.setVisibility(View.INVISIBLE);
                   ballShotValText.setVisibility(View.INVISIBLE);
                   shotsHitText.setVisibility(View.INVISIBLE);
                   ballHitValText.setVisibility(View.INVISIBLE);
                   shotsMissedText.setVisibility(View.INVISIBLE);
                   ballMissValText.setVisibility(View.INVISIBLE);
               }
            }
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });
    }

    public void userData(String userTime){
        final String dailyStringResource = getResources().getString(R.string.daily);
        final String monthlyStringResource = getResources().getString(R.string.monthly);
        final String yearlyStringResource = getResources().getString(R.string.yearly);
        final String allTimeStringResource = getResources().getString(R.string.alltime);
        if(userTime.equalsIgnoreCase((dailyStringResource))) {//This is how we'll pull data for a specific month for our user
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toastDaily), Toast.LENGTH_LONG).show();
        }
        else if(userTime.equalsIgnoreCase((monthlyStringResource))) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toastMonthly), Toast.LENGTH_LONG).show();
        }
        else if(userTime.equalsIgnoreCase((yearlyStringResource))) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toastYearly), Toast.LENGTH_LONG).show();
        }
        else if(userTime.equalsIgnoreCase((allTimeStringResource))) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.toastAlltime), Toast.LENGTH_LONG).show();
        }
    }

}
