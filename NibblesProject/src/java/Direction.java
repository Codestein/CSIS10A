/* Jon Crenshaw
   CSIS 10a: 10 Dec 2010
   Nibbler: Direction.java
*/

public enum Direction {
	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

	// The individual parts of the direction vector
	private int xchange;
	private int ychange;

	// Create a new direction.
	// Parameters:
	//    xchange - horizontal movement per cycle
	//    ychange - vertical movement per cycle
	private Direction(int xchange, int ychange) {
		this.xchange = xchange;
		this.ychange = ychange;
	}

	// Return the horizontal component of velocity
	public int getXchange() {
		return xchange;
	}

	// Return the vertical component of velocity
	public int getYchange() {
		return ychange;
	}

	// Check if one direction is the opposite of another
	public boolean isOpposite(Direction newDirection) {
		return xchange == -newDirection.getXchange() &&
		       ychange == -newDirection.getYchange();
	}

	// Return a string representation of a velocity so that it can be output
	public String toString() {
		return "(" + xchange + ", " + ychange + ")";
	}
}
