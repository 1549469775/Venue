package com.example.jhon.venue.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by John on 2017/3/24.
 */

public class FragmentTest extends Fragment {

    public static FragmentTest newInstance(String tag) {

        Bundle args = new Bundle();

        FragmentTest fragment = new FragmentTest();
        args.putString("name",tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv=new TextView(getContext());
        tv.setText(getArguments().getString("name"));
        return tv;
    }
}
