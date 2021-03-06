package com.example.irecipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irecipe.Prevalent.prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private String UserRights = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        UserRights = getIntent().getStringExtra("isChef");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Insert code for displaying user picture, name and email on the header view

        View headView = navigationView.getHeaderView(0);
        TextView Name = headView.findViewById(R.id.display_name);
        CircleImageView Image = headView.findViewById(R.id.display_image);
        TextView Email = headView.findViewById(R.id.display_email);

        Name.setText(prevalent.currentOnLineUser.getUsername());
        Email.setText(prevalent.currentOnLineUser.getEmail_Address());

        Picasso.get().load(prevalent.currentOnLineUser.getImage()).placeholder(R.drawable.avatar_image_background).into(Image);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        if (UserRights.equals("Chef"))
        {
            menu.findItem(R.id.action_uploadRecipe).setVisible(true);
        }
        else if (UserRights.equals(""))
        {
            menu.findItem(R.id.action_uploadRecipe).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.action_settings:

                Intent settings = new Intent(Dashboard.this, update.class);
                startActivity(settings);
                return true;
            case R.id.action_uploadRecipe:
                //Toast.makeText(this, "" + UserRights, Toast.LENGTH_SHORT).show();
                Intent uploadRecipe = new Intent(Dashboard.this, UploadRecipe.class);
                startActivity(uploadRecipe);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

}