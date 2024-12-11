package com.finalproj.kleplacementapp;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddingNotification extends AppCompatActivity {

    private EditText notificationtext;
    private Button submitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notification_layout);
        FirebaseApp.initializeApp(this);

        notificationtext = findViewById(R.id.editTextNotification);
        submitButton = findViewById(R.id.submitnotification);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Get the notification text from the EditText
                String notificationText = notificationtext.getText().toString();

                if (notificationText.isEmpty()) {
                    Toast.makeText(AddingNotification.this, "No Text Present", Toast.LENGTH_SHORT).show();
                    return; // Exit the click listener without adding the notification
                }

                // Get the current timestamp
                long currentTimestamp = System.currentTimeMillis();

                Map<String, Object> notification = new HashMap<>();
                notification.put("message", notificationText);
                notification.put("timestamp", currentTimestamp);

                db.collection("notifications1").add(notification)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddingNotification.this, "Notification sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle any errors
                                Log.e(TAG, "Error adding notification: " + e.getMessage());
                                Toast.makeText(AddingNotification.this, "Failed to add notification", Toast.LENGTH_SHORT).show();
                            }
                        });

                notificationtext.setText("");
            }
        });


    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(AddingNotification.this,AdminHome.class);
        startActivity(i);
    }
}
