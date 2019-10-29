package pingpong.app.machine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetupScreen extends AppCompatActivity {

    public SeekBar speedBar;
    public SeekBar angleHorizontalBar;
    public SeekBar angleVerticalBar;

    public TextView speedVal;
    public TextView angleHorizontalVal;
    public TextView angleVerticalVal;

    SeekBar.OnSeekBarChangeListener sbListener;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference BallConfig = database.getReference("Ball Configuration");
    DatabaseReference speed= database.getReference("Ball Configuration /Ball Speed");
    DatabaseReference horizontalAngle = database.getReference("Ball Configuration /Horizontal Angle");
    DatabaseReference verticalAngle = database.getReference("Ball Configuration /Vertical Angle");
    DatabaseReference start = database.getReference("Ball Configuration /Start");
    DatabaseReference stop = database.getReference("Ball Configuration /Stop");

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen);

        speedBar =  findViewById(R.id.speedControl);
        speedVal =  findViewById(R.id.speedNum);

        angleHorizontalBar =  findViewById(R.id.angleHorizontalControl);
        angleHorizontalVal =  findViewById(R.id.angleHorizontalNum);

        angleVerticalBar =  findViewById(R.id.angleVerticalControl);
        angleVerticalVal =  findViewById(R.id.angleVerticalNum);

        //Setting up default DB Values
        start.setValue("False");
        stop.setValue("False");
        speed.setValue("0");
        horizontalAngle.setValue("0");
        verticalAngle.setValue("0");


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
    }

    public void start(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.startButtonToast), Toast.LENGTH_SHORT).show();
        start.setValue("True");
        stop.setValue("False");

    }

    public void stop(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.stopButtonToast), Toast.LENGTH_SHORT).show();
        stop.setValue("True");
        start.setValue("False");
    }
}
