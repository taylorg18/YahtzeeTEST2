package edu.up.cs301.Yahtzee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
