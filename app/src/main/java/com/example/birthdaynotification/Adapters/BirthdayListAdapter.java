package com.example.birthdaynotification.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.birthdaynotification.R;
import com.example.birthdaynotification.RoomDb.Entities.Birthday;

import java.util.ArrayList;
import java.util.List;

public class BirthdayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Birthday> birthdayList;
    Context context;
    LayoutInflater inflater;

    public BirthdayListAdapter(Context ctx, List<Birthday> birthdayList) {
        this.context = ctx;
        this.inflater = LayoutInflater.from (ctx);
        this.birthdayList = birthdayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.birthday_list_item_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Manipulate the ui here
        Birthday birthday = birthdayList.get(position);
        ((ListViewHolder)holder).setListDetails(birthday.getName(), birthday.getDate());
    }

    @Override
    public int getItemCount() {
        return birthdayList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView dateView;

        public ListViewHolder(@NonNull View itemView) {
            super (itemView);
            nameView = itemView.findViewById(R.id.name_tv);
            dateView = itemView.findViewById(R.id.date_tv);
        }

        public void setListDetails(String name, String date) {
            nameView.setText(name);
            dateView.setText(date);
        }
    }

    public void setBirthdayList(List<Birthday> birthdayList) {
        this.birthdayList = birthdayList;
        this.notifyDataSetChanged();
    }

}
