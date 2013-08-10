/* Jon Crenshaw
 * CSIS 10a - Lab 8
 * Class: Box
 */

import java.awt.*;

import objectdraw.*;

// Actually looks like a basket...
public class Box extends ActiveObject {
	private static final int EDGE_MARGIN = 15, BOX_SPEED = 20;
	private static final double WIDTH_MOD = 0.20;

	private DrawingCanvas canvas;
	private FilledRect frame;
	public FilledRect boxL, boxR, boxB;
	private Line[] sine;

	private int dif = 1, delta = 0, boxW = 0, boxH = 0, n = 0;
	public boolean running = true;

	public Box(int boxW, int boxH, Color color, int dif, FilledRect frame, DrawingCanvas canvas) {
		this.dif = dif;
		this.canvas = canvas;
		this.frame = frame;
		this.boxW = boxW;
		this.boxH = boxH;

		// Build our "box"
		double bx1 = EDGE_MARGIN, by1 = frame.getY() + frame.getHeight() - EDGE_MARGIN;
		double bx2 = bx1 + boxW - boxW * WIDTH_MOD * dif, by2 = by1;

		boxB = new FilledRect(0, 0, boxW - boxW * WIDTH_MOD * dif, 2, canvas);
		boxL = new FilledRect(0, 0, 10, boxH, canvas);
		boxR = new FilledRect(0, 0, 10, boxH, canvas);
		boxB.setColor(color);
		boxL.setColor(color);
		boxR.setColor(color);

		start();
	}

	private int sine(int x, int halfY, int maxX) {
		return (int) (Math.sin(x * 2 * Math.PI / maxX) * halfY + halfY);
	}

	private void drawSine(int maxX, int halfY) {
		// Drawing a neat vertical sine wave for no practical reason.
		// Populate sine[] only once, use hide/show() during animation.
		// The less calculations overall, the better.
		sine = new Line[maxX];
		for (int i = 0; i < maxX; i++) {
			double sineX = sine(i * 2, halfY, maxX) + frame.getX() + EDGE_MARGIN,
					sineY = frame.getHeight() + frame.getY() - EDGE_MARGIN / 2 - i;
			if (i != 0)
				sine[i - 1].setEnd(sineX, sineY);
			sine[i] = new Line(sineX, sineY, sineX, sineY, canvas);
			sine[i].setColor(new Color(100, 100, 255));
			sine[i].hide();
		}

		boxB.sendToFront();
		boxL.sendToFront();
		boxR.sendToFront();
	}

	public void run() {
		while (true) {
			while (!running)
				pause(100);

			// Calculate for x a vertical sine wave that fits our field width.
			int maxX = (int) frame.getHeight() - EDGE_MARGIN;
			int maxY1 = (int) (frame.getWidth() - EDGE_MARGIN * 2);
			int halfX = (int) maxX / 2, halfY1 = (int) maxY1 / 2;
			if (sine == null)
				drawSine(maxX, halfY1);

			while (running) {
				// Move box in a continuous sine pattern.
				int maxY2 = (int) (maxY1 - boxW + boxW * WIDTH_MOD * dif);
				int halfY2 = (int) maxY2 / 2;

				double x = sine(delta, halfY2, maxX) + frame.getX() + EDGE_MARGIN,
						y = frame.getY() + frame.getHeight() - EDGE_MARGIN + 5;

				boxB.moveTo(x, y);
				boxL.moveTo(x - 5, y + 2 - boxH);
				boxR.moveTo(x - 5 + boxW - boxW * WIDTH_MOD * dif, y + 2 - boxH);

				// Remove lines when sine reaches the top.
				if (n >= maxX) {
					for (Line line : sine)
						line.hide();
					n = 0;
				}

				if (n % 3 == 2)
					sine[n].show();

				delta += 2;
				n++;
				pause(20);
			}
		}
	}

	public void setDifficulty(int dif) {
		this.dif = dif;
		boxB.setSize(boxW - boxW * WIDTH_MOD * dif, 2);
	}
}
