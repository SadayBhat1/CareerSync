package com.finalproj.kleplacementapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private Timer autoSliderTimer;
    private Handler handler;
    private Runnable runnable;

    private static final long SLIDER_DELAY = 3000; // Delay between auto-sliding images (in milliseconds)

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("TPO Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Student Login"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.sidebar_home:
                        closeDrawer(); // Close the drawer
                        navigateToMainScreen(); // Go back to the main activity
                        break;
                    case R.id.website:
                        openLinkInChrome("https://klebcahubli.in/");
                        break;
                    case R.id.feedback:
                        replaceFragment(new FeedbackFragment());
                        break;
                    case R.id.share:
                        shareOption();
                        break;
                    case R.id.developers:
                        replaceFragment(new DevelopersFragment());
                        break;
                }

                // Manually deselect the item
                item.setChecked(false);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Manually deselect the navigation drawer item
                navigationView.getMenu().findItem(R.id.website).setChecked(false);
                navigationView.getMenu().findItem(R.id.feedback).setChecked(false);
                navigationView.getMenu().findItem(R.id.share).setChecked(false);
                navigationView.getMenu().findItem(R.id.developers).setChecked(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        ViewPager2 imageSlider = findViewById(R.id.image_slider);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this);
        imageSlider.setAdapter(imageSliderAdapter);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.placement_pic);
        images.add(R.drawable.placement_pic1);
        images.add(R.drawable.placement_pic2);
        images.add(R.drawable.placement_pic3);
        images.add(R.drawable.placement_pic4);

        for (int image : images) {
            imageSliderAdapter.addImage(image);
        }

        // Auto-slide the images
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = imageSlider.getCurrentItem();
                if (currentItem < imageSliderAdapter.getItemCount() - 1) {
                    imageSlider.setCurrentItem(currentItem + 1);
                } else {
                    imageSlider.setCurrentItem(0);
                }
            }
        };

        autoSliderTimer = new Timer();
        autoSliderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, SLIDER_DELAY, SLIDER_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (autoSliderTimer != null) {
            autoSliderTimer.cancel();
        }
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment);
        fragmentTransaction.commit();
    }

    private void openLinkInChrome(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Chrome is not installed or not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOption() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Check this app out");
        intent.putExtra(Intent.EXTRA_TEXT,"https://drive.google.com/uc?export=download&id=15dcp42hMkot1kAmfhOA5oJa1PgMtic_i");
        try {
            startActivity(Intent.createChooser(intent,"Share Via"));
        } catch (Exception e) {
            Toast.makeText(this, "Chrome is not installed or not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}