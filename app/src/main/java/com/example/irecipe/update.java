package com.example.irecipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.irecipe.Prevalent.prevalent;
import com.example.irecipe.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class update extends AppCompatActivity {


    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicRef;
    private String Checker = "";

    CircleImageView DisplayProfilePic;
    TextView ChangeProfilePicture, selector;
    EditText FullNames, Password, Email;
    Button UpdateButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        storageProfilePicRef = FirebaseStorage.getInstance().getReference().child("Profile_Pictures");

        DisplayProfilePic = (CircleImageView)findViewById(R.id.profilePicture);
        ChangeProfilePicture = (TextView)findViewById(R.id.changeImage);

        FullNames = (EditText)findViewById(R.id.names);
        Password = (EditText)findViewById(R.id.password);
        UpdateButton = (Button)findViewById(R.id.updateinputbtn);
        Email = (EditText)findViewById(R.id.emailText);


        userInfoDisplay(FullNames, Password, DisplayProfilePic, Email);
        ChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checker = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(update.this);
            }
        });


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    UpdateOnlyUserInfo();
                }
            }
        });



    }


    private void UpdateOnlyUserInfo() {


        DatabaseReference databaseRef;
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String,Object> userData = new HashMap<>();
        userData.put("Username", FullNames.getText().toString());
        userData.put("Password", Password.getText().toString());
        userData.put("Email_Address", Email.getText().toString());

        databaseRef.child(prevalent.currentOnLineUser.getPhone_Number()).updateChildren(userData);
        startActivity(new Intent(update.this, Dashboard.class));
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            DisplayProfilePic.setImageURI(imageUri);
        } else {
            startActivity(new Intent(update.this, update.class));
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void userInfoSaved() {
        if (TextUtils.isEmpty(FullNames.getText().toString())){
            Toast.makeText(this, "Name Required", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Password.getText().toString())){
            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Email.getText().toString())){
            Toast.makeText(this, "Email Required", Toast.LENGTH_SHORT).show();
        } else if (Checker.equals("clicked")){
            UploadImage();
        }


    }


    private void UploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if ( imageUri != null) {
            final StorageReference fileRef = storageProfilePicRef.child(prevalent.currentOnLineUser.getPhone_Number() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();
                                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                HashMap<String,Object>userData = new HashMap<>();
                                userData.put("Username", FullNames.getText().toString());
                                userData.put("Address", Password.getText().toString());
                                userData.put("Email_Address", Email.getText().toString());
                                userData.put("Image", myUrl);
                                databaseRef.child(prevalent.currentOnLineUser.getPhone_Number()).updateChildren(userData);

                                progressDialog.dismiss();

                                startActivity(new Intent(update.this, update.class));
                                Toast.makeText(update.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(update.this, "Check Your Internet Connectivity", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(this, "Image File Not Selected", Toast.LENGTH_SHORT).show();
        }

    }


    private void userInfoDisplay( final EditText fullNames, final EditText password, final CircleImageView displayProfilePic, final EditText Email) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.currentOnLineUser.getPhone_Number());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("Image").exists()){
                        String Image = snapshot.child("Image").getValue().toString();
                        String name = snapshot.child("Username").getValue().toString();
                        String Password = snapshot.child("Password").getValue().toString();
                        String email = snapshot.child("Email_Address").getValue().toString();

                        Picasso.get().load(Image).into(displayProfilePic);

                        fullNames.setText(name);
                        password.setText(Password);
                        Email.setText(email);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }




}