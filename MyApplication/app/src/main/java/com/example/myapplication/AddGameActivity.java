package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddGameActivity extends AppCompatActivity {

    ImageView back, save, image;
    EditText name, description;
    TextView addImage;

    DataContent dataContent;
    Database database;

    int userId;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        database = new Database(this);
        dataContent = com.example.myapplication.DataContent.getInstance();
        userId = dataContent.getUserId();

        back = (ImageView) findViewById(R.id.ivBackFromAddGame);
        save = (ImageView) findViewById(R.id.ivAddGameCheck);
        image = (ImageView) findViewById(R.id.ivAddGameImg);
        name = (EditText) findViewById(R.id.etAddGameName);
        description = (EditText) findViewById(R.id.edAddGameDescription);
        addImage = (TextView) findViewById(R.id.txtAddGameImg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGameActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = name.getText().toString();
                String sDescription = description.getText().toString();
                BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                if(bitmap.equals(null) && sName.equals("") && sDescription.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG);
                }else{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] image = stream.toByteArray();
                    String imageString = Base64.encodeToString(image, Base64.DEFAULT);
                    database.registerGame(sName, sDescription , imageString, userId);
                    Toast.makeText(getApplicationContext(), "Changes were made", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(AddGameActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
