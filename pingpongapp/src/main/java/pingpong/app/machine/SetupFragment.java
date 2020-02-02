package pingpong.app.machine;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

public class SetupFragment extends Fragment  {
    //Class Variables
    public RelativeLayout setupBG;

    public RadioGroup speedTypes;

    public Button angleUpBtn;
    public Button angleRightBtn;
    public Button angleLeftBtn;
    public Button angleDownBtn;

    public RadioGroup timeTypes;
    public RadioGroup spinTypes;
    FirebaseAuth mAuth;
    public Switch onOff;
    int timeval=0;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String path = "Users/" + user.getUid() + "/Ball Configuration";

        final DatabaseReference BallConfig = database.getReference(path);
        final DatabaseReference speed = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Ball Speed");
        final DatabaseReference angleUp = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Up");
        final DatabaseReference angleRight = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Right");
        final DatabaseReference angleLeft = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Left");
        final DatabaseReference angleDown = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Down");
        final DatabaseReference start = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Start");
        final DatabaseReference stop = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Stop");
        final DatabaseReference timer = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Timer");
        final DatabaseReference spin = database.getReference("Users/" + user.getUid() + "/Ball Configuration /Spin");

        final View v = inflater.inflate(R.layout.setup_fragment, container, false);
        speedTypes = v.findViewById(R.id.speedChoices);

        angleUpBtn = v.findViewById(R.id.angleUp);
        angleRightBtn = v.findViewById(R.id.angleRight);
        angleLeftBtn = v.findViewById(R.id.angleLeft);
        angleDownBtn = v.findViewById(R.id.angleDown);

        timeTypes = v.findViewById(R.id.timeChoices);
        spinTypes = v.findViewById(R.id.spinChoices);

        setupBG = v.findViewById(R.id.setupBG); //Using this for debugging purposes, will give visual feedback for what angle button is pressed. Red = Left, Blue = Up, Green = Right, Yellow = Down.

        //Setting up default DB Values
        start.setValue("False");
        stop.setValue("True");
        speed.setValue("Slow");
        angleUp.setValue("0");
        angleRight.setValue("0");
        angleLeft.setValue("0");
        angleDown.setValue("0");
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
            }
        });

        angleUpBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    angleUp.setValue("1");
                    setupBG.setBackgroundColor(Color.BLUE); //Debugging purposes, will add animation later.
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    angleUp.setValue("0");
                    setupBG.setBackgroundColor(Color.WHITE); //Debugging purposes, will add animation later.
                }
                return true;
            }
        });

        angleRightBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    angleRight.setValue("1");
                    setupBG.setBackgroundColor(Color.GREEN); //Debugging purposes, will add animation later.
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    angleRight.setValue("0");
                    setupBG.setBackgroundColor(Color.WHITE); //Debugging purposes, will add animation later.
                }
                return true;
            }
        });

        angleLeftBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    angleLeft.setValue("1");
                    setupBG.setBackgroundColor(Color.RED); //Debugging purposes, will add animation later.
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    angleLeft.setValue("0");
                    setupBG.setBackgroundColor(Color.WHITE); //Debugging purposes, will add animation later.
                }
                return true;
            }
        });

        angleDownBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    angleDown.setValue("1");
                    setupBG.setBackgroundColor(Color.YELLOW); //Debugging purposes, will add animation later.
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    angleDown.setValue("0");
                    setupBG.setBackgroundColor(Color.WHITE); //Debugging purposes, will add animation later.
                }
                return true;
            }
        });

        /*angleHorizontalTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Horizontal Angle data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String horzRequest = chosenOption.getText().toString();
                horizontalAngle.setValue(horzRequest);
            }
        });

        angleVerticalTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Vertical Angle data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String vertRequest = chosenOption.getText().toString();
                verticalAngle.setValue(vertRequest);
            }
        });*/

        timeTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Time data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String timeRequest = chosenOption.getText().toString();
                timer.setValue(timeRequest);
            }
        });

        spinTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Spin type data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
                String spinRequest = chosenOption.getText().toString();
                spin.setValue(spinRequest);
            }
        });
        final TextView pgsBar = v.findViewById(R.id.progressBarinsideText);

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    new CountDownTimer(4000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            long countdown = millisUntilFinished/1000;
                            String timer = Long.toString(countdown);
                            String gamestart = getResources().getString(R.string.gamestart);
                            pgsBar.setText(gamestart +"\n"+timer);
                            fadeBackground.animate().alpha(.85f);
                            pgsBar.setVisibility(View.VISIBLE);
                            fadeBackground.setVisibility(View.VISIBLE);
                            if(timeval==1){
                                pgsBar.setVisibility(View.GONE);
                                fadeBackground.setVisibility(View.GONE);
                                this.cancel();
                                timeval=0;
                            }
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
                    timeval = 1;
                }
            }
        });

        return v;
    }
}
