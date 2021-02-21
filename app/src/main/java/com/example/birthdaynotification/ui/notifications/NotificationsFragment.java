package com.example.birthdaynotification.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdaynotification.Adapters.BirthdayListAdapter;
import com.example.birthdaynotification.Adapters.NotificationListAdapter;
import com.example.birthdaynotification.R;
import com.example.birthdaynotification.RoomDb.Entities.Notification;

import java.util.Collections;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private static final String TAG = "NotificationsFragment";

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView notificationRecyclerView;
    private NotificationListAdapter notificationListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        initializeViews(root);
        setOnClickListeners();
        initNotificationList();
        setLiveDataObservers();

        return root;
    }

    private void initializeViews(View root) {
        notificationRecyclerView = root.findViewById(R.id.notification_list_view);
    }

    private void initNotificationList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        notificationListAdapter= new NotificationListAdapter(getContext(), Collections.emptyList());
        notificationRecyclerView.setLayoutManager(linearLayoutManager);
        notificationRecyclerView.setAdapter(notificationListAdapter);

    }

    private void setOnClickListeners() {

    }

    private void setLiveDataObservers() {
        notificationsViewModel.getNotificationListLiveData().observe(getViewLifecycleOwner(), notificationList -> {
            Log.d(TAG, "setLiveDataObservers: observer reacted : " + notificationList.get(0).getTitle());
            notificationListAdapter.setNotificationList(notificationList);
        });
    }
}