package pingpong.app.machine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.machine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RandomScreen extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference BallConfig = database.getReference("Ball Configuration");
    DatabaseReference speed= database.getReference("Ball Configuration /Ball Speed");
    DatabaseReference horizontalAngle = database.getReference("Ball Configuration /Horizontal Angle");
    DatabaseReference verticalAngle = database.getReference("Ball Configuration /Vertical Angle");
    DatabaseReference start = database.getReference("Ball Configuration /Start");
    DatabaseReference stop = database.getReference("Ball Configuration /Stop");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_screen);
    }

    public void randStart(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.randStartButtonToast), Toast.LENGTH_SHORT).show();
        start.setValue("True");
        stop.setValue("False");
        //speed.setValue(//randval);
        //horizontalAngle.setValue(//hangle);
        //verticalAngle.setValue(//vangle);
    }

    public void randStop(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.randStopButtonToast), Toast.LENGTH_SHORT).show();
        stop.setValue("True");
        start.setValue("False");
    }
}
