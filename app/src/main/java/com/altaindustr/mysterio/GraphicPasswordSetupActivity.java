package com.altaindustr.mysterio;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.altaindustr.mysterio.graphicPassDrawer.DrawView;

public class GraphicPasswordSetupActivity extends AppCompatActivity implements GraphicPasswordHelper
{
    View draw;

    public SharedPreferences mySharedPreferences;
    public SharedPreferences.Editor editor;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_password_setup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int)(displayMetrics.heightPixels*0.9);
        int width = (int)(displayMetrics.widthPixels*0.9);

        draw = new DrawView(this, this);
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(width, width);
        imageViewLayoutParams.setMargins((int)(width*0.05),(int)(width*0.1),
                (int)(width*0.05), 0);
        draw.setLayoutParams(imageViewLayoutParams);

        LinearLayout mainlayer = (LinearLayout) findViewById(R.id.lin_lay_pass);
        mainlayer.addView(draw);

        mySharedPreferences = getSharedPreferences(getResources().getString(R.string.APP_PREFERENCES)
                , Context.MODE_PRIVATE);
        editor = mySharedPreferences.edit();

    }


    @Override
    public void updateGraphicPassword(String passKey)
    {

    }

    @Override
    public void saveGraphicPassword(String passKey)
    {
        if (passKey.length()<=3)
        {
            Toast.makeText(this, "Password is too short. At least 4 symbols.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            editor.putString(getResources().getString(R.string.pref_graphic_pass), passKey);
            editor.apply();
            Toast.makeText(this, "Password saved.", Toast.LENGTH_SHORT).show();

        }
    }
}
