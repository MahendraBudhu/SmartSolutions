package pingpong.app.machine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.machine.R;

public class AboutUs extends AppCompatActivity {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_screen);
        ImageView githubImg;
        githubImg =findViewById(R.id.githubImg);
        githubImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri githubUrl = Uri.parse("https://github.com/MahendraBudhu/SmartSolutions");
                Intent intent = new Intent(Intent.ACTION_VIEW, githubUrl);
                startActivity(intent);
            }
        });
    }


}
