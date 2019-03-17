package com.github.simonpham.sunshine.ui.detail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.simonpham.sunshine.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by Simon Pham on 3/10/19.
 * Email: simonpham.dn@gmail.com
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
