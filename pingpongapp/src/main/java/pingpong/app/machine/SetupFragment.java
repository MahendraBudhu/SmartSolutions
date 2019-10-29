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

    public Button stopBtn;
    public Button startBtn;

    SeekBar.OnSeekBarChangeListener sbListener;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference BallConfig = database.getReference("Ball Configuration");
    DatabaseReference speed= database.getReference("Ball Configuration /Ball Speed");
    DatabaseReference horizontalAngle = database.getReference("Ball Configuration /Horizontal Angle");
    DatabaseReference verticalAngle = database.getReference("Ball Configuration /Vertical Angle");
    DatabaseReference start = database.getReference("Ball Configuration /Start");
    DatabaseReference stop = database.getReference("Ball Configuration /Stop");


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

        //Setting up default DB Values
        start.setValue("False");
        stop.setValue("False");
        speed.setValue("0");
        horizontalAngle.setValue("0");
        verticalAngle.setValue("0");

        stopBtn = (Button) v.findViewById(R.id.stopBtn);
        startBtn = (Button) v.findViewById(R.id.startBtn);

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
                        //timeVal.setValue(progress);
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
        timeBar.setOnSeekBarChangeListener(sbListener);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.startButtonToast), Toast.LENGTH_LONG).show();
                start.setValue("True");
                stop.setValue("False");
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.stopButtonToast), Toast.LENGTH_LONG).show();
                stop.setValue("True");
                start.setValue("False");
            }
        });



        return v;
    }
}
