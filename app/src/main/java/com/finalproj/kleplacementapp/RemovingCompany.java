package com.finalproj.kleplacementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class RemovingCompany extends AppCompatActivity {

    EditText companyname,companyimagename;
    Button removeCompanyButton;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removing_company);

        companyname = findViewById(R.id.companyname);
        removeCompanyButton = findViewById(R.id.removeCompanyButton);
        companyname.setSingleLine(true);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Company");

        removeCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyName = companyname.getText().toString().trim();
                if (companyName.isEmpty()) {
                    Toast.makeText(RemovingCompany.this, "Please enter a company name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Delete the data from Firebase Realtime Database
                Query query = mDatabaseRef.orderByChild("name").equalTo(companyName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                            Toast.makeText(RemovingCompany.this, "Company removed from list", Toast.LENGTH_SHORT).show();
                                            companyname.setText("");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle any errors while deleting file
                                            Toast.makeText(RemovingCompany.this, "Failed to remove company file", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors while deleting data
                        Toast.makeText(RemovingCompany.this, "Failed to remove company from list", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RemovingCompany.this, AdminHome.class);
        startActivity(i);
    }
}
