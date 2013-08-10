/* Jon Crenshaw
   CSIS 10a: 10 Dec 2010
   Nibbler: Nibbles.java
*/

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objectdraw.FilledRect;
import objectdraw.FramedRect;
import objectdraw.WindowController;

public class Nibbles extends WindowController implements KeyListener {
	private static final int CELLSIZE = 10;  // these two define a "field" of 50x50 cells
	private static final int NCELLS = 36;      //  each having size 10.

	private FramedRect border = new FramedRect(20, 20, NCELLS * CELLSIZE, NCELLS * CELLSIZE, canvas);
	// draw a border around field

	private Snake theSnake;    // The snake that moves around the screen

	// Remembers if there is a key depressed currently.
	private boolean keyDown = false;

	public Nibbles() {
		startController(CELLSIZE * NCELLS + 120, CELLSIZE * NCELLS + 140);
	}

	public void begin() {
		FilledRect apple = new FilledRect(-1, -1, CELLSIZE - 2, CELLSIZE - 2, canvas);
		// apple starts at same position but moves to new random location after snake eats it
		apple.setColor(Color.RED);
		theSnake = new Snake(NCELLS, CELLSIZE, apple, border, canvas);
		// snake needs to remember the field info, the apple, the border and the canvas

		// Get ready to handle the arrow keys
		requestFocus();
		addKeyListener(this);
		canvas.addKeyListener(this);
	}

	// Required by KeyListener Interface.
	public void keyTyped(KeyEvent e) {
	}

	// Remember that the key is no longer down.
	public void keyReleased(KeyEvent e) {
		keyDown = false;
	}

	// Handle the arrow keys by telling the snake to go in the direction of the arrow.
	public void keyPressed(KeyEvent e) {
		if (!keyDown) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					theSnake.setDirection(Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
					theSnake.setDirection(Direction.DOWN);
					break;
				case KeyEvent.VK_LEFT:
					theSnake.setDirection(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					theSnake.setDirection(Direction.RIGHT);
					break;
			}
		}
		keyDown = true;
	}

	public static void main(String[] args) {
		(new Nibbles()).setVisible(true);
	}
}
