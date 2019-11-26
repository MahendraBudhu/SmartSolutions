package pingpong.app.machine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.machine.R;

public class SocialScreen extends AppCompatActivity {

    public ImageView twitterImg;
    public ImageView githubImg;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_screen);
        twitterImg = (ImageView) findViewById(R.id.twitterImg) ;
        githubImg = (ImageView) findViewById(R.id.githubImg);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        twitterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri twitterUrl =  Uri.parse("https://twitter.com/smarts0lutions");
                Intent intent = new Intent(Intent.ACTION_VIEW, twitterUrl);
                startActivity(intent);
            }
        });

        githubImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri githubUrl =  Uri.parse("https://github.com/MahendraBudhu/SmartSolutions");
                Intent intent = new Intent(Intent.ACTION_VIEW, githubUrl);
                startActivity(intent);
            }
        });
    }

}
