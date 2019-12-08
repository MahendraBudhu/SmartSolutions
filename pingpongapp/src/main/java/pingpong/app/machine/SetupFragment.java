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

    public RadioGroup speedTypes;
    public RadioGroup angleHorizontalTypes;
    public RadioGroup angleVerticalTypes;
    public RadioGroup timeTypes;
    public RadioGroup spinTypes;

    public TextView speedVal;
    public TextView angleHorizontalVal;
    public TextView angleVerticalVal;
    public TextView timeVal;


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
        speedTypes = (RadioGroup) v.findViewById(R.id.speedChoices);
        angleHorizontalTypes = (RadioGroup) v.findViewById(R.id.horzChoices);
        angleVerticalTypes = (RadioGroup) v.findViewById(R.id.vertChoices);
        timeTypes = (RadioGroup) v.findViewById(R.id.timeChoices);
        spinTypes = (RadioGroup) v.findViewById(R.id.spinChoices);

        //Setting up default DB Values
        start.setValue("False");
        stop.setValue("True");
        speed.setValue("Slow");
        horizontalAngle.setValue("Left");
        verticalAngle.setValue("Low");
        spin.setValue("Flat");
        timer.setValue("Slow");

        onOff = (Switch) v.findViewById(R.id.onOff);


        speedTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Speed data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = (RadioButton)group.findViewById(checkedId);
                String speedRequest = chosenOption.getText().toString();
                speed.setValue(speedRequest);
            }
        });

        angleHorizontalTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Horizontal Angle data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = (RadioButton)group.findViewById(checkedId);
                String horzRequest = chosenOption.getText().toString();
                horizontalAngle.setValue(horzRequest);
            }
        });

        angleVerticalTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Vertical Angle data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = (RadioButton)group.findViewById(checkedId);
                String vertRequest = chosenOption.getText().toString();
                verticalAngle.setValue(vertRequest);
            }
        });

        timeTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Time data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = (RadioButton)group.findViewById(checkedId);
                String timeRequest = chosenOption.getText().toString();
                timer.setValue(timeRequest);
            }
        });

        spinTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Spin type data for database
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
