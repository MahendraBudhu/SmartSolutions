package pingpong.app.machine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.machine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
    }
    //Validating Login Infos
    public void login(View view){
        TextView userField =  findViewById(R.id.username);    //Getting user inputs
        TextView passField =  findViewById(R.id.password);
        String username = userField.getText().toString();
        String password = passField.getText().toString();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        String userName = userField.getText().toString();   //Converting user inputs into String
        String passWord = passField.getText().toString();

        if(userName.length() == 0 && passWord.length() == 0){           //Basic checks for username, password and both. Will be expanded later. This is just a prototype at the moment.
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noBothToast), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        }
        else if(userName.length() == 0){
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noUserToast), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        }
        else if(passWord.length() == 0){
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noPassToast), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        }
        else {
            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);
        }
    }

    //Sending user to register screen
    public void register(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.wipToast), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
    }

}
