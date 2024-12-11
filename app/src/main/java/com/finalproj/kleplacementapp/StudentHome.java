package com.finalproj.kleplacementapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHome extends FragmentActivity implements NotificationFragment.NotificationCallback {
    TextView welcomestudent;
    public static String message;
    BottomNavigationView bottomNav;
    BadgeDrawable badgeDrawable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);


        welcomestudent = findViewById(R.id.welcomestudent);
        Intent intent = getIntent();
        message = intent.getStringExtra(StudentLoginFragment.EXTRA_MESSAGE);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Replace the NotificationFragment with the HomeFragment initially
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudentHomeFragment()).commit();
        badgeDrawable = bottomNav.getOrCreateBadge(R.id.nav_notifications);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(0); // Initialize badge count to 0

        // Pass the callback to the NotificationFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, NotificationFragment.newInstance(this))
                .commit();

        // Set the HomeFragment as the initially selected item in the bottom navigation
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StudentHome.this, MainActivity.class);
        startActivity(i);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new StudentHomeFragment();
                            break;
                        case R.id.nav_company:
                            selectedFragment = new CompanyFragment();
                            break;
                        case R.id.nav_papers:
                            selectedFragment = new PapersFragment();
                            break;
                        case R.id.nav_notifications:
                            selectedFragment = new NotificationFragment();
                            // Hide the badge when notification option is selected
                            hideNotificationBadge();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    // Callback method from NotificationFragment
    @Override
    public void onNotificationCountUpdated(int count) {
        updateNotificationBadgeCount(count);
    }

    private void updateNotificationBadgeCount(int count) {
        if (badgeDrawable != null) {
            badgeDrawable.setNumber(count);
        }
    }

    private void hideNotificationBadge() {
        if (badgeDrawable != null) {
            badgeDrawable.setVisible(false);
            badgeDrawable.clearNumber();
        }
    }
}
