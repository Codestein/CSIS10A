import java.awt.*;

import objectdraw.*;

public class Cloud extends ActiveObject {
	private static final int MAX_DROPS = 600;

	private RandomIntGenerator rx;
	private FilledRect water;
	private DrawingCanvas canvas;
	private Image image;

	public Cloud(DrawingCanvas canvas, FilledRect water, Image image) {
		this.canvas = canvas;
		this.water = water;
		this.image = image;
		rx = new RandomIntGenerator(-150, canvas.getWidth() - 100);
		start();
	}

	public void run() {
		for (int i = 0; i < MAX_DROPS; i++) {
			int x = rx.nextValue();
			Location loc = new Location(x, -100);
			new FallingBall(loc, canvas, image, water);
			pause((new RandomIntGenerator(10, 100)).nextValue());
		}

	}
}
