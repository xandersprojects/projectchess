import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

import java.security.*;

/**
 * Represents a chess game.
 */

public class Game {

	Game(int player1, int player2) {
		Player white = null;
		Player black = null;
		if (player1 == 0) {
			white = new HumanPlayer(1);
		} else if (player1 == 1) {
			long[] zobrist = zobristTableGenerator();
			white = new ComputerPlayer(1, zobrist);
		}

		if (player2 == 0) {
			black = new HumanPlayer(0);
		} else if (player2 == 1) {
			long[] zobrist = zobristTableGenerator();
			black = new ComputerPlayer(0, zobrist);
		}
		_player1 = white;
		_player2 = black;
		_movesList = new ArrayList<ArrayList<String>>();
		_board = new Board(_player1, _player2);
		_zobristTable = zobristTableGenerator();
	}

	Game(Player player1, Player player2, Board board, ArrayList<ArrayList<String>> movesList) {
		_player1 = player1;
		_player2 = player2;
		_board = board;
		_movesList = movesList;
		_zobristTable = zobristTableGenerator();
		// _gameHistory = gameHistory;
	}


	Player getPlayer1() {
		return _player1;
	}

	Player getPlayer2() {
		return _player2;
	}

	Board getBoard() {
		return _board;
	}

	ArrayList<ArrayList<String>> getMoveHistory() {
		return _movesList;
	}

	// LinkedList<Board> getGameHistory() {
	// 	return _gameHistory;
	// }

	/* Prints, in text format, the current board. */
	void printBoard() {
		System.out.println("  ===================");
		Square[] current = getBoard().getSquares();
		String rank = "|";
		for (int i = current.length - 1; i >= 0; i--) {

			if (i % 16 == 0) {
				String ranknum = Integer.toString(i / 16 + 1);
				Piece curr = current[i].getPiece();
				if (curr == null) {
					rank = "-" + " " + rank;
				} else {
					rank = curr.getTextRepr() + " " + rank;
				}
				rank = ranknum + " | " + rank;
				System.out.println(rank);
				rank = "|";
				continue;
			}
			if ((i & 8) == 8) {
				continue;
			}
			Piece curr = current[i].getPiece();
			if (curr == null) {
				rank = "-" + " " + rank;
				continue;
			} else {
				rank = curr.getTextRepr() + " " + rank;
			}
		}
		System.out.println("  ===================");
		System.out.println("    a b c d e f g h  ");
	}

	void printMoveHistory() {
		for (int i = 0; i < _movesList.size(); i++) {
			ArrayList<String> completeTurn = _movesList.get(i);
			System.out.print(completeTurn.get(0) + ". ");
			for (int j = 1; j < completeTurn.size(); j++) {
				System.out.print(completeTurn.get(j) + " ");
			}
			System.out.println();
		}
	}

	/* Removes the last entry in the movesList. */
	void removeLast() {
		int cycles = _movesList.size();
		ArrayList<String> lastCycle = _movesList.get(cycles - 1);
		if (lastCycle.size() == 1) { /* Black was the one who made the last move */
			_movesList.remove(cycles - 1);
			ArrayList<String> realLastCycle = _movesList.get(cycles - 2);
			realLastCycle.remove(2);
		} else if (lastCycle.size() == 2) { /* White was the one who made the last move */
			lastCycle.remove(1);
		}
		printMoveHistory();
	}

