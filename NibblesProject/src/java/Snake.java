/* Jon Crenshaw
   CSIS 10a: 10 Dec 2010
   Nibbler: Snake.java
*/

import java.awt.*;

import objectdraw.*;

public class Snake extends ActiveObject {
	// constants to control keyboard movement
	// the snake's growth rate (added links per apple)
	private static final int GROWTHRATE = 3;

	// maximum length of snake
	private static final int MAXLENGTH = 1000;

	// pause time between movements
	private static final int PAUSE_TIME = 100;

	// list of Locations occupied by snake's body
	private FilledRect[] body;
	private FilledRect playfield, background, apple, stem; // the food
	private FramedRect border;  //  the boundary of the playing area
	private Location nextCell;   // next cell to be occupied by snake head
	private RandomIntGenerator randomCell;  // to generate new cell positions for moving apple
	private RandomIntGenerator randomColor = new RandomIntGenerator(0, 255);
	private RandomIntGenerator randomShade = new RandomIntGenerator(125, 255);
	private RandomIntGenerator randomDimShade = new RandomIntGenerator(60, 80);
	private RandomIntGenerator randomDIMShade = new RandomIntGenerator(25, 50);
	private RandomIntGenerator randomDirection = new RandomIntGenerator(0, 3);
	private Line[] lines = new Line[1000];
	private int lineN = 0;

	// Current length of the snake
	private int currentLength = 0;

	// The dimensions of the board and cell size
	private int ncells, cellsize;

	// The current direction in which the snake is moving
	private Direction curDirection = Direction.UP;

	private DrawingCanvas canvas;

	// How many more cells the snake should be allowed to grow for
	// eating some food (a countdown timer for the run method. if toGrow>0 shrink() is avoided
	private int toGrow;

	// Construct a snake
	// Parameters:
	public Snake(int ncells, int cellsize, FilledRect apple, FramedRect border, DrawingCanvas canvas) {
		this.ncells = ncells;
		this.cellsize = cellsize;
		this.apple = apple;
		this.border = border;
		this.canvas = canvas;

		randomCell = new RandomIntGenerator(1, ncells - 1);
		body = new FilledRect[MAXLENGTH];

		border.moveTo(20, 20);

		// dark background and semi-lighter playfield background
		background = new FilledRect(0, 0, 5000, 5000, canvas);
		background.setColor(new Color(0, 0, 45));
		playfield = new FilledRect(border.getX() - 1, border.getY() - 1, border.getWidth(), border.getHeight(), canvas);
		playfield.setColor(new Color(0, 0, 50));

		Color blue = new Color(0, 0, randomDimShade.nextValue());
		for (int i = 2; i <= ncells + 2; i++) {
			lines[lineN] = new Line(playfield.getX(), i * cellsize - 1, ncells * cellsize + playfield.getX(), i * cellsize - 1, canvas);
			lines[lineN].setColor(blue);
			lines[lineN + 1] = new Line(i * cellsize - 1, playfield.getY(), i * cellsize - 1, ncells * cellsize + playfield.getY(), canvas);
			lines[lineN + 1].setColor(blue);
			lineN += 2;
		}

		border.move(-1, -1);
		border.setSize(border.getWidth() + 1, border.getHeight() + 1);
		border.sendToFront();

		apple.sendToFront();
		stem = new FilledRect(apple.getX() + apple.getWidth() / 2 - 1, apple.getY() - 3, 2, 4, canvas);
		stem.setColor(Color.green);

		start();
	}

	private void initBody() {
		moveApple();

		// send the snake into a random direction each time the snake is reincarnated.
		Direction dir = curDirection;
		while (dir == curDirection) {
			switch (randomDirection.nextValue()) {
				case 0:
					setDirection(Direction.UP);
					break;
				case 1:
					setDirection(Direction.DOWN);
					break;
				case 2:
					setDirection(Direction.LEFT);
					break;
				case 3:
					setDirection(Direction.RIGHT);
					break;
			}
		}

		body[0] = new FilledRect(playfield.getWidth() / 2, playfield.getHeight() / 2, cellsize, cellsize, canvas);
		nextCell = body[0].getLocation();

		toGrow = 3;
		currentLength = 1;
	}

