/* Jon Crenshaw, CSIS10a
 * Lab 5, Ex 5.8.1 "ElapsedTime"
 */

import objectdraw.*;
import java.awt.*;

public class ElapsedTime extends WindowController {
	private static final int SCR_WIDTH = 250;
	private static final int SCR_HEIGHT = 150;
	
	private long prevClick = 0;
	
	private Text countText;
	
	public ElapsedTime() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}
	
	public void begin() {
		countText = new Text("Click!", 15, 15, canvas);
	}
	
	public void onMouseClick(Location pt) {
		long now = System.currentTimeMillis();
		
		// if the previous click has been recorded, write seconds to output.
		countText.setText(prevClick > 0 ? String.format("Seconds since last click: %.1f", (float)(now - prevClick) / 1000) : "Click again!");		
		prevClick = now;
	}
}