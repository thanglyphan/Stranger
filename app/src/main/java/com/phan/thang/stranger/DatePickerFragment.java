package com.phan.thang.stranger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by thang on 15.08.2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private String age;
    TextView showDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        Bundle bundle = this.getArguments();
        this.age = bundle.getString("age");
        System.out.println(age);

        this.showDate = (TextView)getActivity().findViewById(R.id.showMyDate2);

        String[] separated = age.split("-");
        separated[0] = separated[0].trim();
        separated[1] = separated[1].trim();
        separated[2] = separated[2].trim();

        final Calendar c = Calendar.getInstance();
        int year = Integer.parseInt(separated[2]);
        int month = Integer.parseInt(separated[1]);
        int day = Integer.parseInt(separated[0]);

        System.out.println(year + " YEAR");
        System.out.println(month + " MONTH");
        System.out.println(day + " DAY");

        return new DatePickerDialog(getActivity(), this, year, (month - 1), day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        showDate.setText("" + day + "-" + (month + 1) + "-" + year);
    }
}