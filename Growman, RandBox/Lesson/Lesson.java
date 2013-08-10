
import objectdraw.*;
import java.awt.*;

public class Lesson extends WindowController {
	private Location pointBegin;
	private RandomIntGenerator random = new RandomIntGenerator(0, 255);
	private Line line;
	
	public Lesson() {
		startController(325, 425);
    }

	public void onMousePress(Location point) {
		pointBegin = point;
	}
	
	public void onMouseDrag(Location point) {
		line = new Line(pointBegin, point, canvas);
		line.setColor(new Color(random.nextValue(), random.nextValue(), random.nextValue()));
	}
}
