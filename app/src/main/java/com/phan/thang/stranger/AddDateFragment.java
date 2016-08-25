package com.phan.thang.stranger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.roughike.bottombar.BottomBar;

/**
 * Created by thang on 25.08.2016.
 */
public class AddDateFragment extends Fragment {

    EditText inputTxt;
    private BottomBar bottomBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addingview, container, false);

        BottomBarActions btmBar = new BottomBarActions(view);

        btmBar.init(getFragmentManager(), R.id.add_frame);



        System.out.println("New VIEW");
        inputTxt = (EditText)view.findViewById(R.id.addInput);
        return view;
    }
}
