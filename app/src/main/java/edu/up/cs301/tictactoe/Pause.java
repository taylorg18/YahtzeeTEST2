package edu.up.cs301.tictactoe;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.ActionBar;

import edu.up.cs301.game.R;


public class Pause extends Activity {
    Button quit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
    }

    public void resume(View v)
    {
        finish();
    }

    public void quitClick(View v)
    {
        finish();
    }




}
