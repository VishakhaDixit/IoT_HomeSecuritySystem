package com.example.my49ersense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText textInputFullname, textInputUsername, textInputEmail, textInputPassword;
    Button buttonSignUp;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputFullname = findViewById(R.id.fullname);
        textInputUsername = findViewById(R.id.username);
        textInputEmail = findViewById(R.id.email);
        textInputPassword = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.signupbtn);
        textViewLogin = findViewById(R.id.loginText);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname, username, password, email;
                fullname = String.valueOf(textInputFullname.getText().toString());
                username = String.valueOf(textInputUsername.getText().toString());
                password = String.valueOf(textInputPassword.getText().toString());
                email = String.valueOf(textInputEmail.getText().toString());

                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";

                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;

                            PutData putData = new PutData(Globals.PHP_URL+"signup.php", "POST", field, data);

                            if(putData.startPut()) {
                                if(putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")) {
                                        Toast.makeText(SignUp.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            // End write and read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}