
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
		this.setValue(1000.0);
		this.setPosition(position);
		this.notMoved();
	}

	/* Creates a new King with preset settings. */
	King (int color, int position, boolean moved) {
		super();
		if (color == 1) {
			this.setTextRepr("K");
		} else {
			this.setTextRepr("k");
		}
		this.setColor(color);
		this.setPieceCode(6);
		this.setSliding(true);
		this.setValue(1000.0);
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