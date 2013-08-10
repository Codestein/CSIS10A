/* Jon Crenshaw
 * CSIS 10a - Exam 1
 * Class: CalcController
 * Requires: Calculator
 */

import objectdraw.*;
import java.awt.*;

public class CalcController extends WindowController {
	// define instance variables here
	private Calculator calc;
	private Location lastLocation;
	private boolean dragging;
		 
	// constructor that creates window
	public CalcController() {
		startController(700, 700);
	}

	public void begin() {
		calc = new Calculator(20, 20, 50, canvas);
	}
	public void onMouseClick(Location mouse) {
		calc.key(mouse);
	} 

	public void onMousePress(Location mouse){
		dragging = calc.contains(mouse);
	}

	public void onMouseDrag(Location mouse){
		if (dragging) {
			Location loc = calc.getLocation();
			double dx = mouse.getX()-loc.getX();
			double dy = mouse.getY()-loc.getY();
			calc.move(dx, dy);
		}
	}

	public void onMouseRelease(Location mouse){
		dragging = false;
	}

	public void onMouseEnter(Location mouse) {
		calc.clear();
	}
}