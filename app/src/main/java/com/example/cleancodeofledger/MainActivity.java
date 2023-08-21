package com.example.cleancodeofledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.cleancodeofledger.tools.AllData;
import com.example.cleancodeofledger.tools.Log;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.setContext(getApplicationContext());
        AllData.init(getApplicationContext());


        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        NavController navController = Navigation.findNavController(this,R.id.mainContainer);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

}