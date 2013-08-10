/* Jon Crenshaw, CSIS10a
 * Lab 5, Ex 5.8.2 "DNAGenerator"
 */

import objectdraw.*;
import java.awt.*;

public class DNAGenerator extends WindowController {
	private static final int SCR_WIDTH = 400;
	private static final int SCR_HEIGHT = 200;
	
	private int clickCount = 0;
	private String strandString = "";
	private String[] bases = {"G","A","T","C"};
	
	private Text clickMe, strand;
	private RandomIntGenerator random = new RandomIntGenerator(0, bases.length-1);
	
	public DNAGenerator() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}
	
	public void begin() {
		clickMe = new Text("Click to generate a strand of DNA", 15, 15, canvas);
		strand = new Text(strandString, 15, 40, canvas);
	}
	
	public void onMouseClick(Location point) {
		// clear the string and count if >= 20
		if (clickCount >= 20) {
			strandString = "";
			clickCount = 0;
		}
		
		clickCount++;
		
		// append a random letter from bases
		strandString += bases[random.nextValue()] + " ";
		strand.setText(strandString);
	}
}