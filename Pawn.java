
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

	/* Creates a new Pawn with preset settings. */
	Pawn (int color, int position, boolean moved) {
		super();
		if (color == 1) {
			this.setTextRepr("P");
		} else {
			this.setTextRepr("p");
		}
		this.setColor(color);
		this.setPieceCode(1);
		this.setSliding(true);
		this.setValue(1.0);
		this.setPosition(position);
		if (moved) {
			this.nowMoved();
		} else {
			this.notMoved();
		}
	}

	/* Creates a new Pawn with preset settings. */
	Pawn (int color, int position, boolean moved, double random) {
		super();
		// if (color == 0 && position % 16 == 0) {
		// 	System.out.println("Creating a new black a pawn with ID " + Double.toString(random));
		// }
		if (color == 1) {
			this.setTextRepr("P");
		} else {
			this.setTextRepr("p");
		}
		this.setColor(color);
		this.setPieceCode(1);
		this.setSliding(true);
		this.setValue(1.0);
		this.setPosition(position);
		if (moved) {
			this.nowMoved();
		} else {
			this.notMoved();
		}
		_random = random;
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

	double getRandom() {
		return _random;
	}


	/** True iff this pawn has already moved. */
	private boolean _moved;
	/** True iff this pawn has moved two spaces on its first move. */
	private boolean _two;
	/** Random number ID for testing purposes */
	private double _random;
}