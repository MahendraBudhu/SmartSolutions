package pingpong.app.machine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.machine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SocialFragment extends Fragment {

    public ImageView twitterImg;
    public ImageView githubImg;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.social_fragment, container, false);

        twitterImg = (ImageView) v.findViewById(R.id.twitterImg);
        githubImg = (ImageView) v.findViewById(R.id.githubImg);

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

        return v;
    }
}
