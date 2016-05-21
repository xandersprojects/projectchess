
/**
 * Represents a knight.
 */

public class Knight extends Piece {

	/** Initializes a COLOR knight at POSITION. */
	Knight(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("N");
		} else {
			this.setTextRepr("n");
		}
		this.setSliding(false);
		this.setPieceCode(3);
		this.setColor(color);
		int[] possibles = {33, 18, -14, -31, -33, -18, 14, 31};
		this.setPossibles(possibles);
		this.setValue(3.0);
		this.setPosition(position);
	}

	static int[] bases() {
		int[] base = {33, 18, -14, -31, -33, -18, 14, 31};
		return base;
	}

}