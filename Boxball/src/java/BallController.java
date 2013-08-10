import java.awt.*;

import objectdraw.FilledRect;
import objectdraw.Location;
import objectdraw.WindowController;

public class BallController extends WindowController {
	private Image rainGIF;
	private FilledRect water;

	public BallController() {
		startController(700, 700);
	}

	public void begin() {
		rainGIF = getImage("raindrop.gif");
		water = new FilledRect(0, canvas.getHeight() - 1, canvas.getWidth(), 1, canvas);
		water.setColor(Color.blue);
		new Cloud(canvas, water, rainGIF);
	}

	public void onMouseRelease(Location point) {
		new FallingBall(point, canvas, rainGIF, water);
	}
}
