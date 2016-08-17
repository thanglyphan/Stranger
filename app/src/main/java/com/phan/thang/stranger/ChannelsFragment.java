package com.phan.thang.stranger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

/**
 * Created by thang on 14.08.2016.
 */
public class ChannelsFragment extends Fragment {
    public SharedPreferences prefs;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channelsview, container, false);
        return view;

    }

    private User getUser(){
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User u = gson.fromJson(json, User.class);
        return u;
    }

    public void notificationSender(){
        prefs = getActivity().getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);
        user = getUser();

        NotificationSender pling = new NotificationSender();

        pling.sendNotificationTo(this.user.firebaseToken, getActivity().getString(R.string.firebaseId), getActivity().getString(R.string.fireBaseApi));

    }

}