/* Jon Crenshaw, CSIS10a
 * Lab 4: Broomball
 */
import objectdraw.*;
import java.awt.*;

public class Broomball extends WindowController {
    private static final int SCR_WIDTH = 400;
	private static final int SCR_HEIGHT = 400;
	
	private static final int BROOM_RADIUS = 10;
	private static final int DIRT_WIDTH = 25;
	private static final int DIRT_HEIGHT = DIRT_WIDTH;
	
	private FilledOval broom;
	private FilledRect dirt;
	
	private Location mousePrev;
	
    public Broomball() {
        startController(SCR_WIDTH, SCR_HEIGHT);
    }
	
	public void begin() {
		broom = new FilledOval(0, 0, BROOM_RADIUS*2, BROOM_RADIUS*2, canvas);
		dirt = new FilledRect(60, 25, DIRT_WIDTH, DIRT_HEIGHT, canvas);
		
		broom.hide();
	}
	
	private void onMousePress(Location pt) {
		mousePrev = pt;
		
		broom.show();
	}
	
	private void onMouseRelease(Location pt) {
		
	}
	
	private void onMouseDrag(Location pt) {
		
	}
	
	private void onMouseMove(Location pt) {
		
	}
	
}