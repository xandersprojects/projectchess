
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
		int[] possibles = {16, 32};
		this.setPossibles(possibles);
		this.setValue(1.0);
		this.setPosition(position);
	}


	/** True iff this pawn has already moved. */
	private boolean _moved;
}