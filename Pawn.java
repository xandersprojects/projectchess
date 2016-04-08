
/**
 * Represents a pawn.
 */

public class Pawn extends Piece {

	/** Initializes a COLOR pawn at POSITION. */
	Pawn(int position, int color) {
		super();
		if (color == 1) {
			this.setTextRepr("P");
			int[] possibles = {16, 32, 15, 17};
			this.setPossibles(possibles);
		} else {
			this.setTextRepr("p");
			int[] possibles = {-16, -32, -15, -17};
			this.setPossibles(possibles);
		}
		this.setSliding(true);
		this.setPieceCode(1);
		this.setColor(color);
		_moved = false;
		this.setValue(1.0);
		this.setPosition(position);
	}

	static int[] whitePawnPossible() {
		int[] white = {16, 32, 15, 17};
		return white;
	}

	static int[] blackPawnPossible() {
		int[] black = {-16, -32, -15, -17};
		return black;
	}


	/** True iff this pawn has already moved. */
	private boolean _moved;
}