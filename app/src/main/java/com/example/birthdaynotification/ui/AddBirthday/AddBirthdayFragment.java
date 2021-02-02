package com.example.birthdaynotification.ui.AddBirthday;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.birthdaynotification.R;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddBirthdayFragment extends Fragment {

    private static final String TAG = "AddBirthdayFragment";

    long today = MaterialDatePicker.todayInUtcMilliseconds();

    private AddBirthdayViewModel mViewModel;
    private TextInputLayout nameInputLayout;
    private TextInputEditText nameEt;
    private TextWatcher inputTextWatcher;
    private TextInputLayout dateInputLayout;
    private TextInputEditText dateEt;
    private Button selectDateBtn;
    private Button saveBtn;
    private MaterialToolbar appToolbar;

    NavController navController;

    public static AddBirthdayFragment newInstance() {
        return new AddBirthdayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_birthday_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddBirthdayViewModel.class);
        initializeViews(view);
        setOnClickListeners();
        setLiveDataObservers();
        setUpAppBar();


        inputTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setNameLiveData(s.toString());
                if(s.toString().equals("")) {
                    nameInputLayout.setError("Name Required.");
                } else {
                    nameInputLayout.setError(null);
                }
            }
        };
        nameEt.addTextChangedListener(inputTextWatcher);

        dateEt.setText(getDateFromEpochMillis(today));
    }

    private void setUpAppBar() {
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupWithNavController(
                appToolbar, navController, appBarConfiguration);
    }

    private void setLiveDataObservers() {
        mViewModel.getNameLiveData().observe(getViewLifecycleOwner(), s -> {
            saveBtn.setEnabled(!s.equals(""));
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initializeViews(View root) {
        nameInputLayout = root.findViewById(R.id.name_input_layout);
        nameEt = root.findViewById(R.id.name_input_et);
        dateInputLayout = root.findViewById(R.id.date_input_layout);
        dateEt = root.findViewById(R.id.date_input_et);
        selectDateBtn = root.findViewById(R.id.select_button);
        saveBtn = root.findViewById(R.id.save_button);
        appToolbar = root.findViewById(R.id.toolbar);

        navController = Navigation.findNavController(root);
    }

    private void setOnClickListeners() {
        selectDateBtn.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> picker = builder.build();

            picker.addOnCancelListener(dialog -> {
                Toast.makeText(getContext(), "picker cancelled", Toast.LENGTH_SHORT).show();
            });

            picker.addOnDismissListener(dialog -> {
                Toast.makeText(getContext(), "picker dismissed", Toast.LENGTH_SHORT).show();
            });

            picker.addOnNegativeButtonClickListener(v1 -> {
                Toast.makeText(getContext(), "negative btn clicked", Toast.LENGTH_SHORT).show();

            });

            picker.addOnPositiveButtonClickListener(selection -> {
                Log.d(TAG, String.valueOf(selection));

                String date = getDateFromEpochMillis(selection);
                dateEt.setText(date);

            });

            picker.show(getChildFragmentManager(), picker.toString());
        });

        saveBtn.setOnClickListener(v -> {
            Birthday birthday = new Birthday(nameEt.getText().toString(), dateEt.getText().toString());
            mViewModel.insert(birthday);
        });
    }

    public String getDateFromEpochMillis(Long millis) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTimeInMillis(millis);
        Date date = c.getTime();
        String day = new SimpleDateFormat("dd").format(date);    // always 2 digits
        String month = new SimpleDateFormat("MM").format(date);  // always 2 digits
        String year = new SimpleDateFormat("yyyy").format(date); // 4 digit year

        return day + "-" + month + "-" + year;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nameEt.removeTextChangedListener(inputTextWatcher);
    }
}