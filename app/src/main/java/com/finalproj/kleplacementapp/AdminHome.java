 package com.finalproj.kleplacementapp;

 import android.annotation.SuppressLint;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.cardview.widget.CardView;

 public class AdminHome extends AppCompatActivity {
     CardView addcompay,addnotif,previouspapers,selectedstudents,addstudent,removecompany;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        addstudent=findViewById(R.id.addstudent);
        addcompay=findViewById(R.id.addcompany);
        addnotif= findViewById(R.id.addnotif);
        previouspapers=findViewById(R.id.previouspapers);
        selectedstudents=findViewById(R.id.selectedstudents);
        removecompany=findViewById(R.id.removecompany);

    }

     public void AddCompany(View view) {
         Intent i2 = new Intent(AdminHome.this, AddingCompany.class);
         startActivity(i2);
     }

     public void AddNotification(View view) {
         Intent i62 = new Intent(AdminHome.this, AddingNotification.class);
         startActivity(i62);
     }

     public void PreviousPapers(View view) {
         Intent i2 = new Intent(AdminHome.this, AddingPapers.class);
         startActivity(i2);
     }

     public void SelectedStudents(View view) {
         Intent i2 = new Intent(AdminHome.this, AddingSelected.class);
         startActivity(i2);
     }

     public void AddStudent(View view) {
         Intent intent6 = new Intent(AdminHome.this,AddingNewStudent.class);
         startActivity(intent6);
     }

     /*public void LogoutAdmin(View view) {
         Intent intent7 = new Intent(AdminHome.this,MainActivity.class);
         startActivity(intent7);
     } */

     public void RemoveCompany(View view) {
         Intent intent62 = new Intent(AdminHome.this,RemovingCompany.class);
         startActivity(intent62);
     }
 }
