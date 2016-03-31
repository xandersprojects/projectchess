
/**
 * Represents a knight.
 */

public class Knight extends Piece {

	/** Initializes a COLOR knight at POSITION. */
	Knight(int position, int color) {
		super();
		if (color == 1) {
			_textRepr = "N";
		} else {
			_textRepr = "n";
		}
		this.setSliding(false);
		this.setColor(color);
		int[] possibles = {33, 18, -14, -31, -33, -18, 14, 31};
		this.setValue(3.0);
		_position = position;
	}

	/** Position of this knight on the board. */
	private int _position;
	/** Text representation of this piece on a text board. */
	private String _textRepr;

}