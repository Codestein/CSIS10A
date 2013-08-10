import java.awt.*;

import objectdraw.*;

public class Lines extends WindowController {

	// Create a window with 400h by 500w
	public Lines() {
		startController(400, 500);
	}

	// Display text when mouse button is pressed.
	// Location point = coordinates inside window.
	//private Text text;

	private boolean begun = false;
	private Location prev_point;
	private Line prev_line;
	private Text coordinates;

	public void begin() {
		coordinates = new Text("", 5, 5, canvas);
	}

	public void onMouseRelease(Location point) {
		if (prev_line != null)
			prev_line.setColor(Color.black);

		if (prev_point != null) {
			prev_line = new Line(prev_point, point, canvas);
			prev_line.setColor(Color.red);

			// display the coordinates of the line
			coordinates.setText("x: " + prev_point + "      y: " + point);
		}

		prev_point = point;

		// text = new Text(i++, point, canvas);
		// text.setColor(Color.red);
	}

	public void onMouseExit(Location point) {
		prev_point = null;
		canvas.clear();
	}

	public static void main(String[] args) {
		(new Lines()).setVisible(true);
	}
}
