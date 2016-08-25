package com.phan.thang.stranger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.api.model.GetAccountInfoUser;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by thang on 14.08.2016.
 */
public class ChannelsFragment extends Fragment {
    public SharedPreferences prefs;
    private User user;
    public TextView testTxt;
    public ImageButton addBtn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channelsview, container, false);

        notificationSend();
        testTxt = (TextView)view.findViewById(R.id.testTxt);
        user = getUser();

        addBtn = (ImageButton)view.findViewById(R.id.addBtn);
        addBtn.setImageDrawable(getResources().getDrawable(R.drawable.plus_circle_outline));
        addBtn.setBackgroundColor(Color.TRANSPARENT);
        addBtn.setAlpha(100);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button was clicked");
                nextFragment(new AddDateFragment());
            }
        });


        if(user!=null){
            testTxt.setText(user.notificationCount + "");
        }
        return view;

    }

    private void nextFragment(Fragment a){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.channel_frame, a);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
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