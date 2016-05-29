
/**
 * Represents a queen.
 */

public class Queen extends Piece {

	/** Initializes a COLOR queen at POSITION. */
	Queen(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("Q");
		} else {
			this.setTextRepr("q");
		}
		this.setSliding(true);
		this.setPieceCode(5);
		this.setColor(color);
		int[] possibles = {16, 17, 1, -15, -16, -17, -1, 15};
		this.setPossibles(possibles);
		this.setValue(9.0);
		this.setPosition(position);
		this.notMoved();
	}

	static int[] bases() {
		int[] base = {16, 17, 1, -15, -16, -17, -1, 15};
		return base;
	}
	
}