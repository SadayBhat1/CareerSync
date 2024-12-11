package com.finalproj.kleplacementapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.PrintStream;


public class FeedbackFragment extends Fragment {

    private EditText nameEditText, emailEditText, phoneEditText, messageEditText;
    private Button feedbacksubmit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        TextInputLayout nameLayout = v.findViewById(R.id.feedbackname);
        TextInputLayout emailLayout = v.findViewById(R.id.feedbackemail);
        TextInputLayout phoneLayout = v.findViewById(R.id.feedbacknumb);
        TextInputLayout messageLayout = v.findViewById(R.id.feedbackmsg);


        nameEditText = nameLayout.getEditText();
        emailEditText = emailLayout.getEditText();
        phoneEditText = phoneLayout.getEditText();
        messageEditText = messageLayout.getEditText();

        Button backButton = v.findViewById(R.id.back_button);

        feedbacksubmit = v.findViewById(R.id.submitfeedback);

                // Process the feedback data here
        feedbacksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String message = messageEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || message.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    String recipientEmail = "proj1372@gmail.com";
                    String subject = "Feedback Form";
                    String emailContent = "Name: " + name + "\nEmail: " + email + "\nPhone Number: " + phone + "\nMessage: " + message;

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, emailContent);

                    // Set the package name for Gmail to directly open Gmail
                    intent.setPackage("com.google.android.gm");

                    try {
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        // If Gmail is not installed or available, show the "No Email Client Found" message
                        Toast.makeText(getActivity(), "No Email Client Found", Toast.LENGTH_SHORT).show();
                    }
                // Clear the input fields
                    nameEditText.getText().clear();
                    emailEditText.getText().clear();
                    phoneEditText.getText().clear();
                    messageEditText.getText().clear();
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the FeedbackFragment with the MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
                startActivity(intent);
                requireActivity().finish(); // Close the current activity
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

}
