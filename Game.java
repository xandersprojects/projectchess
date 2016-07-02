import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

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
			white = new ComputerPlayer(1);
		}

		if (player2 == 0) {
			black = new HumanPlayer(0);
		} else if (player2 == 1) {
			black = new ComputerPlayer(0);
		}
		_player1 = white;
		_player2 = black;
		_movesList = new ArrayList<ArrayList<String>>();
		_board = new Board(_player1, _player2);
		// _gameHistory = new LinkedList<Board>();
	}

	Game(Player player1, Player player2, Board board, ArrayList<ArrayList<String>> movesList) {
		_player1 = player1;
		_player2 = player2;
		_board = board;
		_movesList = movesList;
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
			ArrayList<String> turn = new ArrayList<String>();
			turn.add(String.valueOf(_movesList.size() + 1));
			_movesList.add(turn);
			/* White player's move */
			if (_player1.getType() == 0) { /** Player 1 is human */
				ArrayList<Move> moves = Utils.moveGenerator(1, _board, _movesList);
				for (int i = 0; i < moves.size(); i++) {
					System.out.println(moves.get(i).getStr());
				}
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
				/* Check that the move is chess-valid i.e. doesn't move a pinned piece; doesn't move out of check; */
				/* Checking valid move goes in Utils. Players actually make the move. */
				/* Actually make the move. */
				Utils.makeMove(propose, _board, 0);
				_board.switchTurn();
				turn.add(propose.getStr());
				if (Utils.isCheck(0, _board, _movesList)) {
					System.out.println("Check!");
				}
			}
			printMoveHistory();
			printBoard();
			System.out.println();
			if (_player2.getType() == 0) { /** Player 2 is human */
				ArrayList<Move> moves = Utils.moveGenerator(0, _board, _movesList);
				for (int i = 0; i < moves.size(); i++) {
					System.out.println(moves.get(i).getStr());
				}
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
				if (Utils.isCheck(1, _board, _movesList)) {
					System.out.println("Check!");
				}
			}
			printMoveHistory();
		}
	}

	/* Player 1 of this game, handling white. */
	private Player _player1;
	/* Player 2 of this game, handling black. */
	private Player _player2;
	/* The board for this game. */
	private Board _board;
	/* List of moves for this game. */
	private ArrayList<ArrayList<String>> _movesList;
	/* LinkedList of Boards, with each link made after a single move. */
	// private LinkedList<Board> _gameHistory;
	
}