package pingpong.app.machine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.machine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RandomFragment extends Fragment {
    FirebaseAuth mAuth;

    public Thread t;
    public Switch onOffRandom;

    public RadioGroup randTimeOptions;
    public RadioButton randTime1;
    public RadioButton randTime2;
    public RadioButton randTime3;
    CountDownTimer mCountDownTimer;

    public String rstop;

    public int timeVal;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    volatile boolean activityStopped = false;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.random_fragment, container, false);

        SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0);

        final SharedPreferences.Editor editor = pref.edit();

        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference BallConfig = database.getReference("Users/" + user.getUid() + "/Ball Configuration");
        final DatabaseReference speed = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Ball Speed");
        final DatabaseReference horizontalAngle = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Horizontal Angle");
        final DatabaseReference verticalAngle = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Vertical Angle");
        final DatabaseReference start = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Start");
        final DatabaseReference stop = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Stop");
        final DatabaseReference timer = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Timer");
        final DatabaseReference spin = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Spin");

        onOffRandom = v.findViewById(R.id.onOffRandom);
        randTimeOptions = v.findViewById(R.id.randTimeChoices);

        randTime1 = v.findViewById(R.id.randTimeType1);
        randTime2 = v.findViewById(R.id.randTimeType2);
        randTime3 = v.findViewById(R.id.randTimeType3);


        final TextView pgsBar = v.findViewById(R.id.progressBarinsideText);

        timeVal = 6;

        randTimeOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Spin type data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String randTimeRequest = chosenOption.getText().toString();
                if(randTimeRequest.equalsIgnoreCase("Slow")) {
                    timeVal = 6;
                    timer.setValue("Slow");
                    editor.putInt("randTimerState", 1);
                    editor.commit();
                }
                else if(randTimeRequest.equalsIgnoreCase("Medium")) {
                    timeVal = 4;
                    timer.setValue("Medium");
                    editor.putInt("randTimerState", 2);
                    editor.commit();
                }
                else if(randTimeRequest.equalsIgnoreCase("Fast")) {
                    timeVal = 2;
                    timer.setValue("Fast");
                    editor.putInt("randTimerState", 3);
                    editor.commit();
                }
            }
        });

        int lastRandTimerState = pref.getInt("randTimerState", 0);

        if(lastRandTimerState == 1){
            randTime1.setChecked(true);
        }
        else if(lastRandTimerState == 2){
            randTime2.setChecked(true);
        }
        else if(lastRandTimerState == 3){
            randTime3.setChecked(true);
        }
        final View fadeBackground = v.findViewById(R.id.fadeBackground);
        onOffRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mCountDownTimer = new CountDownTimer(4000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            long countdown = millisUntilFinished/1000;
                            String timer = Long.toString(countdown);
                            String gamestart = getResources().getString(R.string.gamestart);
                            pgsBar.setText(gamestart +"\n"+timer);
                            fadeBackground.animate().alpha(.85f);
                            pgsBar.setVisibility(View.VISIBLE);
                            fadeBackground.setVisibility(View.VISIBLE);
                        }

                        public void onFinish() {
                            pgsBar.setVisibility(View.GONE);
                            fadeBackground.setVisibility(View.GONE);
                            Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStartButtonToast), Toast.LENGTH_SHORT).show();
                            new Thread(new Runnable() {
                                public void run() {
                                    try{

                                        start.setValue("True");
                                        stop.setValue("False");
                                        while (!activityStopped) {
                                            int speedVal = (int) (Math.random() * 101);
                                            int hangle = (int) (Math.random() * 101);
                                            int vangle = (int) (Math.random() * 101);
                                            int spinChoice = (int) (Math.random() * 101);

                                            //Random Speeds
                                            if(speedVal < 34){
                                                speed.setValue("Slow");
                                            }
                                            else if(speedVal >= 34 && speedVal < 67){
                                                speed.setValue("Medium");
                                            }
                                            else if(speedVal >= 67){
                                                speed.setValue("Fast");
                                            }

                                            //Random Horizontal Angle
                                            if(hangle < 34){
                                                horizontalAngle.setValue("Left");
                                            }
                                            else if(hangle >= 34 && hangle < 67){
                                                horizontalAngle.setValue("Center");
                                            }
                                            else if(hangle >= 67){
                                                horizontalAngle.setValue("Right");
                                            }

                                            //Random Vertical Angle
                                            if(vangle < 34){
                                                verticalAngle.setValue("Low");
                                            }
                                            else if(vangle >= 34 && vangle < 67){
                                                verticalAngle.setValue("Medium");
                                            }
                                            else if(vangle >= 67){
                                                verticalAngle.setValue("High");
                                            }

                                            //Random Spin
                                            if(spinChoice < 34){
                                                spin.setValue("Flat");
                                            }
                                            else if(spinChoice >= 34 && spinChoice < 67){
                                                spin.setValue("Topspin");
                                            }
                                            else if(spinChoice >= 67){
                                                spin.setValue("Backspin");
                                            }
                                            t.sleep(timeVal*1000);
                                            if (rstop == "false") {
                                                start.setValue("False");
                                                stop.setValue("True");
                                                rstop = "";
                                                break;
                                            }
                                        }
                                    } catch(InterruptedException e) {
                                        //Error check
                                    }
                                }
                            }).start();
                        }
                    }.start();

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStopButtonToast), Toast.LENGTH_SHORT).show();
                    stop.setValue("True");
                    start.setValue("False");
                    rstop = "false";
                    mCountDownTimer.cancel();
                    pgsBar.setVisibility(View.GONE);
                    fadeBackground.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseUser user = mAuth.getCurrentUser();

        activityStopped = true;

        final DatabaseReference start = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Start");
        final DatabaseReference stop = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Stop");

        stop.setValue("True");
        start.setValue("False");
    }
}
