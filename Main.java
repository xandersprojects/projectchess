import java.lang.IllegalArgumentException;

/**
  * Starting point for the Chess program.
  */

public class Main {

	public static void main(String[] args) {
		try {
			int p1 = Integer.parseInt(args[0]);
			int p2 = Integer.parseInt(args[1]);
			if (args.length != 2) {
				throw new IllegalArgumentException();
			} else if (!(p1 == 0 || p1 == 1) || !(p2 == 0 || p2 == 1)) {
				throw new IllegalArgumentException();
			}
			Game game = new Game(p1, p2);
			game.play();
		} catch (IllegalArgumentException e) {
			System.out.println();
			System.out.println("Error starting up the chess program.");
			System.out.println("On startup, please ensure that you include two integer arguments.");
			System.out.println("The first integer argument is for the white player.");
			System.out.println("The second integer argument is for the black player.");
			System.out.println("The argument is 0 if you want to set this player to be human.");
			System.out.println("The argument is 1 if you want to set this player to be an AI.");
			System.out.println("An example game of human vs. computer is: 'java Main 0 1'");
			System.out.println("An example game of a human vs. another human is: 'java Main 0 0'");
			System.out.println();
			System.exit(0);
		}
	}

}