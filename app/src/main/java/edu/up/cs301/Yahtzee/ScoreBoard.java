package edu.up.cs301.Yahtzee;

import android.app.Activity;
import android.os.Bundle;

import edu.up.cs301.game.R;

/**
 * Created by Grayson on 10/25/2015.
 */
public class ScoreBoard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
    }
}
