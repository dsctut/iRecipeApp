package com.example.irecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.nio.charset.CharacterCodingException;

public class LoginPage extends AppCompatActivity {

    EditText phone,password;
    Button signIn;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

         phone = (EditText) findViewById(R.id.phone);
         password = (EditText)findViewById(R.id.password);
         signIn = (Button)findViewById(R.id.signInBtn);
         rememberMe = (CheckBox)findViewById(R.id.remember);
    }
}