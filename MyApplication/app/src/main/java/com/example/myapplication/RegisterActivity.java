package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText email, username, password, confPassword;
    DataContent dataContent;
    Database db;
    Button registerBtn;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new Database(this);
        dataContent = com.example.myapplication.DataContent.getInstance();

        email = (EditText) findViewById(R.id.txtRegisterEmail);
        username = (EditText) findViewById(R.id.txtRegisterUsername);
        password = (EditText) findViewById(R.id.txtRegisterPassword);
        confPassword = (EditText) findViewById(R.id.txtRegisterConfirmPass);
        registerBtn = (Button) findViewById(R.id.btnRegister);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                String sConfPassword = confPassword.getText().toString();

                if(sEmail.equals("") || sUsername.equals("") || sPassword.equals("") || sConfPassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }else {
                    if(sPassword.equals(sConfPassword)){
                        Boolean chkEmail = db.checkEmail(sEmail);
                        if(chkEmail){
                            Boolean insert = db.registerUser(sEmail, sUsername, sPassword);
                            if(insert){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login = (TextView) findViewById(R.id.txtLoginHere);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
