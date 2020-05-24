package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    DataContent dataContent;
    Database database;

    TextView username, gameNumber;
    ImageView addGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = new Database(this);
        dataContent = com.example.myapplication.DataContent.getInstance();

        username = (TextView) findViewById(R.id.txtViewUsername);
        gameNumber = (TextView) findViewById(R.id.txtViewGameNr);

        String email = dataContent.getEmail();
        String userName = database.getUsername(email);

        username.setText(userName);

        addGame = (ImageView)findViewById(R.id.ivAddGame);
        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
