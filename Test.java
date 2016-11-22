import java.util.ArrayList;

/**
  * Tests for the chess engine.
  */

public class Test {

	public static void runTests(ComputerPlayer white, ComputerPlayer black) {
		System.out.println();
		System.out.println("Begin testing.");
		System.out.println();

		System.out.println("LEVEL 1 TESTS");
		System.out.println("All tests in this section are mate in 1 problems.");
		int numLevelOneTests = 1;
		int correctLevelOneTests = levelOne(white, black);
		System.out.println("Level 1 Tests: " + Integer.toString(correctLevelOneTests) + " out of " + Integer.toString(numLevelOneTests) + " passed.");

	}

	/** Runs all the level one tests.
	 * LEVEL ONE:
	 * - Depth 1 search for Mate in 1 */
	public static int levelOne(ComputerPlayer white, ComputerPlayer black) {
		int passed = 0;
		passed += test1_1(white, black);
		return passed;
	}

	/** Bill Gates vs. Magnus Carlsen (2014)
	 * Board position:
	 */
	public static int test1_1(ComputerPlayer white, ComputerPlayer black) {
		System.out.println();
		System.out.println("Test 1.1");
		System.out.println("Bill Gates vs. Magnus Carlsen (2014)");
		System.out.println();
		Square[] squares = getEmptyBoard();
		Board board = new Board(squares, white, black, 0);
		Square[] board1_1 = board.getSquares();
		int passed1_1 = 0;

		// *** SETUP *** //
		Rook a1 = new Rook(0, 1);
		board1_1[0].putPiece(a1);
		Bishop c1 = new Bishop(2, 1);
		board1_1[2].putPiece(c1);
		Queen d1 = new Queen(3, 1);
		board1_1[3].putPiece(d1);
		Rook f1 = new Rook(5, 1);
		board1_1[5].putPiece(f1);
		King g1 = new King(6, 1);
		board1_1[6].putPiece(g1);
		Pawn a2 = new Pawn(16, 1);
		board1_1[16].putPiece(a2);
		Pawn b2 = new Pawn(17, 1);
		board1_1[17].putPiece(b2);
		Pawn c2 = new Pawn(18, 1);
		board1_1[18].putPiece(c2);
		Pawn d2 = new Pawn(19, 1);
		board1_1[19].putPiece(d2);
		Pawn f2 = new Pawn(21, 1);
		board1_1[21].putPiece(f2);
		Pawn g2 = new Pawn(22, 1);
		board1_1[22].putPiece(g2);
		Knight c3 = new Knight(34, 1);
		board1_1[34].putPiece(c3);
		Bishop d3 = new Bishop(35, 1);
		board1_1[35].putPiece(d3);
		Knight e5 = new Knight(68, 1);
		board1_1[68].putPiece(e5);

		Knight g4 = new Knight(54, 0);
		board1_1[54].putPiece(g4);
		Queen h5 = new Queen(71, 0);
		board1_1[71].putPiece(h5);
		Pawn a7 = new Pawn(96, 0);
		board1_1[96].putPiece(a7);
		Pawn b7 = new Pawn(97, 0);
		board1_1[97].putPiece(b7);
		Pawn c7 = new Pawn(98, 0);
		board1_1[98].putPiece(b7);
		Pawn e7 = new Pawn(100, 0);
		board1_1[100].putPiece(e7);
		Pawn f7 = new Pawn(101, 0);
		board1_1[101].putPiece(f7);
		Pawn g7 = new Pawn(102, 0);
		board1_1[102].putPiece(g7);
		Pawn h7 = new Pawn(103, 0);
		board1_1[103].putPiece(h7);
		Rook a8 = new Rook(112, 0);
		board1_1[112].putPiece(a8);
		King e8 = new King(116, 0);
		board1_1[116].putPiece(e8);
		Bishop f8 = new Bishop(117, 0);
		board1_1[117].putPiece(f8);
		Rook h8 = new Rook(119, 0);
		board1_1[119].putPiece(h8);

		ArrayList<ArrayList<String>> moveList = new ArrayList<ArrayList<String>>();

		ArrayList<String> move1 = new ArrayList<String>();
		move1.add("1");
		move1.add("e4");
		move1.add("Nc6");
		ArrayList<String> move2 = new ArrayList<String>();
		move2.add("2");
		move2.add("Nf3");
		move2.add("d5");
		ArrayList<String> move3 = new ArrayList<String>();
		move3.add("3");
		move3.add("Bd3");
		move3.add("Nf6");
		ArrayList<String> move4 = new ArrayList<String>();
		move4.add("4");
		move4.add("exd5");
		move4.add("Qxd5");
		ArrayList<String> move5 = new ArrayList<String>();
		move5.add("5");
		move5.add("Nc3");
		move5.add("Qh5");
		ArrayList<String> move6 = new ArrayList<String>();
		move6.add("6");
		move6.add("0-0");
		move6.add("Bg4");
		ArrayList<String> move7 = new ArrayList<String>();
		move7.add("7");
		move7.add("h3");
		move7.add("Ne5");
		ArrayList<String> move8 = new ArrayList<String>();
		move8.add("8");
		move8.add("hxg4");
		move8.add("Nfxg4");
		ArrayList<String> move9 = new ArrayList<String>();
		move9.add("9");
		move9.add("Nxe5");
		moveList.add(move1);
		moveList.add(move2);
		moveList.add(move3);
		moveList.add(move4);
		moveList.add(move5);
		moveList.add(move6);
		moveList.add(move7);
		moveList.add(move8);
		moveList.add(move9);
		// *** END SETUP *** //
		board.printBoard();
		System.out.println();

		Move proposed = black.proposeMove(0, board, moveList, 1);
		String expected = "Qh2";
		System.out.println("Expected move: " + expected);
		String got = proposed.getStr();
		System.out.println("Got: " + got);
		if (expected.compareTo(got) == 0) {
			System.out.println("Test passed!");
			passed1_1 = 1;
		} else {
			System.out.println("Test not passed.");
		}
		return passed1_1;

	}

	/* Returns an empty board. */
	public static Square[] getEmptyBoard() {
		Square[] newBoard = new Square[128];
		for (int i = 0; i < 128; i++) {
			newBoard[i] = new Square(null);
		}
		return newBoard;
	}


}



