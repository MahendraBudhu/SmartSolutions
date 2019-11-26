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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    TextView userField;
    TextView passField;
    String passWord;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {
        userField =  findViewById(R.id.username);
        passField =  findViewById(R.id.password);
        userName = userField.getText().toString();
        passWord = passField.getText().toString();
        //register user
        mAuth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.registersuccess), Toast.LENGTH_LONG).show();
                            sendEmailVerification();
                            String emails = "Users/" + user.getUid();
                            String emailEntry = "Users/" + user.getUid() + "/Email: ";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference users = database.getReference(emails);
                            DatabaseReference emailVal = database.getReference(emailEntry);
                            emailVal.setValue(userName);
                            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.registerfailed), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterScreen.this, getString(R.string.emailsent)  + user.getEmail(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegisterScreen.this, getString(R.string.notsent),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
