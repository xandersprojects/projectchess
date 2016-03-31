
/**
 * Represents a rook.
 */

public class Rook extends Piece {

	/** Initializes a COLOR rook at POSITION. */
	Rook(int position, int color) {
		super();
		if (color == 1) {
			_textRepr = "R";
		} else {
			_textRepr = "r";
		}
		this.setColor(color);
		this.setSliding(true);
		int[] possibles = {16, 1, -16, -1};
		this.setValue(5.0);
		_position = position;
	}
	
	/** Position of this rook on the board. */
	private int _position;
	/** Text representation of this piece on a text board. */
	private String _textRepr;

}