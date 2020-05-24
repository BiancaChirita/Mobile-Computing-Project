package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnLogin;

    Database database;
    DataContent dataContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new Database(this);
        dataContent = com.example.myapplication.DataContent.getInstance();

        email = (EditText) findViewById(R.id.txtLoginEmail);
        password = (EditText) findViewById(R.id.txtLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView txtRegister = (TextView) findViewById(R.id.txtRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sPassword = password.getText().toString();

                Boolean chkEmailPassword = database.checkEmailPassword(sEmail, sPassword);
                if(chkEmailPassword){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    dataContent.setEmail(sEmail);
                    String sUsername = database.getUsername(sEmail);
                    int id = database.getUserId(sEmail);
                    dataContent.setUsername(sUsername);
                    dataContent.setPassword(sPassword);
                    dataContent.setUserId(id);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
