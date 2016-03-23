package game;

/**
 * Represents a chess board.
 */

public class Board {

	/** Initializes a new chess board of size 128 */
	Board() {
		_board = new Square[128];
	}

	/** Initializes a new chess board with players WHITE and BLACK */
	Board(Player white, Player black) {
		_white = white;
		_black = black;
		_board = new Square[128];
	}

	/** Resets the board to the initial position. */
	void reset() {
		for (int i = 0; i < 128; i++) {
			_board[i].clear();
		}
		/* Place all pieces */
	}

	/** The current board. */
	private Square[] _board;
	/** White player on this board. */
	private Player _white;
	/** Black player on this board. */
	private Player _black;
	
}