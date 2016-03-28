package chess;

/**
 * Represents a human chess player.
 */

public class HumanPlayer extends Player {
	
	HumanPlayer(int color) {
		this.setColor(color);
		this.setType(0);
	}

}