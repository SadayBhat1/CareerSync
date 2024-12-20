package com.finalproj.kleplacementapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddingNewStudent extends AppCompatActivity {

    EditText studentname, studentid, studentpassword, studentphno, studentperc;
    String sid2, sname2, spwd2, sphno2, sperc2;
    Button addstudent;
    public static DatabaseReference students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_new_student);
        studentid = findViewById(R.id.studentid);
        studentname = findViewById(R.id.studentname);
        studentpassword = findViewById(R.id.studentpassword);
        studentphno = findViewById(R.id.studentphno);
        studentperc = findViewById(R.id.studentperc);
        addstudent = findViewById(R.id.addstudent);

        studentname.setSingleLine(true);
        studentid.setSingleLine(true);
        studentpassword.setSingleLine(true);
        studentphno.setSingleLine(true);
        studentperc.setSingleLine(true);

        students = FirebaseDatabase.getInstance().getReference("STUDENTS");
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AddingNewStudent.this, AdminHome.class);
        startActivity(i);
    }

    public void StudentAdded(View view) {
        sid2 = studentid.getText().toString().trim();
        sname2 = studentname.getText().toString().trim();
        spwd2 = studentpassword.getText().toString().trim();
        sphno2 = studentphno.getText().toString().trim();
        sperc2 = studentperc.getText().toString().trim();

        if (!TextUtils.isEmpty(sname2) && !TextUtils.isEmpty(sid2) && !TextUtils.isEmpty(spwd2) && !TextUtils.isEmpty(sphno2) && !TextUtils.isEmpty(sperc2)) {
            final Student s = new Student(sname2, spwd2, sid2, sphno2, sperc2);
            students.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(s.getSid()).exists()) {
                        Toast.makeText(AddingNewStudent.this, "Student Already exists ", Toast.LENGTH_SHORT).show();
                    } else {
                        students.child(s.getSid()).setValue(s);
                        Toast.makeText(AddingNewStudent.this, "Student Added ", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(AddingNewStudent.this, AdminHome.class);
                        startActivity(i2);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error if needed
                }
            });
        } else {
            Toast.makeText(this, "All Fields required", Toast.LENGTH_LONG).show();
        }
    }
}
