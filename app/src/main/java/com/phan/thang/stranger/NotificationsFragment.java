package com.phan.thang.stranger;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.gson.Gson;

/**
 * Created by thang on 14.08.2016.
 */
public class NotificationsFragment extends Fragment{
    public Switch all;
    public Switch vibrate;
    public Switch sound;
    public Switch notification;
    public SharedPreferences prefs;
    private User user;
    private FDatabase fD;
    private Typeface tf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notificationview, container, false);
        this.tf = Typeface.createFromAsset(getContext().getAssets(), String.format("fonts/%s", "Dense.otf"));

        prefs = getActivity().getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);

        this.user = getUser();
        fD = new FDatabase();

        all = (Switch)view.findViewById(R.id.allEnableSwitch);
        all.setTypeface(tf);
        all.setTextSize(23);
        all.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_all, 0, 0, 0);
        vibrate = (Switch)view.findViewById(R.id.vibrateSwitch);
        vibrate.setTypeface(tf);
        vibrate.setTextSize(23);
        vibrate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vibrate, 0 ,0 ,0);
        sound = (Switch)view.findViewById(R.id.soundSwitch);
        sound.setTypeface(tf);
        sound.setTextSize(23);
        sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.volume_high, 0, 0, 0);
        notification = (Switch)view.findViewById(R.id.notificationSwitch);
        notification.setTypeface(tf);
        notification.setTextSize(23);
        notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bell_ring_outline,0 ,0 ,0);

        if(user.sound.equals("On")){
            sound.toggle();
        }
        if(user.vibrate.equals("On")){
            vibrate.toggle();
        }
        if(user.notification.equals("On")){
            notification.toggle();
        }

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!vibrate.isChecked()){
                        vibrate.toggle();
                        user.vibrate = "On";
                        saveSettings();
                        fD.addVibrate("On", user.UID);
                    }
                    if(!sound.isChecked()){
                        sound.toggle();
                        user.sound = "On";
                        saveSettings();
                        fD.addVibrate("On", user.UID);
                    }
                    if(!notification.isChecked()){
                        notification.toggle();
                        user.notification = "On";
                        saveSettings();
                        fD.addNotification("On", user.UID);
                    }
                }
            }
        });
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    user.notification = "On";
                    saveSettings();
                    fD.addNotification("On", user.UID);
                }else{
                    user.notification = "Off";
                    saveSettings();
                    fD.addNotification("Off", user.UID);
                }
            }
        });

        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    user.vibrate = "On";
                    saveSettings();
                    fD.addVibrate("On", user.UID);
                }else{
                    user.vibrate = "Off";
                    saveSettings();
                    fD.addVibrate("Off", user.UID);
                }
            }
        });

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    user.sound = "On";
                    saveSettings();
                    fD.addSound("On", user.UID);
                }else{
                    user.sound = "Off";
                    saveSettings();
                    fD.addSound("Off", user.UID);
                }
            }
        });
        return view;
    }

    private void saveSettings(){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefs.edit().putString("user", json).apply();
    }

    private User getUser(){
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User u = gson.fromJson(json, User.class);
        return u;
    }
}