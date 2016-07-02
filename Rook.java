
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
		this.setValue(5.0);
		this.setPosition(position);
		this.notMoved();
	}

	/* Creates a new Rook with preset settings. */
	Rook (int color, int position, boolean moved) {
		super();
		if (color == 1) {
			this.setTextRepr("R");
		} else {
			this.setTextRepr("r");
		}
		this.setColor(color);
		this.setPieceCode(2);
		this.setSliding(true);
		this.setValue(5.0);
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
		int[] base = {16, 1, -16, -1};
		return base;
	}

}