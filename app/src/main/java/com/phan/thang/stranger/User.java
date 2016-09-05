package com.phan.thang.stranger;

import android.widget.Switch;

import se.simbio.encryption.Encryption;

/**
 * Created by thang on 13.08.2016.
 */
public class User {
    public String name;
    public String gender;
    public String age;
    public String UID;
    public String role;
    public String vibrate;
    public String sound;
    public String notification;
    public String firebaseToken;
    public int notificationCount;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String gender, String age, String uid, String role, String firebaseToken) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.UID = uid;
        this.role = role;
        this.firebaseToken = firebaseToken;
        this.vibrate = "On";
        this.sound = "On";
        this.notification = "On";
        this.notificationCount = 0;
    }

    public String toString(){
        return " Name: " + name + " \n Gender: " + gender + "\n Age: " + age + "\n UID: " + UID + "\n Role: " + role + "\n Vibrate: " + vibrate + "\n Sound: " + sound + "\n Notifications: " + notification + "\n Notification count: " + notificationCount;
    }


}
