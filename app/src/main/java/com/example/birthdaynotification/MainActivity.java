package com.example.birthdaynotification;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    NavController navController;
    private NavController.OnDestinationChangedListener onDestinationChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        onDestinationChangedListener = (controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.addBirthdayFragment:
                    navView.setVisibility(View.GONE);
                    break;

                case R.id.navigation_home:
                case R.id.navigation_notifications:
                    navView.setVisibility(View.VISIBLE);
                    break;
            }
        };

        navController.addOnDestinationChangedListener(onDestinationChangedListener);
    }

    private void initializeViews() {
        navView = findViewById(R.id.nav_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navController.removeOnDestinationChangedListener(onDestinationChangedListener);
    }
}