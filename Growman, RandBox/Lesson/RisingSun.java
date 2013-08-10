import objectdraw.*;
import java.awt.*;

public class RisingSun extends WindowController {
	private FilledOval sol;
	private int screenH = 200;
	private int screenW = 200;
	
	public RisingSun() {
		startController(screenW,screenH);
	}
	
	public void begin() {
		sol = new FilledOval(screenH-40, 50, 80, 80, canvas);
		sol.setColor(Color.yellow);
	}
	
	public void onMouseClick(Location point) {
		sol.move(0, -5);
	}	
}