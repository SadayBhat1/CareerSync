package com.finalproj.kleplacementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



public class DevelopersFragment extends Fragment {
    private Button linkedin1,linkedin2,gmail1,gmail2,github1,github2;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_developers, container, false);

        linkedin1=v.findViewById(R.id.saday_linkedin);
        gmail1=v.findViewById(R.id.saday_gmail);
        github1=v.findViewById(R.id.saday_github);
        linkedin2=v.findViewById(R.id.milind_linkedin);
        gmail2=v.findViewById(R.id.milind_gmail);
        github2=v.findViewById(R.id.milind_github);


        linkedin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInChrome("https://www.linkedin.com/in/sadaybhat");
            }
        });

        gmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prepare intent with "mailto" action
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:sadaybhat@klebcahubli.in"));
                try {
                    startActivity(Intent.createChooser(intent, "Send email with"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "No email client found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        github1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInChrome("https://sadaybhat.github.io/devportfolio/");
            }
        });


        linkedin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInChrome("https://www.linkedin.com/in/milind-kanbargi-566b1822a");
            }
        });

        gmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prepare intent with "mailto" action
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:milindkanbargi@klebcahubli.in"));
                try {
                    startActivity(Intent.createChooser(intent, "Send email with"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "No email client found.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        github2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInChrome("https://github.com/Milind-Kanbargi");
            }
        });

        return v;
    }

    private void openLinkInChrome(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (Exception e) {
            // Chrome is not installed or not available
            // Fallback to default browser
            intent.setPackage(null);
            startActivity(intent);
        }
    }

}