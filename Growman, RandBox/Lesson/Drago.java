
import objectdraw.*;
import java.awt.*;

public class Drago extends WindowController {
	private FramedRect rect;
	private Location move;
	
	public Drago() {
		startController(400,400);
	}
	
	public void begin() {
		rect = new FramedRect(canvas.getWidth()/2, canvas.getHeight()/2, 50, 50, canvas);
	}
	
	public void onMousePress(Location point) {
		move = point;
	}
	
	public void onMouseDrag(Location point) {
		double newX = Math.sin(point.getX()/200)*100;
		double newY = Math.sin(point.getX()/200)*100;
		rect.moveTo(newX, newY);
	}
	
}