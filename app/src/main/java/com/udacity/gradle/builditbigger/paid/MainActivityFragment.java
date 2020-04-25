package com.udacity.gradle.builditbigger.paid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.R;

import static android.view.View.GONE;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        mAdView.setVisibility(GONE);

        return root;
    }
}
