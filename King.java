
/**
 * Represents a king.
 */

public class King extends Piece {

	/** Initializes a COLOR king at POSITION. */
	King(int position, int color) {
		super();
		if (color == 1) {
			_textRepr = "K";
		} else {
			_textRepr = "k";
		}
		this.setSliding(true);
		this.setColor(color);
		int[] possibles = {16, 17, 1, -15, -16, -17, -1, 15};
		this.setValue(1000.0);
		_position = position;
	}

	/** Position of this king on the board. */
	private int _position;
	/** Text representation of this piece on a text board. */
	private String _textRepr;
}