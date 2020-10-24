package com.example.irecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.irecipe.Model.User;
import com.example.irecipe.Prevalent.prevalent;



import java.nio.charset.CharacterCodingException;



public class LoginPage extends AppCompatActivity {

    EditText phone,password;
    Button signIn;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

         phone = (EditText) findViewById(R.id.phone);
         password = (EditText)findViewById(R.id.password);
         signIn = (Button)findViewById(R.id.signInBtn);
        // forgotPassword = (TextView) findViewById(R.id.forgotPassword);

         signIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String Phone = phone.getText().toString();
                 String Password = password.getText().toString();

                 if (Phone.isEmpty())
                 {
                     Snackbar.make(view,"Phone Number cannot be left empty",Snackbar.LENGTH_LONG)
                             .setAction("Action",null).show();
                 }
                 else
                     if(Password.isEmpty())
                 {
                     Snackbar.make(view,"Password cannot be left empty",Snackbar.LENGTH_LONG)
                             .setAction("Action",null).show();
                 }
                 else
                     Access(Phone,Password);
             }
         });
    }

    private void Access(final String Phone,final String Password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {
                if (datasnapshot.child("Users").child(Phone).exists())
                {

                    User userData = datasnapshot.child("Users").child(Phone).getValue(User.class);
                    if (userData.getPhone_Number().equals(Phone))
                    {
                        if (userData.getPassword().equals(Password)) {
                            Toast.makeText(LoginPage.this, "Successfully logged in", Toast.LENGTH_LONG).show();

                            // // // // // // // // // // // // //
                            Intent intent = new Intent(LoginPage.this,Dashboard.class);
                            prevalent.currentOnLineUser = userData;
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(LoginPage.this, "Incorrect password entered", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(LoginPage.this, "Incorrect Phone Number entered", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(LoginPage.this, Phone + " does not exist. Sign Up", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(LoginPage.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}