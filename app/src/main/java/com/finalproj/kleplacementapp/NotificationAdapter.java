package com.finalproj.kleplacementapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.util.Linkify;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<String> notificationList;

    public NotificationAdapter(List<String> notificationList) {
        this.notificationList = notificationList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notification, parent, false);
        return new ViewHolder(view);
    }

    private String formatTimestamp(long timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault());
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    private long extractTimestamp(String formattedNotification) {
        String[] parts = formattedNotification.split(" - ");
        if (parts.length > 1) {
            String timestampString = parts[1];
            try {
                return Long.parseLong(timestampString);
            } catch (NumberFormatException e) {
                Log.e("NotificationAdapter", "Error parsing timestamp: " + e.getMessage());
            }
        }
        return 0; // Default value if the timestamp cannot be extracted
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String notification = notificationList.get(position);

        // Split the notification using " - " as the delimiter
        String[] parts = notification.split(" - ");

        if (parts.length > 0) {
            // Set the message in the messageTextView
            String message = parts[0];
            holder.messageTextView.setText(message);
            //linkify converts url in link format
            Linkify.addLinks(holder.messageTextView, Linkify.WEB_URLS);
        }

        // Extract the timestamp from the formatted notification
        long timestamp = extractTimestamp(notification);

        // Format the timestamp and set it to the timestampTextView
        String formattedTimestamp = formatTimestamp(timestamp);
        holder.timestampTextView.setText(formattedTimestamp);
    }




    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        //public TextView dateTimeTextView;
        public TextView timestampTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
