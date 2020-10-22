package com.example.irecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPage extends AppCompatActivity {

    EditText username,phone,email,password;
    CircleImageView image;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_page);

         image = findViewById(R.id.image);
         username = findViewById(R.id.username);
         phone = findViewById(R.id.phone);
         email = findViewById(R.id.email);
         password = findViewById(R.id.password);
         signUp = findViewById(R.id.signUpBtn);



    }
}