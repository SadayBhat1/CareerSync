package com.finalproj.kleplacementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudentLoginFragment extends Fragment {

    EditText studentidL,studentpasswordL;
    Button studentlogin;
    String sname1="";
    FirebaseDatabase database;
    DatabaseReference students;
    public final static String EXTRA_MESSAGE="" ;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_login, container, false);


        studentidL=view.findViewById(R.id.studentidL);
        studentpasswordL=view.findViewById(R.id.studentpasswordL);
        studentlogin=view.findViewById(R.id.studentlogin);

        //Database reference

        database= FirebaseDatabase.getInstance();
        students=database.getReference("STUDENTS");
        studentidL.setSingleLine(true);

        studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentLoggedIn();
            }
        });

        return view;
    }

    public void StudentLoggedIn() {
        final String sname2 = studentidL.getText().toString().trim();
        final String spwd2 = studentpasswordL.getText().toString().trim();

        students.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(sname2).exists()) {
                    if (!sname2.isEmpty()) {
                        if (!spwd2.isEmpty()) {
                            Student s1 = dataSnapshot.child(sname2).getValue(Student.class);
                            if (s1.getSid().equals(sname2)) {
                                if (s1.getSpassword().equals(spwd2)) {
                                    sname1 = s1.getSname();
                                    Intent intent = new Intent(getActivity(), StudentHome.class);
                                    intent.putExtra(EXTRA_MESSAGE, sname1);
                                    startActivity(intent);
                                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_LONG).show();
                                }
                                else if(!s1.getSpassword().equals(spwd2) || !s1.getSid().equals(sname2)) {
                                    Toast.makeText(getActivity(), "Wrong Student ID or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else
                            Toast.makeText(getActivity(), "Password required", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), "Student ID required", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}