/* Jon Crenshaw, CSIS10a
 * Lab 6: Spring Flowers
 * Class: Spring, uses Flower
 */

import java.awt.*;

import objectdraw.*;

public class Spring extends WindowController {
	private static final int SCR_WIDTH = 600, SCR_HEIGHT = 400, SKY_HEIGHT = 200,
			GRASS_HEIGHT = SCR_HEIGHT - SKY_HEIGHT;

	private static final Color BRIGHT_SUN = new Color(255, 255, 0),
			DIM_SUN = new Color(255, 175, 0),
			SKY_COLOR = new Color(0, 102, 201),
			GRASS_COLOR = new Color(122, 178, 38),
			WARN_COLOR = new Color(200, 200, 200);

	private static final String WARN_TEXT = "Warning: Too much sun may kill flowers.";

	private Flower flower;
	private FilledRect sky, grass;
	private FilledOval sun;
	private Text warn;

	public Spring() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}

	public void begin() {
		canvas.clear();
		sky = new FilledRect(0, 0, 600, SKY_HEIGHT, canvas);
		grass = new FilledRect(0, SKY_HEIGHT, SCR_WIDTH, GRASS_HEIGHT, canvas);
		sun = new FilledOval(50, 50, 50, 50, canvas);

		sky.setColor(SKY_COLOR);
		grass.setColor(GRASS_COLOR);
		sun.setColor(DIM_SUN);

		warn = new Text(WARN_TEXT, 50, 4, canvas);
		warn.setColor(WARN_COLOR);
		warn.setFontSize(10);
	}

	public void onMousePress(Location point) {
		if (sun.contains(point))
			sun.setColor(BRIGHT_SUN); // sun is getting hotter.
		else if (!sky.contains(point) && (flower == null || (flower.matured && !flower.contains(point))))
			flower = new Flower(point, canvas); // create flower in grass, and only if prev is matured
		else if (flower != null && flower.contains(point))
			flower.randomPetalColor(); // random color for last flower
	}

	// kill the flowers when the sun "cools"
	public void onMouseRelease(Location point) {
		if (sun.getColor() != BRIGHT_SUN)
			return;
		sun.setColor(DIM_SUN);
		begin();
	}

	public void onMouseDrag(Location point) {
		if (flower != null)
			flower.grow();
	}

	public static void main(String[] args) {
		(new Spring()).setVisible(true);
	}
}
