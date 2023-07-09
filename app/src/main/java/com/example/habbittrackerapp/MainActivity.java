package com.example.habbittrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bnview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnview = findViewById(R.id.bnView);
        bnview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.nav_home){
                    loadFrag(new HomeFragment(),false);
                }else if(id == R.id.nav_progress){
                    loadFrag(new ProgressFragment(),false);

                }else if(id == R.id.nav_habits){
                    loadFrag(new HabitsFragment(),false);

                }else if(id == R.id.nav_profile){
                    loadFrag(new ProfileFragment(),false);
                }
                return true;
            }
        });
        bnview.setSelectedItemId(R.id.nav_home);
    }
    public void loadFrag(Fragment fragment,boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag){
            ft.add(R.id.frame_container,fragment);
        }else{
            ft.replace(R.id.frame_container,fragment);
        }
        ft.commit();
    }
}