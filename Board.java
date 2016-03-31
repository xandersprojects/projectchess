
/**
 * Represents a chess board.
 */

public class Board {

	/** Initializes a new chess board with players WHITE and BLACK */
	Board(Player white, Player black) {
		_white = white;
		_black = black;
		_board = new Square[128];
	}

	/** Resets the board to the initial position. */
	void reset() {
		for (int i = 0; i < 128; i++) {
			_board[i].clear();
		}
		/* Place all rooks */
		Rook a1 = new Rook(0, 1);
		_board[0].putPiece(a1);
		Rook h1 = new Rook(7, 1);
		_board[7].putPiece(h1);
		Rook a8 = new Rook(112, 0);
		_board[112].putPiece(a8);
		Rook h8 = new Rook(119, 0);
		_board[119].putPiece(h8);
		/* Place all knights */
		Knight b1 = new Knight(1, 1);
		_board[1].putPiece(b1);
		Knight g1 = new Knight(6, 1);
		_board[6].putPiece(g1);
		Knight b8 = new Knight(113, 0);
		_board[113].putPiece(b8);
		Knight g8 = new Knight(118, 0);
		_board[118].putPiece(g8);
		/* Place all bishops */
		Bishop c1 = new Bishop(2, 1);
		_board[2].putPiece(c1);
		Bishop f1 = new Bishop(5, 1);
		_board[5].putPiece(f1);
		Bishop c8 = new Bishop(114, 0);
		_board[114].putPiece(c8);
		Bishop f8 = new Bishop(117, 0);
		_board[117].putPiece(f8);
		/* Place the queens */
		Queen d1 = new Queen(3, 1);
		_board[3].putPiece(d1);
		Queen d8 = new Queen(115, 0);
		_board[115].putPiece(d8);
		/* Place the kings */
		King e1 = new King(4, 1);
		_board[4].putPiece(e1);
		King e8 = new King(116, 0);
		_board[116].putPiece(e8);
		/* Place all pawns */
		Pawn a2 = new Pawn(16, 1);
		_board[16].putPiece(a2);
		Pawn b2 = new Pawn(17, 1);
		_board[17].putPiece(b2);
		Pawn c2 = new Pawn(18, 1);
		_board[18].putPiece(c2);
		Pawn d2 = new Pawn(19, 1);
		_board[19].putPiece(d2);
		Pawn e2 = new Pawn(20, 1);
		_board[20].putPiece(e2);
		Pawn f2 = new Pawn(21, 1);
		_board[21].putPiece(f2);
		Pawn g2 = new Pawn(22, 1);
		_board[22].putPiece(g2);
		Pawn h2 = new Pawn(23, 1);
		_board[23].putPiece(h2);
		Pawn a7 = new Pawn(96, 0);
		_board[96].putPiece(a7);
		Pawn b7 = new Pawn(97, 0);
		_board[97].putPiece(b7);
		Pawn c7 = new Pawn(98, 0);
		_board[98].putPiece(c7);
		Pawn d7 = new Pawn(99, 0);
		_board[99].putPiece(d7);
		Pawn e7 = new Pawn(100, 0);
		_board[100].putPiece(e7);
		Pawn f7 = new Pawn(101, 0);
		_board[101].putPiece(f7);
		Pawn g7 = new Pawn(102, 0);
		_board[102].putPiece(g7);
		Pawn h7 = new Pawn(103, 0);
		_board[103].putPiece(h7);
	}

	Square[] getSquares() {
		return _board;
	}

	/** The current board. */
	private Square[] _board;
	/** White player on this board. */
	private Player _white;
	/** Black player on this board. */
	private Player _black;
	
}