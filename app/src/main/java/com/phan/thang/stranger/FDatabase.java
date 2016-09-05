package com.phan.thang.stranger;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StreamDownloadTask;

/**
 * Created by thang on 13.08.2016.
 */
public class FDatabase {
    private DatabaseReference mDatabase;

    public FDatabase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public boolean addNewUser(String name, String gender, String age, String uid, String role, String firebaseToken) {
        User user = new User(name, gender, age, uid, role, firebaseToken);

        mDatabase.child("users").child(uid).setValue(user);
        return true;
    }
    public boolean updateUser(String name, String gender, String age, String uid, String role, String firebaseToken){

        mDatabase.child("users").child(uid).child("name").setValue(name);
        mDatabase.child("users").child(uid).child("gender").setValue(gender);
        mDatabase.child("users").child(uid).child("age").setValue(age);
        return true;
    }

    public boolean addLoveNotes(User a, String city, String username, String age, String message, String date, String uid) {
        LoveNote love = new LoveNote(city, username, age, message, date, uid);

        mDatabase.child("lovenotes").child(a.UID).setValue(love);
        return true;
    }
    public boolean deleteLoveNote(String uid) {
        mDatabase.child("lovenotes").child(uid).setValue(null);
        return true;
    }

    public boolean addVibrate(String a, String uid){
        mDatabase.child("users").child(uid).child("vibrate").setValue(a);
        return true;
    }
    public boolean addSound(String a, String uid){
        mDatabase.child("users").child(uid).child("sound").setValue(a);
        return true;
    }
    public boolean addNotification(String a, String uid){
        mDatabase.child("users").child(uid).child("notification").setValue(a);
        return true;
    }
    public boolean addNotificationCount(int a, String uid){
        mDatabase.child("users").child(uid).child("notificationCount").setValue(a);
        return true;
    }


}
