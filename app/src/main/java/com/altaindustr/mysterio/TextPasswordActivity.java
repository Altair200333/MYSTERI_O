package com.altaindustr.mysterio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TextPasswordActivity extends AppCompatActivity
{

    public static final String APP_PREFERENCES_TEXT_PASSWORD = "TEXT_PASSWORD";
    SharedPreferences mySharedPreferences;
    EditText password;
    SharedPreferences.Editor editor;
    long DenyTime = 15000;
    int incorrect_count = 0;
    String sLocker="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_password);

        //initializing preferences
        mySharedPreferences = getSharedPreferences(getResources().getString(R.string.APP_PREFERENCES)
                , Context.MODE_PRIVATE);
        editor = mySharedPreferences.edit();

        password = (EditText)findViewById(R.id.text_password_et);
        if (mySharedPreferences.getBoolean(getResources().getString(R.string.pref_is_first_launch), true))
        {
            startActivity(new Intent(this, WelcomeActivity.class));
        }

        sLocker = mySharedPreferences.getString(getResources().getString(R.string.pref_launch_lockers), "");

    }

    public void enterPassword(View v)
    {
            if (mySharedPreferences.getBoolean(getResources().getString(R.string.pref_is_first_launch), true) == false)
            {
                if ((Calendar.getInstance().getTimeInMillis()-mySharedPreferences.getLong("DENY_TIME", 0))>DenyTime && incorrect_count>=3)
                {
                    incorrect_count=0;
                }
                if (password.getText().toString().equals(
                        mySharedPreferences.getString(APP_PREFERENCES_TEXT_PASSWORD, null)) && incorrect_count<3)
                {
                    if (sLocker.contains("2"))
                    {
                        startActivity(new Intent(TextPasswordActivity.this, GraphicPasswordEnterActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                }
                else
                {
                    if (incorrect_count<3)
                    {
                        incorrect_count++;
                        if (incorrect_count==3)
                        {
                            editor.putLong("DENY_TIME",Calendar.getInstance().getTimeInMillis());
                            editor.commit();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Acces denied. Deny time:"
                                        + (DenyTime -(Calendar.getInstance().getTimeInMillis()-mySharedPreferences.getLong("DENY_TIME", 0)))
                                , Toast.LENGTH_SHORT).show();
                    }

                }
            }
            else
            {
                Toast.makeText(this, "First Launch", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TextPasswordActivity.this, WelcomeActivity.class));
            }
    }
}


