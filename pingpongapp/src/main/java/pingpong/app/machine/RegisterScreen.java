//Smart Solutions
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
    private static final String TAG = "EmailPassword";
    TextView userField;
    TextView passField;
    TextView firstN;
    TextView lastN;
    String passWord;
    String userName;
    String firstName;
    String lastName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {
        userField = findViewById(R.id.username);
        passField = findViewById(R.id.password);
        firstN = findViewById(R.id.firstName);
        lastN = findViewById(R.id.lastName);
        userField = findViewById(R.id.username);
        userName = userField.getText().toString();
        passWord = passField.getText().toString();
        firstName = firstN.getText().toString();
        lastName = lastN.getText().toString();
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

                            //Setting default parameters for when user registers in the database
                            String emailEntry = "Users/" + user.getUid() + "/Email: ";
                            String avgSpeedUser = "Users/" + user.getUid() + "/Average Speed: ";
                            ;
                            String minSpeedUser = "Users/" + user.getUid() + "/Minimum Speed: ";
                            ;
                            String maxSpeedUser = "Users/" + user.getUid() + "/Maximum Speed ";
                            ;
                            String accuracyUser = "Users/" + user.getUid() + "/Accuracy ";
                            ;
                            String totalShotsUser = "Users/" + user.getUid() + "/Total Shots Fired: ";
                            ;
                            String totalHitUser = "Users/" + user.getUid() + "/Total Balls Hit: ";
                            ;
                            String totalMissedUser = "Users/" + user.getUid() + "/Total Balls Missed: ";
                            ;
                            String firstNamePath = "Users/" + user.getUid() + "/First Name ";
                            ;
                            String lastNamePath = "Users/" + user.getUid() + "/Last Name ";
                            ;


                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference emailVal = database.getReference(emailEntry);
                            DatabaseReference avgSpeedVal = database.getReference(avgSpeedUser);
                            DatabaseReference minSpeedVal = database.getReference(minSpeedUser);
                            DatabaseReference maxSpeedVal = database.getReference(maxSpeedUser);
                            DatabaseReference accVal = database.getReference(accuracyUser);
                            DatabaseReference totalShotsVal = database.getReference(totalShotsUser);
                            DatabaseReference totalHitVal = database.getReference(totalHitUser);
                            DatabaseReference totalMissedVal = database.getReference(totalMissedUser);
                            DatabaseReference firstname = database.getReference(firstNamePath);
                            DatabaseReference lastname = database.getReference(lastNamePath);

                            emailVal.setValue(userName);
                            firstname.setValue(firstName);
                            lastname.setValue(lastName);
                            avgSpeedVal.setValue("0");
                            minSpeedVal.setValue("0");
                            maxSpeedVal.setValue("0");
                            accVal.setValue("0.00%");
                            totalShotsVal.setValue("0");
                            totalHitVal.setValue("0");
                            totalMissedVal.setValue("0");
//delete
                            //Sending user back to login screen
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
                            Toast.makeText(RegisterScreen.this, getString(R.string.emailsent) + user.getEmail(),
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
