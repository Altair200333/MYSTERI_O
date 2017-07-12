package com.altaindustr.mysterio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LaunchingLockersActivity extends AppCompatActivity
{
    SharedPreferences mySharedPreferences;
    String sLocker="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching_lockers);

        mySharedPreferences = getSharedPreferences(getResources().getString(R.string.APP_PREFERENCES)
                , Context.MODE_PRIVATE);
        boolean is_first_launch=mySharedPreferences.getBoolean(getResources().getString(R.string.pref_is_first_launch),true);
        if(is_first_launch)
        {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
        else
        {
            sLocker = mySharedPreferences.getString(getResources().getString(R.string.pref_launch_lockers), "");
            Log.e("w",sLocker);
            if (sLocker.startsWith("1"))
            {
                startActivity(new Intent(this, TextPasswordActivity.class));
            }
            else if(sLocker.startsWith("2"))
            {
                startActivity(new Intent(this, GraphicPasswordEnterActivity.class));
            }
        }
    }
}
