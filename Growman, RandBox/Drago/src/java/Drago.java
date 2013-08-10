/* Jon Crenshaw, CSIS10a
 * Ex 2.8.2
 */

import java.awt.*;

import objectdraw.*;

public class Drago extends WindowController {
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 400;

	// Rect coords
	private static final int RECT_WIDTH = 100;
	private static final int RECT_HEIGHT = 100;

	private static final int DRAG_MARGIN = 4;

	// Objects
	private FramedRect framedRect;
	private FilledRect filledRect;
	private Text dragMe;
	private Location offset;

	// Allow dragging is set in mouse events
	private boolean dragging = false;

	private void drawBackground() {
		for (int i = 0; i <= 400; i++) {
			Color gray = new Color(240, 240, 240);
			Line background = new Line(0, i * 5, 2000, i * 5, canvas);
			background.setColor(gray);
			background.sendToBack();
			background = new Line(i * 5, 0, i * 5, 2000, canvas);
			background.setColor(gray);
			background.sendToBack();
		}
	}

	public Drago() {
		startController(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public void begin() {
		drawBackground();

		filledRect = new FilledRect(canvas.getWidth() / 2 - RECT_WIDTH / 2,
		                            canvas.getHeight() / 2 - RECT_HEIGHT / 2,
		                            RECT_WIDTH, RECT_HEIGHT, canvas);
		framedRect = new FramedRect(canvas.getWidth() / 2 - RECT_WIDTH / 2,
		                            canvas.getHeight() / 2 - RECT_HEIGHT / 2,
		                            RECT_WIDTH, RECT_HEIGHT, canvas);

		dragMe = new Text("Drag me!", filledRect.getX() + 5, filledRect.getY() + 5, canvas);
		dragMe.setColor(Color.orange);
		dragMe.setFontSize(10);
		dragMe.setBold(true);

		filledRect.setColor(Color.gray);
	}

	// Events
	public void onMousePress(Location point) {
		offset = new Location(point.getX() - framedRect.getX(),
		                      point.getY() - framedRect.getY());

		// Check if mouse click is inside the rect's bounds + margin
		if (offset.getX() > -DRAG_MARGIN && offset.getY() > -DRAG_MARGIN &&
		    offset.getX() <= RECT_WIDTH + DRAG_MARGIN && offset.getY() <= RECT_HEIGHT + DRAG_MARGIN) {
			filledRect.hide();
			dragMe.hide();
			dragging = true;
		}
	}

	public void onMouseRelease(Location point) {
		if (dragging) {
			filledRect.show();
			dragMe.show();
			dragging = false;
		}
	}

	public void onMouseDrag(Location point) {
		if (!dragging)
			return;
		// drag rect
		double newX = point.getX() - offset.getX();
		double newY = point.getY() - offset.getY();
		framedRect.moveTo(newX, newY);
		filledRect.moveTo(newX, newY);
		dragMe.moveTo(newX + 5, newY + 5);
	}

	public static void main(String[] args) {
		(new Drago()).setVisible(true);
	}
}
