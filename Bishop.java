
/**
 * Represents a bishop.
 */

public class Bishop extends Piece {

	/** Initializes a COLOR bishop at POSITION. */
	Bishop(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("B");
		} else {
			this.setTextRepr("b");
		}
		this.setSliding(true);
		this.setPieceCode(4);
		this.setColor(color);
		this.setValue(3.0);
		this.setPosition(position);
		this.notMoved();
	}

	/* Creates a new Bishop with preset settings. */
	Bishop (int color, int position, boolean moved) {
		super();
		if (color == 1) {
			this.setTextRepr("B");
		} else {
			this.setTextRepr("b");
		}
		this.setColor(color);
		this.setPieceCode(4);
		this.setSliding(true);
		this.setValue(3.0);
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
		int[] base = {17, -15, -17, 15};
		return base;
	}

}