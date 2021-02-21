package com.example.birthdaynotification.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdaynotification.Adapters.BirthdayListAdapter;
import com.example.birthdaynotification.R;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView birthdayListView;
    private FloatingActionButton addFab;
    private NavController navController;
    BirthdayListAdapter birthdayListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(root);
        setOnClickListeners();
        setLiveDataObservers();
        initBirthdayList();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initializeViews(View root) {
        birthdayListView = root.findViewById(R.id.birthday_list_view);
        addFab = root.findViewById(R.id.add_fab);
    }

    private void initBirthdayList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        birthdayListAdapter = new BirthdayListAdapter(getContext(), Collections.emptyList());
        birthdayListView.setLayoutManager(linearLayoutManager);
        birthdayListView.setAdapter(birthdayListAdapter);

    }

    private void setOnClickListeners() {
        addFab.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_addBirthdayFragment);
        });
    }

    private void setLiveDataObservers() {
        homeViewModel.getBirthdayListLiveData().observe(getViewLifecycleOwner(), birthdayList -> {
            birthdayListAdapter.setBirthdayList(birthdayList);
        });
    }


}