package com.example.cleancodeofledger.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cleancodeofledger.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);
        BottomNavigationView income_expenses_bottomNavigationView = view.findViewById(R.id.income_expenses_bottomNavigationView);
        NavController navController = Navigation.findNavController(view.findViewById(R.id.income_expenses_container));
        NavigationUI.setupWithNavController(income_expenses_bottomNavigationView,navController);


        return view;
    }
}