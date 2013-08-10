import java.awt.*;

import objectdraw.*;

public class FallingBall extends ActiveObject {
	private DrawingCanvas canvas;
	private VisibleImage drop;
	public FilledRect water;

	public FallingBall(Location point, DrawingCanvas acanvas, Image img, FilledRect water) {
		this.canvas = acanvas;
		this.water = water;
		drop = new VisibleImage(img, point, canvas);
		drop.sendBackward();
		start();
	}

	public void run() {
		while (drop.getY() < water.getY()) {
			drop.move(0.25, 1);
			pause(3);
		}
		drop.removeFromCanvas();
		water.setHeight(water.getHeight() + 1);
		water.move(0, -1);
	}
}
