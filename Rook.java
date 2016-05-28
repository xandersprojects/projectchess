
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
		_moved = false;
	}

	static int[] bases() {
		int[] base = {16, 1, -16, -1};
		return base;
	}

	/** Returns whether or not this rook has moved already. */
	public boolean hasMoved() {
		return _moved;
	}

	/** Sets moved to true after the rook has moved. */
	public void nowMoved() {
		_moved = true;
	}

	/** Indicates whether the king has moved or not. */
	private boolean _moved;

}