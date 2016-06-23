
/**
 * Represents a pawn.
 */

public class Pawn extends Piece {

	/** Initializes a COLOR pawn at POSITION. */
	Pawn(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("P");
		} else {
			this.setTextRepr("p");
		}
		this.setSliding(true);
		this.setPieceCode(1);
		this.setColor(color);
		_moved = false;
		_two = false;
		this.setValue(1.0);
		this.setPosition(position);
		this.notMoved();
	}

	public int[] getBases() {
		if (this.getColor() == 1) {
			return whitePawnPossible();
		}
		return blackPawnPossible();
	}

	static int[] whitePawnPossible() {
		int[] white = {16, 32, 15, 17};
		return white;
	}

	static int[] blackPawnPossible() {
		int[] black = {-16, -32, -15, -17};
		return black;
	}

	void movedTwo() {
		_two = true;
	}

	boolean didTwo() {
		return _two;
	}


	/** True iff this pawn has already moved. */
	private boolean _moved;
	/** True iff this pawn has moved two spaces on its first move. */
	private boolean _two;
}