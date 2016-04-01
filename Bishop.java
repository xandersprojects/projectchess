
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
		this.setColor(color);
		int[] possibles = {17, -15, -17, 15};
		this.setPossibles(possibles);
		this.setValue(3.0);
		this.setPosition(position);
	}

}