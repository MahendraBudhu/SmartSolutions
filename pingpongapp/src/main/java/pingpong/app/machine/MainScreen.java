package pingpong.app.machine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.machine.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SetupFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFrag = null;
                    switch(menuItem.getItemId()){
                        case R.id.nav_manualConfig: selectedFrag = new SetupFragment();
                        break;
                        case R.id.nav_randomConfig:  selectedFrag = new RandomFragment();
                        break;
                        case R.id.nav_statistics:  selectedFrag = new StatisticsFragment();
                        break;
                        case R.id.nav_socialMedia:  selectedFrag = new SocialFragment();
                        break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();

                    return true;
                }
            };
}
