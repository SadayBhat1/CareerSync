package com.finalproj.kleplacementapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<String> notificationList;
    private NotificationCallback notificationCallback;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Retrieve notification data from Firestore and update the notificationList
        retrieveNotifications();

        return view;
    }

    public static NotificationFragment newInstance(NotificationCallback callback) {
        NotificationFragment fragment = new NotificationFragment();
        fragment.notificationCallback = callback;
        return fragment;
    }

    private void retrieveNotifications() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications1").orderBy("timestamp", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        notificationList.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String notification = documentSnapshot.getString("message");
                            long timestamp = documentSnapshot.getLong("timestamp");
                            String formattedNotification = notification + " - " + timestamp;
                            notificationList.add(formattedNotification);
                        }
                        adapter.notifyDataSetChanged();

                        Log.d("NotificationFragment", "Notification count: " + notificationList.size());

                        // Update the badge count using the callback
                        if (notificationCallback != null) {
                            notificationCallback.onNotificationCountUpdated(notificationList.size());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("NotificationFragment", "Error retrieving notifications: " + e.getMessage());
                    }
                });
    }

    // Callback interface for notifying the count to the activity
    public interface NotificationCallback {
        void onNotificationCountUpdated(int count);
    }

}
