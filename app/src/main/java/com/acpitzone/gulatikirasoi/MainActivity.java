package com.acpitzone.gulatikirasoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().add(R.id.flFragment, new homeFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new homeFragment();
                        break;
                    case R.id.person:
                        fragment = new personFragment();
                        break;
                    case R.id.settings:
                        fragment = new settingFragment();
                        break;
                    case R.id.notification:
                        fragment = new notificationFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().add(R.id.flFragment, fragment).commit();
                return true;
            }
        });
    }
}