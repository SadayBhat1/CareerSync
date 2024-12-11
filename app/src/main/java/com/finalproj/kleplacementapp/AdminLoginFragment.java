package com.finalproj.kleplacementapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginFragment extends Fragment {
    EditText adminname, adminpassword;
    Button adminlogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);

        adminname = view.findViewById(R.id.adminname);
        adminpassword = view.findViewById(R.id.adminpassword);
        adminlogin = view.findViewById(R.id.adminlogin);

        adminname.setSingleLine(true);

        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLoggedIn();
            }
        });

        return view;
    }

    private void adminLoggedIn() {
        String adname = "Admin";
        String adpas = "Admin";
        if (adminname.getText().toString().equals(adname) && adminpassword.getText().toString().equals(adpas)) {
            Intent intent2 = new Intent(getActivity(), AdminHome.class);
            startActivity(intent2);
        } else {
            Toast.makeText(getActivity(), "Wrong Admin Name or Password!", Toast.LENGTH_SHORT).show();
        }
    }
}
