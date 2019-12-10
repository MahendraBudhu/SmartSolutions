package pingpong.app.machine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.machine.R;

public class SettingsScreen extends AppCompatActivity {

    private boolean nightMode = false;

    public Switch nightModeSwitch;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        pref  = getApplicationContext().getSharedPreferences("MyPrefs", 0);
        editor = pref.edit();

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("darkMode", true);
                    editor.commit();
                    darkModeCheck();
                }
                else{
                    editor.putBoolean("darkMode", false);
                    editor.commit();
                    darkModeCheck();
                }
            }
        });

        }
    public void darkModeCheck(){
        Boolean check = pref.getBoolean("darkMode", false);
        if(check){
            setTheme(R.style.ActivityTheme_Primary_Base_Dark);
        }
        else{
            setTheme(R.style.ActivityTheme_Primary_Base_Light);
        }
    }
}
