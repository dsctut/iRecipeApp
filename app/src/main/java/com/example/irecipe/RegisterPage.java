package com.example.irecipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPage extends AppCompatActivity {

    EditText username,phone,email,password;
    CircleImageView img;
    Button submit;

    private StorageReference mStorage;
    private StorageTask uploadTask;
    private String myUrl = "";
    private Uri imageUri;
    private boolean User_Available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_page);

        mStorage = FirebaseStorage.getInstance().getReference().child("Profile_Pictures");

        img = findViewById(R.id.image);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.signUpBtn);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1).start(RegisterPage.this);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Snackbar.make(view, "Username cannot be left empty",Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar.make(view, "Phone Number cannot be left empty",Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(view, "Email Address cannot be left empty",Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(TextUtils.isEmpty(password.getText().toString())){
                    Snackbar.make(view, "Password cannot be left empty",Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    boolean isAvailable = available(phone.getText().toString());

                    if(isAvailable){
                        Toast.makeText(RegisterPage.this, "Account with "+phone.getText().toString()+" already exist", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        createAccount(view);
                    }
                }
            }
        });
    }



    private boolean available(final String user) {
        DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users");

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(user).exists()){
                    User_Available = true;
                }
                else{
                    User_Available = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterPage.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        return User_Available;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            img.setImageURI(imageUri);
        }
    }

    private void createAccount(final View view) {
        if (imageUri != null){
            final StorageReference imgRef = mStorage.child(phone.getText().toString()+".jpg");
            uploadTask = imgRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imgRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUrl = task.getResult();
                    myUrl = downloadUrl.toString();

                    DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users");

                    HashMap<String,Object>userData = new HashMap<>();

                    userData.put("Username", username.getText().toString());
                    userData.put("Password", password.getText().toString());
                    userData.put("Phone_Number", phone.getText().toString());
                    userData.put("Email_Address", email.getText().toString());
                    userData.put("Image", myUrl);

                    RootRef.child(phone.getText().toString()).updateChildren(userData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Snackbar.make(view,"Account Successfully Created",Snackbar.LENGTH_LONG)
                                                .setAction("Action",null).show();
                                        startActivity(new Intent(RegisterPage.this,LoginPage.class));
                                    }
                                }
                            });
                }
            });
        }
    }
}