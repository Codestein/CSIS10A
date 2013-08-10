/* Jon Crenshaw, CSIS10a
 * Lab 4: Broomball
 */

import java.awt.*;

import objectdraw.*;

public class Broomball extends WindowController {
	private static final int SCR_WIDTH = 400;
	private static final int SCR_HEIGHT = 400;

	// broom and dirt consts
	private static final int BROOM_RADIUS = 10;
	private static final int TOUCH_PADDING = 10;

	private static final int[] BROOM_BEGINS = { 15, 15 };
	private static final int[] DIRT_BEGINS = { 100, 50 };
	private static final int[] DIRT_DIM = { 30, 30 };

	// goal
	private static final String GOAL_TEXT = "GOAL!";

	// gui objects
	private FilledOval broom;
	private FilledRect dirt, top, right, bottom, left;

	// dragging flags
	private boolean dragging = false;
	private boolean mouseIsOver = false;
	private boolean inWindow = false;
	private Location offset;

	// A grid texture for the boring white window.
	private void drawBackground() {
		Color gray = new Color(240, 240, 240);
		for (int i = 0; i <= 400; i++) {
			Line background = new Line(0, i * 5, 2000, i * 5, canvas);
			background.setColor(gray);
			background.sendToBack();
			background = new Line(i * 5, 0, i * 5, 2000, canvas);
			background.setColor(gray);
			background.sendToBack();
		}
	}

	public Broomball() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}

	public void begin() {
		drawBackground();

		top = new FilledRect(DIRT_BEGINS[0] + TOUCH_PADDING / 2, 0, DIRT_DIM[0] - TOUCH_PADDING, DIRT_BEGINS[1], canvas);
		left = new FilledRect(DIRT_BEGINS[0] + DIRT_DIM[0], DIRT_BEGINS[1] + TOUCH_PADDING / 2, 2000, DIRT_DIM[1] - TOUCH_PADDING, canvas);
		bottom = new FilledRect(DIRT_BEGINS[0] + TOUCH_PADDING / 2, DIRT_BEGINS[1] + DIRT_DIM[1], DIRT_DIM[0] - TOUCH_PADDING, 2000, canvas);
		right = new FilledRect(0, DIRT_BEGINS[1] + TOUCH_PADDING / 2, DIRT_BEGINS[0], DIRT_DIM[1] - TOUCH_PADDING, canvas);
		top.setColor(Color.yellow);
		left.setColor(Color.yellow);
		bottom.setColor(Color.yellow);
		right.setColor(Color.yellow);

		top.hide();
		left.hide();
		bottom.hide();
		right.hide();

		broom = new FilledOval(BROOM_BEGINS[0], BROOM_BEGINS[1], BROOM_RADIUS * 2, BROOM_RADIUS * 2, canvas);
		dirt = new FilledRect(DIRT_BEGINS[0], DIRT_BEGINS[1], DIRT_DIM[0], DIRT_DIM[1], canvas);

	}

	public void onMouseEnter(Location point) {
		inWindow = true;
	}

	public void onMouseExit(Location point) {
		inWindow = false;
	}

	public void onMousePress(Location point) {
		dragging = mouseIsOver;
	}

	public void onMouseRelease(Location point) {
		dragging = false;
	}

	public void onMouseDrag(Location point) {
		if (!dragging)
			return;

		// get new coordinates for the broom and move it
		broom.moveTo(new Location(point.getX() - offset.getX(), point.getY() - offset.getY()));

		// makeshift side and top bounds to check if the broom is in the left/right or top/bottom around the dirt

		if (broom.overlaps(dirt)) {
			if (broom.overlaps(top) && dirt.getY() + dirt.getHeight() < canvas.getHeight()) {
				dirt.moveTo(dirt.getX(), broom.getY() + broom.getHeight());
			}
			else if (broom.overlaps(right) && dirt.getX() + dirt.getWidth() < canvas.getWidth()) {
				dirt.moveTo(broom.getX() + broom.getWidth(), dirt.getY());
			}
			else if (broom.overlaps(bottom) && dirt.getY() > 0) {
				dirt.moveTo(dirt.getX(), broom.getY() - dirt.getHeight());
			}
			else if (broom.overlaps(left) && dirt.getX() > 0) {
				dirt.moveTo(broom.getX() - dirt.getWidth(), dirt.getY());
			}

			top.setSize(DIRT_DIM[0] - TOUCH_PADDING, dirt.getY());
			top.moveTo(dirt.getX() + TOUCH_PADDING / 2, 0);
			right.setSize(dirt.getX(), DIRT_DIM[1] - TOUCH_PADDING);
			right.moveTo(0, dirt.getY() + TOUCH_PADDING / 2);
			bottom.moveTo(dirt.getX() + TOUCH_PADDING / 2, dirt.getY() + dirt.getHeight());
			left.moveTo(dirt.getX() + dirt.getWidth(), dirt.getY() + TOUCH_PADDING / 2);
		}
	}

	public void onMouseMove(Location point) {
		offset = new Location(point.getX() - broom.getX(), point.getY() - broom.getY());

		// exit if dragging, don't need to check if mouse is over, derp derp.
		if (dragging)
			return;

		// check if the mouse is currently over the broom.
		mouseIsOver = broom.contains(point);

		// change the color of the "broom" as it is moused over.
		broom.setColor(mouseIsOver ? Color.red : Color.black);
	}

	public static void main(String[] args) {
		(new Broomball()).setVisible(true);
	}
}
