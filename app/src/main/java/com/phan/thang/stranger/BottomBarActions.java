package com.phan.thang.stranger;

import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by thang on 25.08.2016.
 */
public class BottomBarActions {
    private View v;
    private BottomBar bottomBar;
    private FragmentManager ftM;

    public BottomBarActions(View v){
        this.v = v;
    }

    public void init(FragmentManager ft, final int id){
        bottomBar = (BottomBar) v.findViewById(R.id.bottomBar);
        this.ftM = ft;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_back: goPreviousFragment(id); break;
                    case R.id.tab_send: System.out.println("Send pressed"); break;
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_nothing: System.out.println("Nothing here!"); break;
                }
            }
        });
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
