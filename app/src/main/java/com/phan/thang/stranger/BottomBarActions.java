package com.phan.thang.stranger;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Date;
import java.util.Locale;

/**
 * Created by thang on 25.08.2016.
 */
public class BottomBarActions {
    private View v;
    private BottomBar bottomBar;
    private FragmentManager ftM;
    private User user;
    private FDatabase fDatabase;
    private EditText inputTxt;
    private String city;
    private int ids;
    private ProgressDialog pg;
    private DatabaseReference mDatabase;

    public BottomBarActions(View v, User user, FDatabase fDatabase, EditText inputTxt, String city) {
        this.v = v;
        this.user = user;
        this.fDatabase = fDatabase;
        this.inputTxt = inputTxt;
        this.city = city;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void init(FragmentManager ft, final int id){
        this.ids = id;
        bottomBar = (BottomBar) v.findViewById(R.id.bottomBar);
        this.ftM = ft;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_back: goPreviousFragment(id); break;
                    case R.id.tab_send: addTheLoveNote(); break;
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_send: addTheLoveNote(); break;
                    case R.id.tab_nothing: System.out.println("Nothing here!"); break;
                }
            }
        });
    }


    private void activateProgressBar(){
        this.pg = new ProgressDialog(v.getContext());
        this.pg.setMessage("Loading...");
        this.pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.pg.setCancelable(false);
        this.pg.show();
    }
    @TargetApi(24)
    private void addTheLoveNote(){
        final String username = user.name;
        final String inputTxtYes = inputTxt.getText().toString();

        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        final String date = today.monthDay + "-" + today.month + "-" + today.year + " " + today.format("%H:%M:%S");
        //TODO: Need love note uid and date for that.

        activateProgressBar();
        mDatabase.child("lovenotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot lovenotes : dataSnapshot.getChildren()) {
                        LoveNote love = lovenotes.getValue(LoveNote.class);
                        if(!love.uid.equals(user.UID)){
                            fDatabase.addLoveNotes(user, city, username, user.age, inputTxtYes, date, user.UID);
                            goPreviousFragment(ids);
                            Toast.makeText(v.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                        }else{
                            alertbox();
                            break;
                        }
                    }
                }else{
                    //TODO: Alertbox or something here displaying that no lovenotes are in the area.
                    fDatabase.addLoveNotes(user, city, username, user.age, inputTxtYes, date, user.UID);
                    goPreviousFragment(ids);
                    Toast.makeText(v.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                }

                pg.hide();
                pg.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    protected void alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("You have posted a note for less than 24 hours. Please get a date in the meantime :)")
                .setCancelable(false)
                .setTitle("Sorry!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {dialog.cancel();}
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goPreviousFragment(final int id){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                bottomBar.setVisibility(View.GONE);
                FragmentTransaction ft = ftM.beginTransaction();
                ft.replace(id, new ChannelsFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        }, 200);

    }


}
