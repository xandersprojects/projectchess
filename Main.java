package chess;

/**
  * Starting point for the Chess program.
  */

public class Main {

	public static void main(String[] args) {
		Game game = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}

}