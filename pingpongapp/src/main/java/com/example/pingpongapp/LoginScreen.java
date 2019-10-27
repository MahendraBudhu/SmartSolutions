package com.example.pingpongapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    //Validating Login Info
    public void login(View view){

        TextView userField = (TextView) findViewById(R.id.username);    //Getting user inputs
        TextView passField = (TextView) findViewById(R.id.password);

        String userName = userField.getText().toString();               //Converting user inputs into String
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
