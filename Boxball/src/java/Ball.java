/* Jon Crenshaw
 * CSIS 10a - Lab 8
 * Class: Ball
 * Requires: Box
 */

import java.awt.*;

import objectdraw.*;

public class Ball extends ActiveObject {
	private static final double ACCELERATION = 9.80665;
	private static final int TIME = 7;

	private static final String FAIL = "Oh Bugger.",
			SCORE = "  SCORE!  ";

	private int stopY = 0, delta = 0;
	private Text status;
	private Box box;
	private Color color;

	public FilledOval ball;
	public boolean inPlay;

	public Ball(int dim, Color color, int stopY, Text status, Box box, DrawingCanvas canvas) {
		// Initializing our ball object.
		this.stopY = stopY;
		this.color = color;
		this.status = status;
		this.box = box;

		ball = new FilledOval(0, 0, dim, dim, canvas);
		ball.setColor(color);
		ball.hide();
		start();
	}

	public void drop(Location loc) {
		delta = 0; // reset velocity time
		ball.setColor(color);
		ball.sendToFront();
		ball.moveTo(loc.getX(), loc.getY());
		ball.show();
		inPlay = true; // Ball falls until it hits something.
	}

	private void ballHit() {
		if (ball.overlaps(box.boxB)) {
			// Ball hit the bottom of the box, we have a winner!
			ball.moveTo(ball.getX(), box.boxB.getY() - ball.getHeight());
			ball.setColor(Color.green);
			status.setText(SCORE);
		}
		else {
			// Ball hit a side of the box or it reached stopY, either is FAIL.
			ball.setColor(Color.red);
			status.setText(FAIL);

			// Wouldn't want the ball to overlap the bottom of the box or bottom.
			if (!ball.overlaps(box.boxL) && !ball.overlaps(box.boxR))
				ball.moveTo(ball.getX(), stopY);
		}

		// Stop the box and wait for 0.5 sec, then continue.
		box.running = false;
		pause(500);
		box.running = true;

		// We're done with this ball until we want to show it again.
		ball.hide();
		inPlay = false;
	}

	public void run() {
		while (true) {
			while (inPlay) {
				// Ball falls with gravity-like velocity.
				pause(TIME);
				delta += TIME;
				ball.move(0, ACCELERATION * delta / 1000);

				// Check if the ball hit something.
				if (ball.overlaps(box.boxB) || ball.getY() >= stopY || ball.overlaps(box.boxL) || ball.overlaps(box.boxR))
					ballHit();
			}
			pause(100);
		}
	}
}
