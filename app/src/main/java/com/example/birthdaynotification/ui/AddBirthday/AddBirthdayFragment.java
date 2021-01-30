package com.example.birthdaynotification.ui.AddBirthday;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.birthdaynotification.R;

public class AddBirthdayFragment extends Fragment {

    private AddBirthdayViewModel mViewModel;

    public static AddBirthdayFragment newInstance() {
        return new AddBirthdayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_birthday_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddBirthdayViewModel.class);
        // TODO: Use the ViewModel
    }

}