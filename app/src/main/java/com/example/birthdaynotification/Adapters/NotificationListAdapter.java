package com.example.birthdaynotification.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdaynotification.R;
import com.example.birthdaynotification.RoomDb.Entities.Notification;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Notification> notificationList;
    Context context;
    LayoutInflater inflater;

    public NotificationListAdapter(Context ctx, List<Notification> notificationList) {
        this.context = ctx;
        this.inflater = LayoutInflater.from (ctx);
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_list_item_layout, parent, false);
        return new NotificationListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Manipulate the ui here
        Notification notification = notificationList.get(position);
        String title = notification.getTitle();
        String message = notification.getMessage();
        ((NotificationListAdapter.ListViewHolder)holder).setListDetails(title, message);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView messageView;

        public ListViewHolder(@NonNull View itemView) {
            super (itemView);
            titleView = itemView.findViewById(R.id.title_tv);
            messageView = itemView.findViewById(R.id.message_tv);
        }

        public void setListDetails(String title, String message) {
            titleView.setText(title);
            messageView.setText(message);
        }
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
        this.notifyDataSetChanged();
    }
}
