package com.phan.thang.stranger;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.api.model.GetAccountInfoUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thang on 14.08.2016.
 */
public class ChannelsFragment extends Fragment {
    public SharedPreferences prefs;
    private User user;
    public TextView testTxt;
    public ImageButton addBtn;
    private DatabaseReference mDatabase;
    private ProgressDialog pg;
    private ListView lv;
    private List<LoveNote> items = new ArrayList<>();
    private String cityname;
    private FDatabase fDatabase;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channelsview, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lv = (ListView)view.findViewById(R.id.lv_channel);
        notificationSend();
        user = getUser();
        this.fDatabase = new FDatabase();

        if(!displayGpsStatus()){
            alertbox();
        }else{
            this.cityname = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("City", "RackCityBitch");
            displayLoveNotes();
        }

        addBtn = (ImageButton)view.findViewById(R.id.addBtn);
        addBtn.setImageDrawable(getResources().getDrawable(R.drawable.plus_circle_outline));
        addBtn.setBackgroundColor(Color.TRANSPARENT);
        addBtn.setAlpha(100);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!displayGpsStatus()){
                    alertbox();
                }else{
                    nextFragment(new AddDateFragment());
                }
            }
        });

        return view;

    }

    private void displayLoveNotes() {
        activateProgressBar();
        mDatabase.child("lovenotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot lovenotes : dataSnapshot.getChildren()) {
                        LoveNote love = lovenotes.getValue(LoveNote.class);
                        String diff = DateDifference.getTimeLeft2(love.dateSent);
                        String[] parts = diff.split("-");
                        int days = Integer.parseInt(parts[0]);
                        int hours = Integer.parseInt(parts[1]);
                        int minutes = Integer.parseInt(parts[2]);
                        if(days >= 1 || hours >= 24 && minutes == 0){
                            fDatabase.deleteLoveNote(love.uid);
                        }
                        if(love.city.equals(cityname)){
                            items.add(love);
                        }
                    }
                }else{
                //TODO: Alertbox or something here displaying that no lovenotes are in the area.
                System.out.println("Nothing to show");
            }
            CustomListAdapterChannel my_adapter = new CustomListAdapterChannel(getActivity().getApplicationContext(), R.layout.custom_textview_listview, items, "SansSerifBookFLF.otf");

            lv.setAdapter(my_adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    switch (i){
                        //TODO: Do something cool here, when clicked!
                        default: System.out.println("Sup bitch");
                    }
                }
            });
            pg.hide();
            pg.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void activateProgressBar(){
        this.pg = new ProgressDialog(getContext());
        this.pg.setMessage("Loading...");
        this.pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.pg.setCancelable(false);
        this.pg.show();
    }

    protected void alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your Device's GPS is disabled")
                .setCancelable(false)
                .setTitle("Enable GPS")
                .setPositiveButton("Gps On", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(myIntent);
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {dialog.cancel();}
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    return true;

                }

                return false;
            }
        });
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

        if(user != null){
            pling.sendNotificationTo(this.user.firebaseToken, getActivity().getString(R.string.firebaseId), getActivity().getString(R.string.fireBaseApi));

        }

    }

}