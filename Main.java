import java.lang.IllegalArgumentException;

/**
  * Starting point for the Chess program.
  */

public class Main {

	public static void main(String[] args) {
		try {
			if (args.length != 2) {
				throw new IllegalArgumentException();
			}
			Game game = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			game.printBoard();			
		} catch (IllegalArgumentException e) {
			/* Handle it. */
		}
	}

}