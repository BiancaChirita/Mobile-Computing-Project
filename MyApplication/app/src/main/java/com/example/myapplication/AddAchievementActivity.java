package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class AddAchievementActivity extends AppCompatActivity {

    ImageView back, save;
    EditText name, description;
    int gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);
    }
}
