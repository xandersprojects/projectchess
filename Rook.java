
/**
 * Represents a rook.
 */

public class Rook extends Piece {

	/** Initializes a COLOR rook at POSITION. */
	Rook(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("R");
		} else {
			this.setTextRepr("r");
		}
		this.setColor(color);
		this.setPieceCode(2);
		this.setSliding(true);
		int[] possibles = {16, 1, -16, -1};
		this.setPossibles(possibles);
		this.setValue(5.0);
		this.setPosition(position);
	}

	static int[] bases() {
		int[] base = {16, 1, -16, -1};
		return base;
	}

}