package com.phan.thang.stranger;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;

import java.util.Locale;

/**
 * Created by thang on 25.08.2016.
 */
public class AddDateFragment extends Fragment {

    private EditText inputTxt;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String city;
    private FDatabase fDatabase;
    private SharedPreferences prefs;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addingview, container, false);
        this.fDatabase = new FDatabase();
        this.user = getUser();
        this.inputTxt = (EditText)view.findViewById(R.id.addInput);

        //Initiate all needed things.
        this.city = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("City", "RackCityBitch");
        BottomBarActions btmBar = new BottomBarActions(view, user, fDatabase, inputTxt, city);
        btmBar.init(getFragmentManager(), R.id.add_frame);
        //End initiate

        return view;
    }

    //TODO: Actionlistener on "Send" button.

    private User getUser(){
        prefs = getActivity().getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User u = gson.fromJson(json, User.class);
        return u;
    }
}
