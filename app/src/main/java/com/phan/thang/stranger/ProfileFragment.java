package com.phan.thang.stranger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

/**
 * Created by thang on 14.08.2016.
 */
public class ProfileFragment extends DialogFragment implements View.OnClickListener{
    private String name;
    private String gender;
    private String age;
    private String UID;
    private String role;
    private String firebaseToken;
    public SharedPreferences prefs;
    private FDatabase fD;
    EditText displayName;
    RadioButton rMale;
    RadioButton rFemale;
    TextView showDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profileview, container, false);
        fD = new FDatabase();
        Button b = (Button) view.findViewById(R.id.regBtn2);
        Button datePick = (Button) view.findViewById(R.id.datePicker2);
        datePick.setOnClickListener(this);
        b.setOnClickListener(this);

        this.displayName = (EditText)view.findViewById(R.id.nameInput2);
        this.rMale = (RadioButton)view.findViewById(R.id.radioMale2);
        this.rFemale = (RadioButton)view.findViewById(R.id.radioFemale2);
        this.showDate = (TextView)view.findViewById(R.id.showMyDate2);


        prefs = getActivity().getApplicationContext().getSharedPreferences("com.phan.thang.stranger", Context.MODE_PRIVATE);
        getUserInfo();
        setFieldsOnLayout();
        return view;
    }

    private void getUserInfo(){
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User user = gson.fromJson(json, User.class);

        this.name = user.name;
        this.gender = user.gender;
        this.age = user.age;
        this.UID = user.UID;
        this.role = user.role;
        this.firebaseToken = user.firebaseToken;
    }
    private void setFieldsOnLayout(){
        displayName.setText(name);

        if(gender.equals("Male")){
            rMale.toggle();
        }else{
            rFemale.toggle();
        }
        showDate.setText(age);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regBtn2:
                name = displayName.getText().toString();
                if(rMale.isChecked()){
                    gender = "Male";
                }else{
                    gender = "Female";
                }
                age = showDate.getText().toString();
                if(fD.updateUser(name, gender, age, UID, role, firebaseToken)){
                    User user = new User(name, gender, age, UID, role, firebaseToken);
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    prefs.edit().putString("user", json).apply();
                    getFragmentManager().popBackStack();
                    Toast.makeText(getActivity().getApplicationContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.datePicker2:
                System.out.println("Datepicker");
                DialogFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("age", age);
                newFragment.setArguments(bundle);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;

        }
    }

}
