package com.altaindustr.mysterio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity
{
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;
    EditText password;
    String sLockers="";

    boolean isAtLeastOneSelected=true;
    CheckBox useText, useGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mySharedPreferences = getSharedPreferences(getResources().getString(R.string.APP_PREFERENCES)
                , Context.MODE_PRIVATE);
        sLockers = mySharedPreferences.getString(getResources().getString(R.string.pref_launch_lockers), "");

        editor = mySharedPreferences.edit();
        password = (EditText)findViewById(R.id.password_et);

        useText = (CheckBox)findViewById(R.id.chb_use_text);
        useGraph = (CheckBox)findViewById(R.id.chb_use_graph);

        if (sLockers.contains("1"))
            useText.setChecked(true);
        if (sLockers.contains("2"))
            useGraph.setChecked(true);

        useText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!useText.isChecked() && !useGraph.isChecked())
                {
                    useText.setChecked(true);
                    Toast.makeText(WelcomeActivity.this,"You must select at least one option", Toast.LENGTH_SHORT).show();
                }
            }
        });
        useGraph.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (useGraph.isChecked() &&
                        !mySharedPreferences.contains(getResources().getString(R.string.pref_graphic_pass)))
                {
                    startActivity(new Intent(WelcomeActivity.this, GraphicPasswordSetupActivity.class));
                }
                if (!useText.isChecked() && !useGraph.isChecked())
                {
                    useGraph.setChecked(true);
                    Toast.makeText(WelcomeActivity.this,"You must select at least one option", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(mySharedPreferences.getBoolean(getResources().getString(R.string.pref_is_first_launch), true))
        {
            useText.setChecked(true);
            useGraph.setChecked(false);
            saveLockers();
        }


        showHelpDialog("Welcome", getResources().getString(R.string.welcome_text));
    }

    @Override
    public void onBackPressed()
    {

    }
    public void showHelpDialog(String title, String text)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setTitle(title)
                .setMessage(text)
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setNegativeButton("Ok",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showPasswordHelp(View v)
    {
        showHelpDialog("Help", "This Password will be used to enter app");
    }
    public void savePassword(View v)
    {
        editor.putString(TextPasswordActivity.APP_PREFERENCES_TEXT_PASSWORD,
                password.getText().toString());
        editor.putBoolean(getResources().getString(R.string.pref_is_first_launch), false);
        editor.apply();
        saveLockers();

        Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show();
    }

    public void saveLockers()
    {
        sLockers="";
        if (useText.isChecked())
        {
            sLockers+="1";
        }
        if (useGraph.isChecked())
        {
            sLockers+="2";
        }

        editor.putString(getResources().getString(R.string.pref_launch_lockers), sLockers);
        editor.apply();
    }

    public void setupGraphicPassword(View v)
    {
        startActivity(new Intent(WelcomeActivity.this, GraphicPasswordSetupActivity.class));
    }
}





















