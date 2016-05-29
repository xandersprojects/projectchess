
/**
 * Represents a king.
 */

public class King extends Piece {

	/** Initializes a COLOR king at POSITION. */
	King(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("K");
		} else {
			this.setTextRepr("k");
		}
		this.setSliding(true);
		this.setPieceCode(6);
		this.setColor(color);
		int[] possibles = {16, 17, 1, -15, -16, -17, -1, 15};
		this.setPossibles(possibles);
		this.setValue(1000.0);
		this.setPosition(position);
		this.notMoved();
	}

	static int[] bases() {
		int[] base = {16, 17, 1, -15, -16, -17, -1, 15};
		return base;
	}

}