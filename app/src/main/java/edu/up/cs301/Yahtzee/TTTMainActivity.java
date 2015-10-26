package edu.up.cs301.Yahtzee;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * this is the primary activity for Counter game
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class TTTMainActivity extends GameMainActivity {
	
	public static final int PORT_NUMBER = 5213;

	/**
	 * a tic-tac-toe game is for two players. The default is human vs. computer
	 */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
		
		/*// yellow-on-blue GUI
		playerTypes.add(new GamePlayerType("Local Human Player (blue-yellow)") {
			public GamePlayer createPlayer(String name) {
				return new TTTHumanPlayer1(name);
			}
		});
		
		// red-on-yellow GUI
		playerTypes.add(new GamePlayerType("Local Human Player (yellow-red)") {
			public GamePlayer createPlayer(String name) {
				return new TTTHumanPlayer2(name);
			}
		});
		*/
		// game of 33
		playerTypes.add(new GamePlayerType("Local Human Player (Yahtzee)") {
			public GamePlayer createPlayer(String name) {
				return new YAHHumanPlayer1(name);
			}
		});
		
		// dumb computer player
		playerTypes.add(new GamePlayerType("YAHTbot - easy") {
			public GamePlayer createPlayer(String name) {
				return new YAHTbotEASY(name);
			}
		});
		
		// smarter computer player
		playerTypes.add(new GamePlayerType("YAHTbot - hard") {
			public GamePlayer createPlayer(String name) {
				return new YAHTbotHARD(name);
			}
		});

		// Create a game configuration class for Tic-tac-toe
		GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Yahtzee", PORT_NUMBER);

		// Add the default players
		defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
		defaultConfig.addPlayer("Computer", 3); // dumb computer player

		// Set the initial information for the remote player
		defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI
		
		//done!
		return defaultConfig;
		
	}//createDefaultConfig


	/**
	 * createLocalGame
	 * 
	 * Creates a new game that runs on the server tablet,
	 * 
	 * @return a new, game-specific instance of a sub-class of the LocalGame
	 *         class.
	 */
	@Override
	public LocalGame createLocalGame() {
		return new YAHLocalGame();
	}


	////////////////////////////////////////////////////////////////////////////////////////////////


	public void pauseGame(View v)
	{
		Intent pause = new Intent(this, Pause.class);
		startActivity(pause);

	}

	public void scoreBoard(View v)
	{
		Intent scores = new Intent(this,ScoreBoard.class);
		startActivity(scores);
	}

}



