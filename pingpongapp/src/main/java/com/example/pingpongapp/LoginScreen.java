package com.example.pingpongapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    //Validating Login Info
    public void login(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference login = database.getReference("Login");
        DatabaseReference username = database.getReference("Login/Username: ");     //Making Username DB Field
        DatabaseReference password = database.getReference("Login/Password: ");     //Making Password DB Field
        TextView userField = (TextView) findViewById(R.id.username);    //Getting user inputs
        TextView passField = (TextView) findViewById(R.id.password);

        String userName = userField.getText().toString();   //Converting user inputs into String
        String passWord = passField.getText().toString();
        username.setValue(userName);    //Storing Username DB Field
        password.setValue(passWord);    //Storing Password DB Field

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

            intent.putExtra("userKey", userName);
            intent.putExtra("passKey", passWord);

            startActivity(intent);
        }
    }

    //Sending user to register screen
    public void register(View view){
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.wipToast), Toast.LENGTH_LONG).show();
    }
}
