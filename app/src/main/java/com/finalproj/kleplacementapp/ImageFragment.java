package com.finalproj.kleplacementapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ImageFragment extends Fragment {

    private static final String ARG_IMAGE_RES_ID = "arg_image_res_id";

    public static ImageFragment newInstance(int imageResId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);

        Bundle args = getArguments();
        if (args != null) {
            int imageResId = args.getInt(ARG_IMAGE_RES_ID);
            imageView.setImageResource(imageResId);
        }

        return view;
    }
}
