package pingpong.app.machine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
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
    public Button randStartBtn;
    public Button randStopBtn;
    public RadioGroup randTimeOptions;
    public String rstop;
    public int timeVal;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.random_fragment, container, false);

        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference BallConfig = database.getReference("Users/" + user.getUid() + "/Ball Configuration");
        final DatabaseReference speed = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Ball Speed");
        final DatabaseReference horizontalAngle = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Horizontal Angle");
        final DatabaseReference verticalAngle = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Vertical Angle");
        final DatabaseReference start = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Start");
        final DatabaseReference stop = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Stop");
        final DatabaseReference timer = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Timer");
        final DatabaseReference spin = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Spin");

        randStartBtn = v.findViewById(R.id.randStartBtn);
        randStopBtn = v.findViewById(R.id.randStopBtn);
        randTimeOptions = v.findViewById(R.id.randTimeChoices);

        randTimeOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //Spin type data for database
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosenOption = group.findViewById(checkedId);
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

        randStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStartButtonToast), Toast.LENGTH_SHORT).show();
                t = new Thread() {
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
                        } catch(InterruptedException e) {
                            //Error check
                        }
                    }
                };
                t.start();
            }
        });


        randStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStopButtonToast), Toast.LENGTH_SHORT).show();
                stop.setValue("True");
                start.setValue("False");
                rstop = "false";
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseUser user = mAuth.getCurrentUser();

        final DatabaseReference start = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Start");
        final DatabaseReference stop = database.getReference("Users/" + user.getUid() +"/Ball Configuration /Stop");
        t.interrupt();
        stop.setValue("True");
        start.setValue("False");
    }
}
