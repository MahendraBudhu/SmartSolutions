package pingpong.app.machine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.machine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetupFragment extends Fragment {
    CountDownTimer mCountDownTimer;
    public RadioGroup speedTypes;
    public RadioButton speedOption1;
    public RadioButton speedOption2;
    public RadioButton speedOption3;

    public RadioGroup angleHorizontalTypes;
    public RadioButton horizOption1;
    public RadioButton horizOption2;
    public RadioButton horizOption3;

    public RadioGroup angleVerticalTypes;
    public RadioButton vertOption1;
    public RadioButton vertOption2;
    public RadioButton vertOption3;

    public RadioGroup timeTypes;
    public RadioButton timeOption1;
    public RadioButton timeOption2;
    public RadioButton timeOption3;

    public RadioGroup spinTypes;
    public RadioButton spinOption1;
    public RadioButton spinOption2;
    public RadioButton spinOption3;

    FirebaseAuth mAuth;
    public Switch onOff;
    int timeval=0;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String path = "Users/" + user.getUid() + "/Ball Configuration";
        final DatabaseReference BallConfig = database.getReference(path);
        final DatabaseReference speed = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Ball Speed");
        final DatabaseReference horizontalAngle = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Horizontal Angle");
        final DatabaseReference verticalAngle = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Vertical Angle");
        final DatabaseReference start = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Start");
        final DatabaseReference stop = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Stop");
        final DatabaseReference timer = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Timer");
        final DatabaseReference spin = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Spin");
        final View v = inflater.inflate(R.layout.setup_fragment, container, false);

        speedTypes = v.findViewById(R.id.speedChoices);
        speedOption1 = v.findViewById(R.id.speedType1);
        speedOption2 = v.findViewById(R.id.speedType2);
        speedOption3 = v.findViewById(R.id.speedType3);

        angleHorizontalTypes = v.findViewById(R.id.horzChoices);
        horizOption1 = v.findViewById(R.id.horzType1);
        horizOption2 = v.findViewById(R.id.horzType2);
        horizOption3 = v.findViewById(R.id.horzType3);

        angleVerticalTypes = v.findViewById(R.id.vertChoices);
        vertOption1 = v.findViewById(R.id.vertType1);
        vertOption2 = v.findViewById(R.id.vertType2);
        vertOption3 = v.findViewById(R.id.vertType3);

        timeTypes = v.findViewById(R.id.timeChoices);
        timeOption1 = v.findViewById(R.id.timeType1);
        timeOption2 = v.findViewById(R.id.timeType2);
        timeOption3 = v.findViewById(R.id.timeType3);

        spinTypes = v.findViewById(R.id.spinChoices);
        spinOption1 = v.findViewById(R.id.spinType1);
        spinOption2 = v.findViewById(R.id.spinType2);
        spinOption3 = v.findViewById(R.id.spinType3);

        //Setting up default DB Values
        start.setValue("False");
        stop.setValue("True");
        speed.setValue("Slow");
        horizontalAngle.setValue("Left");
        verticalAngle.setValue("Low");
        spin.setValue("Flat");
        timer.setValue("Slow");
//
        onOff = v.findViewById(R.id.onOff);
        final View fadeBackground = v.findViewById(R.id.fadeBackground);

        speedTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Speed data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String speedRequest = chosenOption.getText().toString();
                speed.setValue(speedRequest);
                switch(speedRequest){
                    case "Slow":    editor.putInt("speedState", 1);
                                    editor.commit();
                        break;
                    case "Medium":  editor.putInt("speedState", 2);
                                    editor.commit();
                        break;
                    case "Fast":    editor.putInt("speedState", 3);
                                    editor.commit();
                        break;
                }
            }
        });

        angleHorizontalTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Horizontal Angle data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String horzRequest = chosenOption.getText().toString();
                horizontalAngle.setValue(horzRequest);
                switch(horzRequest){
                    case "Left":    editor.putInt("horizState", 1);
                                    editor.commit();
                        break;
                    case "Center":  editor.putInt("horizState", 2);
                                    editor.commit();
                        break;
                    case "Right":    editor.putInt("horizState", 3);
                                    editor.commit();
                        break;
                }
            }
        });

        angleVerticalTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Vertical Angle data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String vertRequest = chosenOption.getText().toString();
                verticalAngle.setValue(vertRequest);
                switch(vertRequest){
                    case "Low":    editor.putInt("vertState", 1);
                                    editor.commit();
                        break;
                    case "Medium":  editor.putInt("vertState", 2);
                                    editor.commit();
                        break;
                    case "High":    editor.putInt("vertState", 3);
                                    editor.commit();
                        break;
                }
            }
        });

        timeTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Time data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String timeRequest = chosenOption.getText().toString();
                timer.setValue(timeRequest);
                switch(timeRequest){
                    case "Slow":    editor.putInt("timeState", 1);
                                    editor.commit();
                        break;
                    case "Medium":  editor.putInt("timeState", 2);
                                    editor.commit();
                        break;
                    case "Fast":    editor.putInt("timeState", 3);
                                    editor.commit();
                        break;
                }
            }
        });

        spinTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Spin type data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String spinRequest = chosenOption.getText().toString();
                spin.setValue(spinRequest);
                switch(spinRequest){
                    case "Flat":    editor.putInt("spinState", 1);
                                    editor.commit();
                        break;
                    case "Topspin":  editor.putInt("spinState", 2);
                                    editor.commit();
                        break;
                    case "Backspin":    editor.putInt("spinState", 3);
                                    editor.commit();
                        break;
                }
            }
        });


        int speedState = pref.getInt("speedState", 0);
        int horizState = pref.getInt("horizState", 0);
        int vertState = pref.getInt("vertState", 0);
        int timeState = pref.getInt("timeState", 0);
        int spinState = pref.getInt("spinState", 0);

        switch(speedState){
            case 1: speedOption1.setChecked(true);
                break;
            case 2: speedOption2.setChecked(true);
                break;
            case 3: speedOption3.setChecked(true);
                break;
        }

        switch(horizState){
            case 1: horizOption1.setChecked(true);
                break;
            case 2: horizOption2.setChecked(true);
                break;
            case 3: horizOption3.setChecked(true);
                break;
        }

        switch(vertState){
            case 1: vertOption1.setChecked(true);
                break;
            case 2: vertOption2.setChecked(true);
                break;
            case 3: vertOption3.setChecked(true);
                break;
        }

        switch(timeState){
            case 1: timeOption1.setChecked(true);
                break;
            case 2: timeOption2.setChecked(true);
                break;
            case 3: timeOption3.setChecked(true);
                break;
        }

        switch(spinState){
            case 1: spinOption1.setChecked(true);
                break;
            case 2: spinOption2.setChecked(true);
                break;
            case 3: spinOption3.setChecked(true);
                break;
        }

        final TextView pgsBar = v.findViewById(R.id.progressBarinsideText);

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                            start.setValue("True");
                            stop.setValue("False");
                         }
                    }.start();
                }
                else{
                    stop.setValue("True");
                    start.setValue("False");
                   // timeval = 1;
                    mCountDownTimer.cancel();
                    pgsBar.setVisibility(View.GONE);
                    fadeBackground.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }
}
