package com.phan.thang.stranger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by thang on 14.08.2016.
 */
public class ChannelsFragment extends Fragment {
    public SharedPreferences prefs;
    private User user;
    public TextView testTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channelsview, container, false);
        notificationSend();
        testTxt = (TextView)view.findViewById(R.id.testTxt);
        user = getUser();

        if(user!=null){
            testTxt.setText(user.notificationCount + "");
        }
        return view;

    }

    private User getUser(){
        prefs = getActivity().getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User u = gson.fromJson(json, User.class);
        return u;
    }

    public void notificationSend(){
        prefs = getActivity().getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);
        user = getUser();

        NotificationSender pling = new NotificationSender();

        pling.sendNotificationTo(this.user.firebaseToken, getActivity().getString(R.string.firebaseId), getActivity().getString(R.string.fireBaseApi));

    }

}