package pingpong.app.machine;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.machine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private int STORAGE_PERMISSION_CODE = 1;
    ProgressBar pgsBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
    }

    //Validating Login Infos
    public void login(final View view) {

        pgsBar = findViewById(R.id.pBar);
        if (ContextCompat.checkSelfPermission(LoginScreen.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestStoragePermission();
        }
        pgsBar.setVisibility(view.VISIBLE);
        TextView userField = findViewById(R.id.username);    //Getting user inputs
        TextView passField = findViewById(R.id.password);
        String username = userField.getText().toString();
        String password = passField.getText().toString();

        if (username.length() == 0 && password.length() == 0) {           //Basic checks for username, password and both. Will be expanded later. This is just a prototype at the moment.
            pgsBar.setVisibility(view.GONE);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noBothToast), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        } else if (username.length() == 0) {
            pgsBar.setVisibility(view.GONE);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noUserToast), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        } else if (password.length() == 0) {
            pgsBar.setVisibility(view.GONE);
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noPassToast), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        } else {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.loginsucess), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                                startActivity(intent);
                                pgsBar.setVisibility(view.GONE);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                pgsBar.setVisibility(view.GONE);
                                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.loginfailed), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    //Sending user to register screen
    public void register(View view) {
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginScreen.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    public void forgotPass(View view) {
//delete
        FirebaseAuth auth = FirebaseAuth.getInstance();
        TextView userField = findViewById(R.id.username);
        String username = userField.getText().toString();
        if (username.length() == 0) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterUserToReset), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
        } else {
            auth.sendPasswordResetEmail(username)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.forgotpass), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.invalid_username), Toast.LENGTH_LONG).show(); //Change hardcoded toasts!
                            }
                        }
                    });
        }
    }

}
