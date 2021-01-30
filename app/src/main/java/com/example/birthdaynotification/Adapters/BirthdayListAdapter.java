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

public class BirthdayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;

    public BirthdayListAdapter(Context ctx) {
        this.context = ctx;
        this.inflater = LayoutInflater.from (ctx);
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
        ((ListViewHolder)holder).setListDetails("Shubham singh", "18-11-2000");
    }

    @Override
    public int getItemCount() {
        return 2;
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

}
