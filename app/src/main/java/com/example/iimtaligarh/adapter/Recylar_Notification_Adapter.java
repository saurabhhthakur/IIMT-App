package com.example.iimtaligarh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iimtaligarh.R;

import java.util.ArrayList;

public class Recylar_Notification_Adapter extends RecyclerView.Adapter<Recylar_Notification_Adapter.ViewHolder> {

    private final Context context;
    ArrayList<String> notification_text;

    public Recylar_Notification_Adapter(Context context, ArrayList<String> notification_text) {
        this.context = context;
        this.notification_text = notification_text;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(notification_text.get(position));

    }

    @Override
    public int getItemCount() {
        return notification_text.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.recyclar_text);


        }
    }


}