	// moveApple method: generate new random location while making sure that
	// the apple doesn't overlap the snake.
	private void moveApple() {
		apple.show();
		stem.show();

		Location loc = new Location(-1, -1);
		while (loc.getX() == -1 || this.contains(loc)) {
			loc = new Location(randomCell.nextValue() * cellsize + playfield.getX() + 2,
			                   randomCell.nextValue() * cellsize + playfield.getY() + 2);
		}
		apple.moveTo(loc);
		stem.moveTo(loc.getX() + apple.getWidth() / 2 - 1, loc.getY() - 3);
	}

	private void stretch() {
		// flash the border, grid, and apple in corresponding shades of red, blue, or green.
		apple.setColor(new Color(randomShade.nextValue(), 0, 0));
		stem.setColor(new Color(0, randomShade.nextValue(), 0));
		border.setColor(new Color(0, 0, randomShade.nextValue()));
		for (int i = 0; i < lineN; i++)
			lines[i].setColor(new Color(0, 0, randomDimShade.nextValue()));

		// generate the location of where the head will be moved to next.
		nextCell = body[0].getLocation();
		switch (curDirection) {
			case UP:
				nextCell.translate(0, -cellsize);
				break;
			case RIGHT:
				nextCell.translate(cellsize, 0);
				break;
			case DOWN:
				nextCell.translate(0, cellsize);
				break;
			case LEFT:
				nextCell.translate(-cellsize, 0);
				break;
		}

		// if next direction is bound for an out of bounds area, don't move it.
		if (!outOfBounds()) {
			// move each cell of the body to the location of the cell ahead of it.
			for (int i = 0; i < currentLength; i++) {
				Location loc = body[i].getLocation();
				body[i].moveTo(nextCell);
				body[i].setColor(new Color(randomColor.nextValue(), randomColor.nextValue(), randomColor.nextValue()));

				nextCell = loc;
			}
			// grow the body
			for (int i = 0; i < toGrow; i++) {
				body[currentLength++] = new FilledRect(body[currentLength - 2].getLocation(), cellsize, cellsize, canvas);
			}
		}
		// make sure to reset toGrow to stop growing
		toGrow = 0;
	}

	private void shrink() {
		currentLength--;
		body[currentLength].removeFromCanvas();
		body[currentLength] = null;
	}

	public void run() {
		while (true) {
			while (currentLength > 0 && !outOfBounds() && !ateSelf()) {
				stretch(); // move and/or grow the snake's body

				// determine if an apple was eaten by the snake, then move the apple
				if (body[0].overlaps(apple)) {
					toGrow = GROWTHRATE;
					moveApple();
				}

				if (toGrow < 0)
					shrink();
				if (!outOfBounds())
					pause(PAUSE_TIME);
			}

			boolean dead = false;

			apple.hide();
			stem.hide();

			// set play area and snake color to red to indicate the snake death
			if (currentLength > 0) {
				dead = true;

				for (int i = 0; i < currentLength; i++)
					body[i].setColor(new Color(randomShade.nextValue(), 0, 0));
				background.setColor(new Color(25, 0, 0));
				playfield.setColor(new Color(30, 0, 0));
				border.setColor(new Color(randomShade.nextValue() - 25, 0, 0));
				for (int i = 0; i < lineN; i++)
					lines[i].setColor(new Color(randomDimShade.nextValue() - 25, 0, 0));
			}

			if (ateSelf())
				body[0].hide();
			int longestLength = currentLength;

			if (dead)
				pause(500);
			while (currentLength > 0) {
				shrink();
				for (int i = 0; i < currentLength; i++)
					body[i].setColor(new Color(randomShade.nextValue(), 0, 0));

				// shrink snake cells with a effect of a quickening pace
				pause(Math.max(PAUSE_TIME / 5, PAUSE_TIME / 1.5 * currentLength / longestLength));
			}
			if (dead)
				pause(750);

			// reset background colors
			background.setColor(new Color(0, 0, 45));
			playfield.setColor(new Color(0, 0, 50));

			initBody();
		}
	}

	public void setDirection(Direction dir) {
		if (!dir.isOpposite(curDirection))
			curDirection = dir;
	}

	public Boolean contains(Location point) {
		for (int i = 0; i < currentLength; i++) {
			if (body[i].contains(point))
				return true;
		}
		return false;
	}

	public Boolean outOfBounds() {
		return !playfield.contains(nextCell);
	}

	public Boolean ateSelf() {
		for (int i = 0; i < currentLength; i++) {
			// detect if the head is on top somewhere INSIDE the cell, not the edge of body
			Location loc = new Location(body[i].getX() + 3, body[i].getY() + 3);
			if (body[0].contains(loc) && i > GROWTHRATE)
				return true;
		}
		return false;
	}
}
