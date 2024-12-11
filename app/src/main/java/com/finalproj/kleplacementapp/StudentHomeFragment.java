package com.finalproj.kleplacementapp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StudentHomeFragment extends Fragment {

    TextView welcomestudent;
    public static final int PICK_IMAGE = 2;
    public Uri resumeUri;
    EditText editresumetext;
    Button resumeBuildbutton, browseresumebutton, uploadresumebutton;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        welcomestudent = v.findViewById(R.id.welcomestudent);
        editresumetext = v.findViewById(R.id.editresumetext);
        resumeBuildbutton = v.findViewById(R.id.resumeBuildbutton);
        browseresumebutton = v.findViewById(R.id.browseresumebutton);
        uploadresumebutton = v.findViewById(R.id.uploadresumebutton);

        browseresumebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowseResume();
            }
        });

        uploadresumebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addresume();
            }
        });

        resumeBuildbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInChrome("https://docs.google.com/templates?q=resume");
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadResume");
        String sname = StudentHome.message;
        if (sname != null) {
            welcomestudent.append(" " + sname);
        }

        super.onViewCreated(view, savedInstanceState);
    }

    public void BrowseResume() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select PDF file"), PICK_IMAGE);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressLint("Range")
    private String getFileNameFromUri(ContentResolver contentResolver, Uri uri) {
        String fileName = null;
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            resumeUri = data.getData();
            if (resumeUri != null) {
                String fileName = getFileNameFromUri(getActivity().getContentResolver(), resumeUri);
                editresumetext.setText(fileName);
            } else {
                Toast.makeText(getActivity(), "Failed to get file URI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Addresume() {
        if (resumeUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference reference = storageReference.child("uploadResume/" + System.currentTimeMillis() + ".pdf");
            reference.putFile(resumeUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                            Uri url = uri.getResult();
                            Toast.makeText(getActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Please select a file", Toast.LENGTH_SHORT).show();
        }
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
