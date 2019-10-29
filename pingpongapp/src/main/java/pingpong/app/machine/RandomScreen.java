package pingpong.app.machine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.machine.R;

public class RandomScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_screen);
    }

    public void randStart(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.randStartButtonToast), Toast.LENGTH_LONG).show();
        //Code to start machine
    }

    public void randStop(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.randStopButtonToast), Toast.LENGTH_LONG).show();
        //Code to stop machine
    }
}
