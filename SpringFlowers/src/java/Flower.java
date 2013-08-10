/* Jon Crenshaw, CSIS10a
 * Lab 6: Spring Flowers
 * Class: Flower
 */

import java.awt.*;

import objectdraw.*;

public class Flower {
	private static final int PWIDTH = 20, PHEIGHT = 40, CDIM = 15, SWIDTH = 3, SHEIGHT = 75, GROW_BY = 4, SSTART = 4;
	private FilledOval petal1, petal2, center;
	private FramedOval petal1Edge, petal2Edge, centerEdge;
	private FilledRect stem;
	private RandomIntGenerator rand = new RandomIntGenerator(0, 255);
	public boolean matured = false;

	public Flower(Location point, DrawingCanvas canvas) {
		// set the flower and stem's coordinates
		int x = (int) point.getX() - PHEIGHT / 2, y = (int) point.getY() - SHEIGHT - PHEIGHT / 2;

		stem = new FilledRect(point.getX() - SWIDTH / 2, point.getY(), SWIDTH, SSTART, canvas);
		petal1Edge = new FramedOval(x, y + PHEIGHT / 2 - PWIDTH / 2, PHEIGHT, PWIDTH, canvas);
		petal2Edge = new FramedOval(x + PHEIGHT / 2 - PWIDTH / 2, y, PWIDTH, PHEIGHT, canvas);
		petal1 = new FilledOval(x, petal1Edge.getY(), PHEIGHT, PWIDTH, canvas);
		petal2 = new FilledOval(petal2Edge.getX(), y, PWIDTH, PHEIGHT, canvas);
		centerEdge = new FramedOval(x + PHEIGHT / 2 - CDIM / 2, y + PHEIGHT / 2 - CDIM / 2, CDIM, CDIM, canvas);
		center = new FilledOval(centerEdge.getX(), centerEdge.getY(), CDIM, CDIM, canvas);

		// random green color for stem
		stem.setColor(new Color(0, (new RandomIntGenerator(100, 255)).nextValue(), 0));
		randomPetalColor();

		// hide flower at first
		petal1.hide();
		petal1Edge.hide();
		petal2.hide();
		petal2Edge.hide();
		center.hide();
		centerEdge.hide();
	}

	// random flower color
	public void randomPetalColor() {
		int r = rand.nextValue(), g = rand.nextValue(), b = rand.nextValue();
		petal1.setColor(new Color(r, g, b));
		petal2.setColor(new Color(r, g, b));
		center.setColor(new Color(Math.abs(255 - r), Math.abs(255 - g), Math.abs(255 - b)));
	}

	// grow stem until it reaches SHEIGHT, then show the petals and flag as mature.
	public void grow() {
		if (stem.getHeight() < SHEIGHT) {
			stem.setSize(SWIDTH, stem.getHeight() + GROW_BY);
			stem.move(0, -GROW_BY);
		}
		else {
			petal1.show();
			petal1Edge.show();
			petal2.show();
			petal2Edge.show();
			center.show();
			centerEdge.show();
			matured = true;
		}
	}

	public boolean contains(Location point) {
		return (petal1.contains(point) || petal2.contains(point) || stem.contains(point));
	}
}
