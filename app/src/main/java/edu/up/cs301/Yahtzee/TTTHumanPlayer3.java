package edu.up.cs301.Yahtzee;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.up.cs301.animation.Animator;
import edu.up.cs301.animation.dice;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * A human (i.e., GUI) version of a tic-tac-toe player that gives the user
 * the "game of 33" view.
 * 
 * @author Steven R. Vegdahl 
 * @version July 2013
 */
public class TTTHumanPlayer3 extends TTTHumanPlayer implements TTTPlayer, OnClickListener, Animator, View.OnLongClickListener {

	// the activity under which we're running
	GameMainActivity myActivity = null;

	// the game's state
	TTTState state = null;

	int round;
	int rollNum;


	/**
	 * constuctor
	 *
	 * @param name the player's name
	 */
	public TTTHumanPlayer3(String name) {
		super(name);
	}


	// android ID values for the button objects
	private static final int[] buttonIndices = {
			R.id.p1_ace,
			R.id.p1_two,
			R.id.p1_three,
			R.id.p1_four,
			R.id.p1_five,
			R.id.p1_six,
			R.id.p1_3kind,
			R.id.p1_4kind,
			R.id.p1_house,
			R.id.p1_smstraight,
			R.id.p1_lgstraight,
			R.id.p1_yahtzee,
			R.id.p1_chance,
	};


	private static final int[] buttonIndices2 = {
			R.id.p2_ace,
			R.id.p2_two,
			R.id.p2_three,
			R.id.p2_four,
			R.id.p2_five,
			R.id.p2_six,
			R.id.p2_3kind,
			R.id.p2_4kind,
			R.id.p2_house,
			R.id.p2_smstraight,
			R.id.p2_lgstraight,
			R.id.p2_yahtzee,
			R.id.p2_chance,
	};


	// our 9 button objects, in order from 7 to 15
	private Button[] numberedButtons1;

	private Button[] numberedButtons2;


	private Button roll;
	private dice[] thedice;

	private static final int[] dieID =
			{
					R.id.imageButton,
					R.id.imageButton2,
					R.id.imageButton3,
					R.id.imageButton4,
					R.id.imageButton5,
			};


	/**
	 * initializes the GUI's button array so that we can access them
	 * by position
	 */
	private void initializeButtons() {
		// create the button array
		numberedButtons1 = new Button[buttonIndices.length];

		// fill the array using the indices in the buttonIndices array
		for (int i = 0; i < numberedButtons1.length; i++) {
			numberedButtons1[i] =
					(Button) myActivity.findViewById(buttonIndices[i]);
		}


		numberedButtons2 = new Button[buttonIndices2.length];

		// fill the array using the indices in the buttonIndices array
		for (int i = 0; i < numberedButtons2.length; i++) {
			numberedButtons2[i] =
					(Button) myActivity.findViewById(buttonIndices2[i]);


		}


		thedice = new dice[dieID.length];

		// fill the array using the indices in the buttonIndices array
		for (int i = 0; i < thedice.length; i++) {
			thedice[i] =
					(dice) myActivity.findViewById(dieID[i]);
			thedice[i].setBackgroundColor(Color.WHITE);
		}

		roll = (Button) myActivity.findViewById(R.id.rollButton);
		roll.setOnClickListener(this);
	}


	/**
	 * perform any initialization that needs to be done after the player
	 * knows what their game-position and opponents' names are.
	 */
	protected void initAfterReady() {

		// update the title, including the player names
		myActivity.setTitle("Yahtzee: " + allPlayerNames[0] + " vs. " + allPlayerNames[1]);

		// update the TextFields that contain the players' names
		updatePlayerNames();
		round = 1;
		rollNum = 1;
	}

	/**
	 * Updates the player-name TextFields
	 */
	private void updatePlayerNames() {
		// if we haven't yet gotten the player names, ignore
		if (allPlayerNames == null) return;

		// get the two text fields
		TextView oppName = (TextView) myActivity.findViewById(R.id.player2_title);
		TextView myName = (TextView) myActivity.findViewById(R.id.player1_title);

		// update them each with the appropriate name, so that the current player
		// is listed on the bottom.
		oppName.setText(allPlayerNames[1 - playerNum]);
		myName.setText(allPlayerNames[playerNum]);
	}

	/**
	 * callback-method that responds to a button click
	 */
	public void onClick(View v) {
		// get the text from the button, which will denote an
		// integer in the range 7..15
		if (v == roll) {
			ROLL(v);
			return;
		}
		String s = ((Button) v).getText().toString();

		// convert to an integer
		int val = Integer.parseInt(s);
		v.setBackgroundColor(Color.MAGENTA);
		v.setEnabled(false);

	}

	/**
	 * sets the current player as the activity's GUI
	 */
	public void setAsGui(GameMainActivity activity) {

		// remember the activity
		myActivity = activity;

		// Load the layout resource for the new configuration
		activity.setContentView(R.layout.ttt_human_player3);

		// initialize the button-array
		initializeButtons();

		// intialize the array of padding objects
		//initializePaddingObjects();

		// put the player names into the GUI
		updatePlayerNames();

		// listen to each of the buttons
		for (int i = 0; i < numberedButtons1.length; i++) {
			numberedButtons1[i].setOnClickListener(this);
		}

		for (int i = 0; i < numberedButtons2.length; i++) {
			numberedButtons2[i].setOnClickListener(this);
		}

		for (int i = 0; i < thedice.length; i++) {
			thedice[i].setOnLongClickListener(this);
		}


		// if we have state, update the GUI based on the state
		if (state != null) {
			receiveInfo(state);
		}
	}

	/**
	 * returns the GUI's top view
	 *
	 * @return the GUI's top view
	 */
	@Override
	public View getTopView() {
		return (RelativeLayout) myActivity.findViewById(R.id.top_gui_layout);
	}

	/**
	 * Callback method, called when player gets a message
	 *
	 * @param info the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
			// if the move is out of turn or otherwise illegal, flash the screen
			flash(Color.RED, 50);
		} else if (!(info instanceof TTTState)) {
			// if it's not a TTTState object, ignore
			return;
		} else {
			// update the state variable, then update the GUI to reflect the updated
			// state
			state = (TTTState) info;

		}
	}


	/**
	 * callback-method, called when the game is over. The effect
	 * here is that if there is a winning combination, that those
	 * buttons would be highlighted black-on-green, so that the
	 * user sees the "win".
	 *
	 * @param msg the message that tells the result of the game
	 */
	@Override
	protected void gameIsOver(String msg) {

		if (round == 13) {

		} else {
			return;
		}

	}

	public void ROLL(View view) {
		if (rollNum < 4)
		{
			for (int i = 0; i < thedice.length; i++) {
				if (thedice[i].keep == false) {
					thedice[i].roll();

					thedice[i].invalidate();
				}
			}
			rollNum++;
		}

		else

		{
			return;
		}

	}

	public boolean onLongClick(View v) {


		if(((dice)v).keep) {
			v.setBackgroundColor(Color.WHITE);
			((dice)v).keep = false;
			return false;
		}
		else
		{
			((dice)v).keep = true;
			v.setBackgroundColor(Color.BLUE);

			return true;
		}



	}

	public int interval() {
		return 0;
	}

	public int backgroundColor() {
		return 0;
	}

	public boolean doPause() {
		return false;
	}

	public boolean doQuit() {
		return false;
	}

	public void tick(Canvas canvas) {

	}

	public void onTouch(MotionEvent event) {

	}


//////////////////////////////////////////////////////////////////////////////////////////////////////


}

