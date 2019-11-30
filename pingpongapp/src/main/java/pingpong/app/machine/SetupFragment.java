package pingpong.app.machine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.machine.R;

public class SetupFragment extends Fragment {

    public SeekBar speedBar;
    public SeekBar angleHorizontalBar;
    public SeekBar angleVerticalBar;
    public SeekBar timeBar;

    public TextView speedVal;
    public TextView angleHorizontalVal;
    public TextView angleVerticalVal;
    public TextView timeVal;

    public RadioGroup spinTypes;

    public Switch onOff;

    SeekBar.OnSeekBarChangeListener sbListener;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference BallConfig = database.getReference("Ball Configuration");
    DatabaseReference speed= database.getReference("Ball Configuration /Ball Speed");
    DatabaseReference horizontalAngle = database.getReference("Ball Configuration /Horizontal Angle");
    DatabaseReference verticalAngle = database.getReference("Ball Configuration /Vertical Angle");
    DatabaseReference start = database.getReference("Ball Configuration /Start");
    DatabaseReference stop = database.getReference("Ball Configuration /Stop");
    DatabaseReference timer = database.getReference("Ball Configuration /Timer");
    DatabaseReference spin = database.getReference("Ball Configuration /Spin");


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setup_fragment, container, false);
        speedBar = (SeekBar) v.findViewById(R.id.speedControl);
        speedVal = (TextView) v.findViewById(R.id.speedNum);

        angleHorizontalBar = (SeekBar) v.findViewById(R.id.angleHorizontalControl);
        angleHorizontalVal = (TextView) v.findViewById(R.id.angleHorizontalNum);

        angleVerticalBar = (SeekBar) v.findViewById(R.id.angleVerticalControl);
        angleVerticalVal = (TextView) v.findViewById(R.id.angleVerticalNum);

        timeBar = (SeekBar) v.findViewById(R.id.timerControl);
        timeVal = (TextView) v.findViewById(R.id.timerVal);

        spinTypes = (RadioGroup) v.findViewById(R.id.spinChoices);

        //Setting up default DB Values
        start.setValue("False");
        stop.setValue("True");
        speed.setValue("0");
        horizontalAngle.setValue("0");
        verticalAngle.setValue("0");
        spin.setValue("Flat");

        onOff = (Switch) v.findViewById(R.id.onOff);

        sbListener = new SeekBar.OnSeekBarChangeListener(){ //sbListener is used to determine which SeekBar is being changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch(seekBar.getId()){
                    case R.id.speedControl: speedVal.setText("" + progress + "");
                        speed.setValue(progress);
                        break;
                    case R.id.angleHorizontalControl: progress-=6;
                        angleHorizontalVal.setText("" + progress + "");
                        horizontalAngle.setValue(progress);
                        break;
                    case R.id.angleVerticalControl: progress-=3;
                        angleVerticalVal.setText("" + progress + "");
                        verticalAngle.setValue(progress);
                        break;
                    case R.id.timerControl: progress = progress + 2;
                        timeVal.setText("" + progress + "");
                        timer.setValue(progress);
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

        speedBar.setOnSeekBarChangeListener(sbListener);
        angleHorizontalBar.setOnSeekBarChangeListener(sbListener);
        angleVerticalBar.setOnSeekBarChangeListener(sbListener);
        timeBar.setOnSeekBarChangeListener(sbListener);

        spinTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = (RadioButton)group.findViewById(checkedId);
                String spinRequest = chosenOption.getText().toString();
                spin.setValue(spinRequest);
            }
        });


        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.startButtonToast), Toast.LENGTH_LONG).show();
                    start.setValue("True");
                    stop.setValue("False");
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.stopButtonToast), Toast.LENGTH_LONG).show();
                    stop.setValue("True");
                    start.setValue("False");
                }
            }
        });

        return v;
    }
}
