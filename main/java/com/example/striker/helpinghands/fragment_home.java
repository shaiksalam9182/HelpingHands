package com.example.striker.helpinghands;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class fragment_home extends Fragment {

    private final Handler handler = new Handler();
    Animation animation;
    int count=0;
    ViewFlipper vf;
    ImageView vanim1,vanim2;
    public static fragment_home newInstance() {
        fragment_home fragment = new fragment_home();
        return fragment;
    }

    public fragment_home() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        return rootView;
    }
}