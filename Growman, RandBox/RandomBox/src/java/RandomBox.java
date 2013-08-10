/* Jon Crenshaw, CSIS10a
 * Ex 3.12.3
 */

import java.awt.*;

import objectdraw.*;

public class RandomBox extends WindowController {
	private static final int SCREEN_WIDTH = 200;
	private static final int SCREEN_HEIGHT = 300;

	private static final int DRAG_MARGIN = 4;

	private RandomIntGenerator randomSize = new RandomIntGenerator(20, 120);
	private RandomIntGenerator randomColor = new RandomIntGenerator(0, 255);

	private FramedRect framedRect;
	private FilledRect filledRect;
	private Text tLocation, tDimensions, tColor, tHello, tQue;
	private Location offset;
	private Color tempColor;

	private boolean dragging = false;
	private boolean mouseIsOver = false;
	private boolean inWindow = false;

	// A little striped texture for the bland window.
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

	public void generateBoxStyle() {
		// Generate a random height and width
		int width = randomSize.nextValue();
		int height = randomSize.nextValue();
		double x = canvas.getWidth() / 2 - width / 2;
		double y = canvas.getHeight() / 2 - height / 2;

		filledRect.setSize(width, height);
		framedRect.setSize(width, height);
		filledRect.moveTo(x, y);
		framedRect.moveTo(x, y);

		// Set the inner box color
		int r1 = randomColor.nextValue();
		int g1 = randomColor.nextValue();
		int b1 = randomColor.nextValue();
		filledRect.setColor(new Color(r1, g1, b1));

		// Inverse the previous color for the border.
		int r2 = 255 - r1;
		int g2 = 255 - g1;
		int b2 = 255 - b1;
		framedRect.setColor(new Color(r2, g2, b2));
	}

	private void sayHello() {
		tHello.moveTo(canvas.getWidth() / 2 - tHello.getWidth() / 2,
		              canvas.getHeight() / 2 - tHello.getHeight() / 2 - 10);
		tHello.show();
	}

	private void giveQue() {
		tQue.setText((!inWindow || dragging) ? "" : (mouseIsOver ? "Drag!" : (inWindow ? "Hover me" : "")));
		tQue.moveTo(filledRect.getX() + filledRect.getWidth() / 2 - tQue.getWidth() / 2,
		            filledRect.getY() + filledRect.getHeight() + 3);

	}

	private void printConsole() {
		tLocation.setText(String.format("x:%.0f, y:%.0f", filledRect.getX(), filledRect.getY()));
		tDimensions.setText(String.format("w:%.0f, h:%.0f", filledRect.getWidth(), filledRect.getHeight()));
		tColor.setText(String.format("r:%d, g:%d, b:%d",
		                             filledRect.getColor().getRed(), filledRect.getColor().getGreen(), filledRect.getColor().getBlue()));
	}

	public RandomBox() {
		startController(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public void begin() {
		drawBackground();

		filledRect = new FilledRect(0, 0, 0, 0, canvas);
		framedRect = new FramedRect(0, 0, 0, 0, canvas);
		tLocation = new Text("", 5, 5, canvas);
		tDimensions = new Text("", 5, 20, canvas);
		tColor = new Text("", 5, 35, canvas);
		tQue = new Text("", 5, 5, canvas);
		tQue.setColor(new Color(200, 200, 200));
		tQue.setFontSize(10);
		tQue.setBold(true);
		tHello = new Text("Hello...?", 0, 0, canvas);
		tHello.setColor(new Color(200, 200, 200));
		tHello.setBold(true);
		sayHello();
	}

	// Events
	public void onMousePress(Location point) {
		if (mouseIsOver)
			dragging = true;

		giveQue();
		printConsole();
	}

	public void onMouseRelease(Location point) {
		if (dragging) {
			dragging = false;
			if (!inWindow)
				onMouseExit(point);
		}

		giveQue();
	}

	public void onMouseDrag(Location point) {
		if (!dragging)
			return;
		double newX = point.getX() - offset.getX();
		double newY = point.getY() - offset.getY();
		framedRect.moveTo(newX, newY);
		filledRect.moveTo(newX, newY);
		printConsole();
		giveQue();
	}

	public void onMouseMove(Location point) {
		if (dragging)
			return;

		offset = new Location(point.getX() - framedRect.getX(),
		                      point.getY() - framedRect.getY());
		mouseIsOver = (offset.getX() > -DRAG_MARGIN && offset.getY() > -DRAG_MARGIN &&
		               offset.getX() <= filledRect.getWidth() + DRAG_MARGIN && offset.getY() <= filledRect.getHeight() + DRAG_MARGIN);

		if (mouseIsOver && tempColor == null) {
			tempColor = filledRect.getColor();
			filledRect.setColor(new Color(Math.min(tempColor.getRed() + 20, 255),
			                              Math.min(tempColor.getGreen() + 20, 255),
			                              Math.min(tempColor.getBlue() + 20, 255)));
		}
		else if (!mouseIsOver && tempColor != null) {
			filledRect.setColor(tempColor);
			tempColor = null;
		}

		giveQue();
		printConsole();
	}

	public void onMouseEnter(Location point) {
		inWindow = true;

		if (dragging)
			return;
		generateBoxStyle();
		filledRect.show();
		framedRect.show();
		tHello.hide();
		giveQue();
	}

	public void onMouseExit(Location point) {
		inWindow = false;

		if (dragging)
			return;
		filledRect.hide();
		framedRect.hide();
		sayHello();
		giveQue();
		tLocation.setText("");
		tDimensions.setText("");
		tColor.setText("");
	}

	public static void main(String[] args) {
		(new RandomBox()).setVisible(true);
	}
}
