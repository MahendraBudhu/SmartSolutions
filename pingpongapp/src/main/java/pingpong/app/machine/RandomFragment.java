package pingpong.app.machine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.machine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RandomFragment extends Fragment {

    public Button randStart;
    public Button randStop;

    public SeekBar randTimeBar;
    public TextView randTimeVal;

    SeekBar.OnSeekBarChangeListener sbListener;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference BallConfig = database.getReference("Ball Configuration");
    DatabaseReference speed= database.getReference("Ball Configuration /Ball Speed");
    DatabaseReference horizontalAngle = database.getReference("Ball Configuration /Horizontal Angle");
    DatabaseReference verticalAngle = database.getReference("Ball Configuration /Vertical Angle");
    DatabaseReference start = database.getReference("Ball Configuration /Start");
    DatabaseReference stop = database.getReference("Ball Configuration /Stop");
    DatabaseReference timer = database.getReference("Ball Configuration /Timer");

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.random_fragment, container, false);

        randStart = (Button) v.findViewById(R.id.randomStart);
        randStop = (Button) v.findViewById(R.id.randomStop);
        randTimeBar = (SeekBar) v.findViewById(R.id.randTimerControl);
        randTimeVal = (TextView) v.findViewById(R.id.randTimerValue);

        randStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStartButtonToast), Toast.LENGTH_LONG).show();
                start.setValue("True");
                stop.setValue("False");
                while(start.getValue().equalsIgnoreCase("True")) {
                    int randval = (int) (Math.random() * 61);
                    int hangle = (int) (Math.random() * 13) - 6;
                    int vangle = (int) (Math.random() * 7) - 3;
                    int timer = (int) (Math.random() * 6) + 2;
                    speed.setValue(randval);
                    horizontalAngle.setValue(hangle);
                    verticalAngle.setValue(vangle);
                    verticalAngle.setValue(timer);
                }
            }
        });

        randStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.randStopButtonToast), Toast.LENGTH_LONG).show();
                stop.setValue("True");
                start.setValue("False");
            }
        });

        sbListener = new SeekBar.OnSeekBarChangeListener(){ //sbListener is used to determine which SeekBar is being changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch(seekBar.getId()){
                    case R.id.randTimerControl: progress = progress + 2;
                        randTimeVal.setText("" + progress + "");
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

        randTimeBar.setOnSeekBarChangeListener(sbListener);

        return v;
    }
}
