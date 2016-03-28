package chess;

/**
 * Represents a chess game.
 */

public class Game {

	Game(int player1, int player2) {
		Player white = null;
		Player black = null;
		if (player1 == 0) {
			white = new HumanPlayer(1);
		} else if (player1 == 1) {
			white = new ComputerPlayer(1);
		}

		if (player2 == 0) {
			black = new HumanPlayer(0);
		} else if (player2 == 1) {
			black = new ComputerPlayer(1);
		}

		if (white == null || black == null) {
			System.out.println("Error reached, terminate program");
		}
		_player1 = white;
		_player2 = black;
		_board = new Board(_player1, _player2);
	}
	/* Ask for player settings */
	/* Initializes a board */
	/* Contains a play() function in while loop to handle the game */

	/* Player 1 of this game, handling white. */
	private Player _player1;
	/* Player 2 of this game, handling black. */
	private Player _player2;
	/* The board for this game. */
	private Board _board;
	
}