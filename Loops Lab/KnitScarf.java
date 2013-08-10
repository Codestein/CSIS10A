/* Jon Crenshaw, CSIS10a
 * Lab 7: KnitScarf
 */
import java.awt.*;
import objectdraw.*;

public class KnitScarf extends WindowController {
	private static final int SCR_WIDTH = 450, SCR_HEIGHT = 450,
	                         CIRCLES = 6, START_X = 50, START_Y = 50,
	                         MARGIN = 10, MAX_D = 120;
	
	public KnitScarf() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}

	public void begin() {
		for (int i=0; i<CIRCLES; i++) {
			int x = START_X+i*MARGIN, y = START_Y+i*MARGIN;
			int dim = MAX_D-i*MARGIN*2;
			
			FilledOval circle = new FilledOval(x, y, dim, dim, canvas);
			circle.setColor(i%2==0 ? Color.red : Color.white);
			
		}
	}
}