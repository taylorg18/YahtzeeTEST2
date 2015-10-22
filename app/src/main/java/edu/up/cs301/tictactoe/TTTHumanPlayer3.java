package edu.up.cs301.tictactoe;

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



	
	/**
	 * constuctor
	 * 
	 * @param name
	 * 		the player's name
	 */
	public TTTHumanPlayer3(String name) {
		super(name);
	}

	// android ID values for the padding objects
	/*private static final int[][] paddingIndices = {
			{R.id.padding7Top, R.id.padding7Bot},
			{R.id.padding8Top, R.id.padding8Bot},
			{R.id.padding9Top, R.id.padding9Bot},
			{R.id.padding10Top, R.id.padding10Bot},
			{R.id.padding11Top, R.id.padding11Bot},
			{R.id.padding12Top, R.id.padding12Bot},
			{R.id.padding13Top, R.id.padding13Bot},
			{R.id.padding14Top, R.id.padding14Bot},
			{R.id.padding15Top, R.id.padding15Bot},
	};*/
	
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
	// an array to hold our padding objects. It consists of 9 two-element arrays,
	// each two-element array contains the upper and lower padding objects for
	// a particular button, which are stored in order from 7 to 15. Element 0, for
	// example, contains the two padding objects for the '7' button.
	private LinearLayout[][] buttonPaddings;
	
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
					(Button)myActivity.findViewById(buttonIndices[i]);
		}



		numberedButtons2 = new Button[buttonIndices2.length];

		// fill the array using the indices in the buttonIndices array
		for (int i = 0; i < numberedButtons2.length; i++) {
			numberedButtons2[i] =
					(Button)myActivity.findViewById(buttonIndices2[i]);


		}


		thedice = new dice[dieID.length];

		// fill the array using the indices in the buttonIndices array
		for (int i = 0; i < thedice.length; i++) {
			thedice[i] =
					(dice) myActivity.findViewById(dieID[i]);
			thedice[i].setBackgroundColor(Color.WHITE);
		}

		roll = (Button)myActivity.findViewById(R.id.rollButton);
		roll.setOnClickListener(this);
	}

	/**
	 * initializes the GUI's array of button-padding objects so that we
	 * modify them in order to change the positions of buttons.
	 */
	private void initializePaddingObjects() {
	/*
		// create the array
		buttonPaddings = new LinearLayout[paddingIndices.length][];
		
		// create a 2D array of button-padding object that parallels the
		// corresponding index-array
		for (int i = 0; i < buttonPaddings.length; i++) {
			// create the ith subarray
			buttonPaddings[i] = new LinearLayout[paddingIndices[i].length];
			for (int j = 0; j < buttonPaddings[i].length; j++) {
				// look up the object and initialize the array element
				buttonPaddings[i][j] =
						(LinearLayout)myActivity.findViewById(paddingIndices[i][j]);
			}
		}
		*/
	}

	/**
	 * perform any initialization that needs to be done after the player
	 * knows what their game-position and opponents' names are.
	 */
	protected void initAfterReady() {
		
		// update the title, including the player names
		myActivity.setTitle("Yahtzee: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
		
		// update the TextFields that contain the players' names
		updatePlayerNames();
	}
	
	/**
	 * Updates the player-name TextFields
	 */
	private void updatePlayerNames() {
		// if we haven't yet gotten the player names, ignore
		if (allPlayerNames == null) return;
		
		// get the two text fields
		TextView oppName = (TextView)myActivity.findViewById(R.id.player2_title);
		TextView myName = (TextView)myActivity.findViewById(R.id.player1_title);
		
		// update them each with the appropriate name, so that the current player
		// is listed on the bottom.
		oppName.setText(allPlayerNames[1-playerNum]);
		myName.setText(allPlayerNames[playerNum]);
	}

	/**
	 * callback-method that responds to a button click
	 */
	public void onClick(View v) {
		// get the text from the button, which will denote an
		// integer in the range 7..15
		if(v == roll)
		{
			ROLL(v);
			return;
		}
		String s = ((Button)v).getText().toString();
		
		// convert to an integer
		int val = Integer.parseInt(s);
		v.setBackgroundColor(Color.MAGENTA);
		v.setEnabled(false);
		
		// map the integer to a (2D) tic-tac-toe coordinate
		//int[] coord = mapNumberToCoord(val);
		
		// send a move action to the game
		//game.sendAction(new TTTMoveAction(this, coord[0], coord[1]));
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
	 * @return
	 * 		the GUI's top view
	 */
	@Override
	public View getTopView() {
		return (RelativeLayout)myActivity.findViewById(R.id.top_gui_layout);
	}

	/**
	 * Callback method, called when player gets a message
	 * 
	 * @param info
	 * 		the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
			// if the move is out of turn or otherwise illegal, flash the screen
			flash(Color.RED, 50);
		}
		else if (!(info instanceof TTTState)) {
			// if it's not a TTTState object, ignore
			return;
		}
		else {
			// update the state variable, then update the GUI to reflect the updated
			// state
			state = (TTTState)info;
			setButtonLocationsAndColors();
		}
	}
   
    // tells the mapping between numbers in the game of 33 and the
    // corresponding square in the tic-tac-toe game
   /* private static int[][] mapping = {
        {1,0}, // value = 7
        {2,2}, // value = 8
        {0,1}, // value = 9
        {0,2}, // value = 10
        {1,1}, // value = 11
        {2,0}, // value = 12
        {2,1}, // value = 13
        {0,0}, // value = 14
        {1,2}, // value = 15
    };
    */
    /**
     * maps a game-of-33 number to the corresponding tic-tac-toe coordinate
     * 
     * @param x the number to map
     * @return  a two-element array of int containing the tic-tac-toe
     *   coordinates if the number is in the right range; otherwise, null
     */
   /* private static int[] mapNumberToCoord(int x) {
    
        // subtract 7 to reflect that the 'mapping' array begins with the
        // value for 7
        int idx = x-7;
        
        // if we're out of range return null
        if (idx < 0 || idx >= mapping.length) return null;
        
        // number is in range: return corresponding tic-tac-toe coordinate
        return mapping[idx];
    }*/
    
    // objects for setting the weight of a LinearLayout to either 0 or 1
   // private static LinearLayout.LayoutParams pad0 = new LinearLayout.LayoutParams(0, 0, 0);
   // private static LinearLayout.LayoutParams pad1 = new LinearLayout.LayoutParams(0, 0, 1);
    
    /**
     * sets the location and the color for each of the buttons
     */
	private void setButtonLocationsAndColors() {
		for (int i = 7; i <= 15; i++) {
			setButtonPositionAndColor(i);
		}
	}
    
	/**
	 * sets the location and color for the given button
	 * 
	 * @param buttonNumber
	 * 		the number on the button (7-15) that we want to handle
	 */
    private void setButtonPositionAndColor(int buttonNumber) {
    	// if there is not yet state, or if the number is out of range,
    	// ignore
    	if (state == null) return;
    	if (buttonNumber < 7 || buttonNumber > 15) return;
    	
    	// find the value of the piece (X, O or blank) by mapping
    	// the button number to the tic-tac-toe coordinate
    	//int[] coord = mapNumberToCoord(buttonNumber);
    	//char piece = state.getPiece(coord[0], coord[1]);
    	
    	// get the value of my piece and of my opponent's piece,
    	// based on whether I am player 0 or player 1
    	//char myPiece = "XO".charAt(playerNum);
    	//char oppPiece = "XO".charAt(1-playerNum);
    	
    	// fetch the array containing the pair of padding widgets
    	// for the current number; also get the Button widget
    	//LinearLayout[] paddings = buttonPaddings[buttonNumber-7];
    	//Button button = numberedButtons[buttonNumber-7];
    	
    	// if we're looking at my piece:
    	// - move the button down on the screen
    	// - set button to white-on-blue
    	// if we're looking at my opponent's piece
    	// - move the button up on the screen
    	// - set button to white-on-red
    	// otherwise
    	// - center the button vertically on the screen
    	// - set the button to black-on-gray
    	/*if (piece == myPiece) {
    		// give the padding above the weight 1, and the
    		// padding above the weight 0, which pushes the
    		// button down on the screen
    	//	paddings[0].setLayoutParams(pad1);
    	//	paddings[1].setLayoutParams(pad0);
    		// set the button's text and background colors
    		button.setBackgroundColor(Color.BLUE);
    		button.setTextColor(Color.WHITE);
    	}
    	else if (piece == oppPiece) {
    		// give the padding above the weight 1, and the
    		// padding above the weight 0, which pushes the
    		// button down on the screen
    	//	paddings[0].setLayoutParams(pad0);
    	//	paddings[1].setLayoutParams(pad1);
    		// set the button's text and background colors
    		button.setBackgroundColor(Color.RED);
    		button.setTextColor(Color.WHITE);
    	}
    	else {
    		// set both the above and below padding-weights
    		// to 1, which will center the button vertically
    	//	paddings[0].setLayoutParams(pad1);
    	//	paddings[1].setLayoutParams(pad1);
    		// set the button's text and background colors
    		button.setBackgroundColor(Color.GRAY);
    		button.setTextColor(Color.BLACK);
    	}*/

    	//button.setEnabled(state.getWhoseMove() == playerNum);
    }
    
    /**
     * gets player-number that has selected the corresponding number
     * 
     * @param number
     * 		the piece number
     * @return
     * 		the player number (0 or 1) that selected that piece, or -1
     * 		if the piece has not been selected
     */
   /* private int playerWhoOwnsPiece(int number) {
    	if (state == null) {
    		// if we don't yet have state, no one owns anything
    		return -1;
    	}
    	
    	// map the player number to the corresponding tic-tac-toe coordinate
    	int[] coord = mapNumberToCoord(number);
    	
    	// return -1 (no one owns piece) if the number was not in range
    	if (coord == null) {
    		return -1;
    	}
    	
    	// return 0 if the piece is an 'X', 1 if the piece is an 'O'; otherwise -1
    	return "XO".indexOf(state.getPiece(coord[0], coord[1]));

    }*/
    
    /**
     * callback-method, called when the game is over. The effect
     * here is that if there is a winning combination, that those
     * buttons would be highlighted black-on-green, so that the
     * user sees the "win".
     * 
     * @param msg
     * 		the message that tells the result of the game
     */
    @Override
    protected void gameIsOver(String msg) {
    	// go through all combinations of three buttons,
    	// highlighting the given triple if:
    	// - the sum of the numbers is 33, and
    	// - all three buttons are "owned" by the same player
    	/*outer:
    	for (int i = 7; i <= 15; i++) {
    		for (int j = i+1; j <= 15; j++) {
    			for (int k = j+1; k <= 15; k++) {
    				if (i+j+k == 33) { // sum is 33
    					// if the same player owns all three pieces, mark the buttons
    					// and break out of the loop
    					int owner1 = playerWhoOwnsPiece(i);
    					int owner2 = playerWhoOwnsPiece(j);
    					int owner3 = playerWhoOwnsPiece(k);
    					if (owner1 >= 0 && owner1 == owner2 && owner1 == owner3) {
    						// all three owners are the same, and are not negative (i.e., blank)
    						numberedButtons[i-7].setBackgroundColor(Color.GREEN);
    						numberedButtons[i-7].setTextColor(Color.BLACK);
    						numberedButtons[j-7].setBackgroundColor(Color.GREEN);
    						numberedButtons[j-7].setTextColor(Color.BLACK);
    						numberedButtons[k-7].setBackgroundColor(Color.GREEN);
    						numberedButtons[k-7].setTextColor(Color.BLACK);
    						// exit all three loops, as we only want to highlight one winner
    						break outer;
    					}
    				}
    			}
    		}
    	}
    	
    	// do the "game is over" behavior that my superclass would have done (which should include
    	// showing the user a pop-up message with the game's result
    	super.gameIsOver(msg);
    	*/
    }

	public void ROLL(View view)
	{
		for (int i = 0; i < thedice.length; i++) {
			if(thedice[i].keep == false) {
				thedice[i].invalidate();
			}
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
			v.setBackgroundColor(Color.BLUE);
			((dice)v).keep = true;
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

