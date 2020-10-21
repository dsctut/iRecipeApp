package com.example.irecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView TestNavDrawer;
    Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = (Button)findViewById(R.id.signIn);
        signUp = (Button)findViewById(R.id.signUp);


        //Test nav drawer layout
        TestNavDrawer = (TextView)findViewById(R.id.testNavLayout);
        TestNavDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent navDrawer = new Intent(MainActivity.this, NavigationDrawer.class);
                startActivity(navDrawer);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterPage.class));
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginPage.class));
                finish();
            }
        });
    }



}