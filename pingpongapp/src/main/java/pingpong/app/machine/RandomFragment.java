package pingpong.app.machine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RandomFragment extends Fragment {

    public Switch randSwitch;
    public RadioGroup randTimeOptions;
    public String  rstop;

    int timeVal = 0;

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
        View v = inflater.inflate(R.layout.random_fragment, container, false);

        randSwitch = (Switch) v.findViewById(R.id.onOffRandom);
        randTimeOptions = (RadioGroup) v.findViewById(R.id.randTimeChoices);

        randTimeOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Spin type data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = (RadioButton)group.findViewById(checkedId);
                String randTimeRequest = chosenOption.getText().toString();
                if(randTimeRequest.equalsIgnoreCase("Slow")) {
                    timeVal = 6;
                }
                else if(randTimeRequest.equalsIgnoreCase("Medium")) {
                    timeVal = 4;
                }
                else if(randTimeRequest.equalsIgnoreCase("Fast")) {
                    timeVal = 2;
                }
            }
        });

        randSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStartButtonToast), Toast.LENGTH_SHORT).show();
                    Thread t = new Thread() {
                        public void run() {
                            try{
                                start.setValue("True");
                                stop.setValue("False");
                                while (true) {
                                    int speedVal = (int) (Math.random() * 101);
                                    int hangle = (int) (Math.random() * 101);
                                    int vangle = (int) (Math.random() * 101);
                                    int spinChoice = (int) (Math.random() * 101);

                                    //Random Speed
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

                                    TimeUnit.SECONDS.sleep(timeVal);
                                    if (rstop == "false") {
                                        start.setValue("False");
                                        stop.setValue("True");
                                        rstop = "";
                                        break;
                                    }
                                }
                            } catch(
                                    InterruptedException e)

                            {
                                //Error check
                            }
                        }
                    };
                    t.start();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStopButtonToast), Toast.LENGTH_SHORT).show();
                    stop.setValue("True");
                    start.setValue("False");
                    rstop = "false";
                }
            }
        });

        return v;
    }
}
