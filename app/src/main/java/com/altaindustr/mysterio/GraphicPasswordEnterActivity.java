package com.altaindustr.mysterio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.altaindustr.mysterio.graphicPassDrawer.DrawView;

public class GraphicPasswordEnterActivity extends Activity implements GraphicPasswordHelper
{

    View draw;
    String password;
    public SharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_password_enter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int)(displayMetrics.heightPixels*0.9);
        int width = (int)(displayMetrics.widthPixels*0.9);

        draw = new DrawView(this, this);
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(width, width);
        imageViewLayoutParams.setMargins((int)(width*0.05),0,
                (int)(width*0.05), 0);
        draw.setLayoutParams(imageViewLayoutParams);

        LinearLayout mainlayer = (LinearLayout) findViewById(R.id.lin_lay_enter);
        mainlayer.addView(draw);

        mySharedPreferences = getSharedPreferences(getResources().getString(R.string.APP_PREFERENCES)
                , Context.MODE_PRIVATE);
        password = mySharedPreferences.getString(getResources().getString(R.string.pref_graphic_pass), "");
    }

    @Override
    public void updateGraphicPassword(String passKey)
    {
        if (passKey.equals(password))
        {
            Toast.makeText(this, "Password correct",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void saveGraphicPassword(String passKey)
    {
        //NOTHIG TO DO HERE
    }
}
