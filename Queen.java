
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
		this.setValue(9.0);
		this.setPosition(position);
		this.notMoved();
	}

	/* Creates a new Queen with preset settings. */
	Queen (int color, int position, boolean moved) {
		super();
		if (color == 1) {
			this.setTextRepr("Q");
		} else {
			this.setTextRepr("q");
		}
		this.setColor(color);
		this.setPieceCode(5);
		this.setSliding(true);
		this.setValue(9.0);
		this.setPosition(position);
		if (moved) {
			this.nowMoved();
		} else {
			this.notMoved();
		}
	}

	public int[] getBases() {
		return bases();
	}

	static int[] bases() {
		int[] base = {16, 17, 1, -15, -16, -17, -1, 15};
		return base;
	}
	
}