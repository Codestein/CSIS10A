import objectdraw.*;
import java.awt.*;

public class Prob3 extends WindowController {

	public Prob3() {
		startController(500,500);
	}
	
	public void begin() {
		int k = 3;
		int y = 15;
		int m = 0;
		String out = "";
		
		
		while (k >= 2) {
			m = k;
			while (m >= 0) {
				if (m%2 == 0) {
					out = out + "H";
				}
				else {
					out = out + "AA";
				}
				m = m - 1;
			}
			new Text(out, 0, y, canvas);
			y = y + 15;
			k = k - 1;
			out = "";
		}
	}
}