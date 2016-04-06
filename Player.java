
/**
 * Represents a chess player.
 */

public abstract class Player {
	
	/** Returns the color pieces this player controls. */
	int getColor() {
		return _color;
	}

	/** Sets this player to control COLOR pieces. */
	void setColor(int color) {
		_color = color;
	}

	/** Returns the type of this player. */
	int getType() {
		return _player;
	}

	/** Sets this player to be of type TYPE. */
	void setType(int type) {
		_player = type;
	}

	/** Makes a move on BOARD. */
	abstract void makeMove(Board board);

	/* The color pieces this player controls. 1 for white, 0 for black. */
	private int _color;
	/* 1 if player is AI; 0 if player is human. */
	private int _player;

}