	/** While loop controlling game flow. */
	void play() {
		while (true /* Game is not yet over*/) {
			printBoard();
			System.out.println();
			System.out.println("This board's evaluation is: " + ComputerPlayer.centipawnValue(_board, _movesList));
			ArrayList<String> turn = new ArrayList<String>();
			turn.add(String.valueOf(_movesList.size() + 1));
			_movesList.add(turn);

			/* White player's move */

			if (_player1.getType() == 0) { /** Player 1 is human */

				HumanPlayer white = (HumanPlayer) _player1;
				Scanner scan = new Scanner(System.in);
				System.out.println("Your move: ");
				String trying = scan.next();
				Move propose = white.proposeMove(_board, trying, _movesList);

				while (propose == null || Utils.sanityCheck(propose, 1, _board, _movesList))  {
					System.out.println("That move is invalid. Try again: ");
					trying = scan.next();
					propose = white.proposeMove(_board, trying, _movesList);
				}

				Utils.makeMove(propose, _board, 0);
				_board.switchTurn();
				turn.add(propose.getStr());
			} else {
				ComputerPlayer white = (ComputerPlayer) _player1;
				Move propose = white.proposeMove(1, _board, _movesList, 5);
				Utils.makeMove(propose, _board, 0);
				_board.switchTurn();
				turn.add(propose.getStr());
			}
			if (Utils.isCheckMate(0, _board, _movesList)) {
				System.out.println("White wins!");
				printBoard();
				break;
			} else if (Utils.isCheck(0, _board, _movesList)) {
				System.out.println("Check!");
			}
			printMoveHistory();
			printBoard();
			System.out.println();
			System.out.println("This board's evaluation is: " + ComputerPlayer.centipawnValue(_board, _movesList));

			/* Black player's move */
			if (_player2.getType() == 0) { /** Player 2 is human */

				HumanPlayer black = (HumanPlayer) _player2;
				Scanner scan = new Scanner(System.in);
				System.out.println("Your move: ");
				String trying = scan.next();
				Move propose = black.proposeMove(_board, trying, _movesList);

				while (propose == null) {
					System.out.println("That move is invalid. Try again: ");
					trying = scan.next();
					propose = black.proposeMove(_board, trying, _movesList);
				}

				Utils.makeMove(propose, _board, 0);
				_board.switchTurn();
				turn.add(propose.getStr());
			} else {
				ComputerPlayer black = (ComputerPlayer) _player2;
				long startTime = System.nanoTime();
				Move propose = black.proposeMove(0, _board, _movesList, 5);
				Utils.makeMove(propose, _board, 0);
				_board.switchTurn();
				turn.add(propose.getStr());
				long endTime = System.nanoTime();
				long length = (endTime - startTime) / 1000000;
				double duration = (double) length / 1000.0;
				System.out.println("This turn took " + Double.toString(duration) + " seconds to calculate.");
			}
			if (Utils.isCheckMate(1, _board, _movesList)) {
				System.out.println("Black wins!");
				printBoard();
				break;
			} else if (Utils.isCheck(1, _board, _movesList)) {
				System.out.println("Check!");
			}
			printMoveHistory();
		}
	}

	/* Generates a random Zobrist table to generate Zobrist keys later.
	 * How the indices work:
	 * Square number * 12 will get you to the "start" index of the square.
	 * 1, 2, 3, 4, 5, 6 are for black pawn, rook, knight, bishop, queen, king respectively.
	 * 7, 8, 9, 10, 11, 12 are for white pawn, rook, knight, bishop, queen, king respectively.
	 * Since there are 128 squares, there will be 128 * 12 = 1536 entries, which
	 * range from indices 0 to 1535. Thus, starting at index 1536, we have:
	 * 1536: Number for white kingside castling
	 * 1537: Number for black kingside castling
	 * 1538: Number for white queenside castling
	 * 1539: Number for black queenside castling
	 * 1540-1547: Numbers for target files where en passant is possible
	 * 1548: Number indicating who's turn it is to move.
	 * This makes 1549 entries total, which is the size of the zobrist table. */
	long[] zobristTableGenerator() {
		long[] zobrist = new long[1549];
		for (int i = 0; i < zobrist.length; i++) {
			zobrist[i] = rng();
		}
		return zobrist;
	}

	public static long rng() {
		SecureRandom random = new SecureRandom();
		return random.nextLong();
	}

	/* Player 1 of this game, handling white. */
	private Player _player1;
	/* Player 2 of this game, handling black. */
	private Player _player2;
	/* The board for this game. */
	private Board _board;
	/* List of moves for this game. */
	private ArrayList<ArrayList<String>> _movesList;
	/* Zobrist table for this game. */
	public long[] _zobristTable;
	
}