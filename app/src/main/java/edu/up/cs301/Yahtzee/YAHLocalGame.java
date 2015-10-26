package edu.up.cs301.Yahtzee;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * The YAHLocalGame class for a simple tic-tac-toe game.  Defines and enforces
 * the game rules; handles interactions with players.
 * 
 * @author Steven R. Vegdahl 
 * @version July 2013
 */

public class YAHLocalGame extends LocalGame implements TTTGame {

	// the game's state
	YAHState state;


	private int roundCount;

	/**
	 * Constructor for the YAHLocalGame.
	 */
	public YAHLocalGame() {

		// perform superclass initialization
		super();

		// create a new, unfilled-in YAHState object
		state = new YAHState();
	}

	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	public String checkIfGameOver() {




		if (roundCount < 13) {


		}
		else
		{
			return null;
		}

		// if we get here, then we've found a winner, so return the 0/1
		// value that corresponds to that mark; then return a message
		int gameWinner =0;
		return playerNames[gameWinner]+" is the winner.";
	}

	/**
	 * Notify the given player that its state has changed. This should involve sending
	 * a GameInfo object to the player. If the game is not a perfect-information game
	 * this method should remove any information from the game that the player is not
	 * allowed to know.
	 * 
	 * @param p
	 * 			the player to notify
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// make a copy of the state, and send it to the player
		p.sendInfo(new YAHState(state));

	}

	/**
	 * Tell whether the given player is allowed to make a move at the
	 * present point in the game. 
	 * 
	 * @param playerIdx
	 * 		the player's player-number (ID)
	 * @return
	 * 		true iff the player is allowed to move
	 */
	public boolean canMove(int playerIdx) {
		return playerIdx == state.getWhoseMove();
	}

	/**
	 * Makes a move on behalf of a player.
	 * 
	 * @param action
	 * 			The move that the player has sent to the game
	 * @return
	 * 			Tells whether the move was a legal one.
	 */
	@Override
	public boolean makeMove(GameAction action) {

		// get the row and column position of the player's move
		TTTMoveAction tm = (TTTMoveAction) action;

		// get the 0/1 id of our player
		int playerId = getPlayerIdx(tm.getPlayer());


		// get the 0/1 id of the player whose move it is
		int whoseMove = state.getWhoseMove();


		// make it the other player's turn
		state.setWhoseMove(1 - whoseMove);

		// bump the round count
		roundCount++;
		
		// return true, indicating the it was a legal move
		return true;
	}

}
