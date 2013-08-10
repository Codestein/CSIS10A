/* Jon Crenshaw, CSIS10a
 * Lab 5, Ex 5.8.3 "MorseCode"
 */

import objectdraw.*;
import java.awt.*;

public class MorseCode extends WindowController {
	private static final int SCR_WIDTH = 400;
	private static final int SCR_HEIGHT = 200;
	
	private String code = "Code:";
	private long pressTimer, clickTimer;
	
	private Text codeText;
	
	public MorseCode() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}
	
	public void begin() {
		codeText = new Text(code, 15, 15, canvas);
		codeText.setBold(true);
		codeText.setFontSize(15);
	}
	
	// when mouse is pressed, record the current ms, then append spaces if the click is long enough.
	public void onMousePress(Location point) {
		pressTimer = System.currentTimeMillis();
		
		if (pressTimer-clickTimer > 1000)
			code += "    ";
	}
	
	//  check timer is under 250ms, and use a dot, otherwise use a dash. record the clickTimer.
	public void onMouseRelease(Location point) {
		clickTimer = System.currentTimeMillis();
		code += (clickTimer - pressTimer < 300 ? ". " : "- ");
		codeText.setText(code);
	}
}