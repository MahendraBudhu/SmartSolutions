package pingpong.app.machine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.machine.R;

    public class ContactUs extends AppCompatActivity {

        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contactus_screen);
            ImageView twitterImg;
            twitterImg = findViewById(R.id.twitterImg);
            twitterImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri twitterUrl = Uri.parse("https://twitter.com/smarts0lutions");
                    Intent intent = new Intent(Intent.ACTION_VIEW, twitterUrl);
                    startActivity(intent);
                }
            });
            ImageView email;
            email = findViewById(R.id.emailimage);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "smartsolutionsmia@gmail.com"));
                    startActivity(intent);
                }
            });
        }
}
