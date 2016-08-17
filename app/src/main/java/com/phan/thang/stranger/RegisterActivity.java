package com.phan.thang.stranger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private FDatabase fD;
    private String androidId;
    private String name;
    private String gender;
    private String age;
    private String role = "user";


    private int mYear;
    private int mMonth;
    private int mDay;

    private TextView mDateDisplay;
    private Button mPickDate;
    private String firebaseToken;

    static final int DATE_DIALOG_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fD = new FDatabase();

        Bundle extras = getIntent().getExtras();
        this.androidId = extras.getString("id");
        this.firebaseToken = extras.getString("token");

        mDateDisplay = (TextView) findViewById(R.id.showMyDate);
        mPickDate = (Button) findViewById(R.id.myDatePickerButton);

        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();

        //fD.addNewUser(name, gender, age, androidId, role);
    }

    public void updateDisplay() {
        this.mDateDisplay.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear).append(" "));
    }

    public DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
    public void registerAll(View view){
        EditText nameInput = (EditText)findViewById(R.id.nameInput);
        this.name = nameInput.getText().toString();

        RadioButton rMale = (RadioButton)findViewById(R.id.radioMale);
        if(rMale.isChecked()){
            this.gender = "Male";
        }else{
            this.gender = "Female";
        }
        TextView dayOfBirth = (TextView)findViewById(R.id.showMyDate);
        this.age = dayOfBirth.getText().toString();

        fD.addNewUser(name, gender, age, androidId, role, firebaseToken);
        Intent menuActivity = new Intent(RegisterActivity.this, MenuActivity.class);
        menuActivity.putExtra("id", androidId);
        startActivity(menuActivity);
        RegisterActivity.this.finish();
    }
}
