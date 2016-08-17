package com.phan.thang.stranger;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;



public class MainActivity extends AppCompatActivity {

    private String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                androidId = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

                Intent nextActivity = new Intent(MainActivity.this, MenuActivity.class);
                nextActivity.putExtra("id", androidId);
                startActivity(nextActivity);
                MainActivity.this.finish();

            }
        }, 2000);
    }



}
