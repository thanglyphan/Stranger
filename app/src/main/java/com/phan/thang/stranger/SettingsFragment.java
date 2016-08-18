package com.phan.thang.stranger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thang on 14.08.2016.
 */

public class SettingsFragment extends Fragment {
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settingsview, container, false);
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.settings_list));

        lv = (ListView) view.findViewById(R.id.lv_settings);
        CustomListAdapter my_adapter = new CustomListAdapter(getActivity().getApplicationContext(), R.layout.custom_textview_listview, items, "Dense.otf");

        lv.setAdapter(my_adapter);//new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.custom_textview_listview, items));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0: nextFragment(new ProfileFragment()); break;
                    case 1: nextFragment(new TermsFragment()); break;
                    case 2: nextFragment(new NotificationsFragment()); break;
                    case 3: nextFragment(new ContactUsFragment()); break;
                }
            }
        });
        System.out.println("Before");

        return view;
    }

    private void nextFragment(Fragment a){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.settings_frame, a);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }
}