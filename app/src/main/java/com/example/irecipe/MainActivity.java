package com.example.irecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView TestNavDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test nav drawer layout
        TestNavDrawer = (TextView)findViewById(R.id.testNavLayout);
        TestNavDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent navDrawer = new Intent(MainActivity.this, NavigationDrawer.class);
                startActivity(navDrawer);
            }
        });

    }



}