package com.phan.thang.stranger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by thang on 18.08.2016.
 */
public class MyAccountFragment extends Fragment {
    public SharedPreferences prefs;
    private User user;
    public TextView testTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accountview, container, false);
        testTxt = (TextView)view.findViewById(R.id.testTxt);

        return view;

    }

}
