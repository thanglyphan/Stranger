package com.phan.thang.stranger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class MenuActivity extends ActionBarActivity implements ActionBar.TabListener{
    private String androidId;
    private DatabaseReference mDatabase;
    private ProgressDialog pg;

    private ViewPager tabsviewPager;
    private Tabsadapter mTabsAdapter;
    private User user;
    private String tokenFirebase;

    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //Set custom font.
        SpannableString s = new SpannableString("Stranger");
        s.setSpan(new com.phan.thang.stranger.TypefaceSpan(this, "Bellico.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        //End custom font.

        Bundle extras = getIntent().getExtras();
        androidId = extras.getString("id");

        tokenFirebase = FirebaseInstanceId.getInstance().getToken();
        System.out.println(tokenFirebase);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        prefs = this.getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tryLogin(androidId, pg);

        initializeTabs();
    }
    private void setUser(User user){
        if(user != null){
            Gson gson = new Gson();
            user.firebaseToken = tokenFirebase;
            String json = gson.toJson(user);
            prefs.edit().putString("user", json).apply();
            pg.hide();
            pg.dismiss();
        }else{
            Intent register = new Intent(MenuActivity.this, RegisterActivity.class);
            register.putExtra("id", androidId);
            register.putExtra("token", tokenFirebase);
            pg.hide();
            pg.dismiss();
            startActivity(register);
            MenuActivity.this.finish();
        }
    }

    private void tryLogin(final String uid, final ProgressDialog pg){
        activateProgressBar();
        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                setUser(user);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LOL", "getUser:onCancelled", databaseError.toException());
            }
        });
    }

    private void activateProgressBar(){
        this.pg = new ProgressDialog(this);
        this.pg.setMessage("Loading...");
        this.pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.pg.setCancelable(false);
        this.pg.show();
    }

    public void initializeTabs(){
        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);
        tabsviewPager.setOffscreenPageLimit(2);

        mTabsAdapter = new Tabsadapter(getSupportFragmentManager());

        tabsviewPager.setAdapter(mTabsAdapter);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab channeltab = getSupportActionBar().newTab().setTabListener(this);
        ActionBar.Tab settingstab = getSupportActionBar().newTab().setTabListener(this);
        ActionBar.Tab accounttab = getSupportActionBar().newTab().setTabListener(this);
        channeltab.setIcon(R.drawable.ic_home_black_24dp);
        settingstab.setIcon(R.drawable.ic_settings_black_24dp);
        accounttab.setIcon(R.drawable.ic_account_box_black_24dp);

        getSupportActionBar().addTab(channeltab);
        getSupportActionBar().addTab(accounttab);
        getSupportActionBar().addTab(settingstab);

        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                getSupportActionBar().setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        tabsviewPager.setCurrentItem(tab.getPosition());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
