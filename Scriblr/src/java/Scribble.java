/* Jon Crenshaw
 * 10a - Scribble
 */

import java.awt.*;

import objectdraw.Line;
import objectdraw.Location;

// this class holds an array of line segments
// that make up one scribble
public class Scribble {
	private int numberLines = 0;        // count of line segments in scribble
	private Line[] lines = new Line[1]; // array to hold line segments

	// resizes line array for unlimited length, arbitrary limits suck!
	public Line[] resize(Line[] array) {
		Line[] newArray = new Line[array.length * 2];
		System.arraycopy(array, 0, newArray, 0, array.length);

		System.out.print((newArray.length > 2 ? ", " : "\nLine[] resized to ") + newArray.length);
		return newArray;
	}

	public void addLine(Line segment) {
		if (numberLines >= lines.length) {
			lines = resize(lines);
		}
		lines[numberLines] = segment;
		numberLines++;
	}

	public void setColor(Color color) {
		for (int i = 0; i < numberLines; i++)
			lines[i].setColor(color);
	}

	public void erase() {
		for (int i = 0; i < numberLines; i++) {
			lines[i].removeFromCanvas();
			lines[i] = null;
		}
	}

	public boolean contains(Location pt) {
		for (int i = 0; i < numberLines; i++) {
			if (lines[i].contains(pt)) {
				return true;
			}
		}
		return false;
	}

	public void move(double dx, double dy) {
		for (int i = 0; i < numberLines; i++) {
			lines[i].move(dx, dy);
		}
	}
}
