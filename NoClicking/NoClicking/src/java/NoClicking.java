// Lab 1
// CSIS10A Fall 2010
// Jon Crenshaw

import java.awt.*;

import objectdraw.*;

public class NoClicking extends WindowController {
	public NoClicking() {
		startController(325, 425);
	}

	// Declare ObjectDraw objects
	private FilledRect rect;
	private FilledOval circle;
	private Text text, ouch, coords;
	private Line circleLine;
	private Line wtfL1;
	private Line wtfL2;
	private Line wtfR1;
	private Line wtfR2;
	private Line wtfMouth;

	// Sign rectangle parameters
	public int rectX = 100;
	public int rectY = 50;
	public int rectWidth = 100;
	public int rectHeight = 100;

	// Sign circle parameters
	private int circlePadding = 5;
	private int circleX = rectX + circlePadding;
	private int circleY = rectY + circlePadding;
	private int circleWidth = rectWidth - circlePadding * 2;
	private int circleHeight = rectHeight - circlePadding * 2;
	private int[] circleMid = { circleX + circleWidth / 2, circleY + circleHeight / 2 };

	// Calculate a coordinate on a circle's arc given a midpoint, radius, and an angle.
	// (r * cos(a) + midX, r * sin(a) + midY) = (x, y)
	private double deg = Math.PI / 180; // degree unit as radians

	public double[] arc(double r, int a, int[] m) {
		double angle = deg * (360 - a); // Note: Angles are reversed in the window's coordinate system.
		return new double[] { r * Math.cos(angle) + m[0], r * Math.sin(angle) + m[1] }; // (x, y)
	}

	// Not a very happy face
	private static final double r2 = 8; // eye radius
	private static final String ouchText = "ouch...";

	// Left eye line starting coords
	private int[] leftMid = { circleMid[0] - 15, circleMid[1] - 12 };
	private double[] left1 = arc(r2, 135, leftMid);
	private double[] left2 = arc(r2, 45, leftMid);

	// Right eye line starting coords
	private int[] rightMid = { circleMid[0] + 15, circleMid[1] - 12 };
	private double[] right1 = arc(r2, 135, rightMid);
	private double[] right2 = arc(r2, 45, rightMid);

	// Mouth line coords
	private int[] mouthA = { leftMid[0], leftMid[1] + 35 };
	private int[] mouthB = { rightMid[0], rightMid[1] + 35 };

	// Sign text parameters
	private static final String signString = "DON'T CLICK";
	private static final int textX = 115;
	private static final int textY = 92;

	// Sign post rect parameters
	private static final int postHeight = 150;
	private static final int postWidth = 3;

	// Sign base line parameters
	private int baseWidth = rectWidth;

	// Color toggling event checks
	private boolean mouseIsDown = false;
	private boolean insideWindow = false;

	public void begin() {
		// coords text
		coords = new Text("(0, 0)", 5, 5, canvas);

		// Create sign's background rectangle
		rect = new FilledRect(rectX, rectY, rectWidth, rectHeight, canvas);
		rect.setColor(Color.green);
		new FramedRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), canvas); // border

		// Sign circle
		circle = new FilledOval(circleX, circleY, circleWidth, circleHeight, canvas);
		circle.setColor(Color.red);
		new FramedOval(circleX, circleY, circleWidth, circleHeight, canvas); // border

		// Sign text
		text = new Text(signString, textX, textY, canvas);
		text.setBold(true);
		text.setFontSize(11);
		text.setColor(Color.yellow);

		// Sign circle cross
		double[] cross = arc(circleHeight / 2, 135, circleMid);
		circleLine = new AngLine(cross[0], cross[1], circleHeight, deg * 315, canvas);

		// WTFCLICKEDFACE
		wtfL1 = new AngLine(left1[0], left1[1], r2 * 2, deg * 315, canvas);
		wtfL2 = new AngLine(left2[0], left2[1], r2 * 2, deg * 225, canvas);
		wtfR1 = new AngLine(right1[0], right1[1], r2 * 2, deg * 315, canvas);
		wtfR2 = new AngLine(right2[0], right2[1], r2 * 2, deg * 225, canvas);
		wtfMouth = new Line(mouthA[0], mouthA[1], mouthB[0], mouthB[1], canvas);
		wtfL1.hide();
		wtfL2.hide();
		wtfR1.hide();
		wtfR2.hide();
		wtfMouth.hide();

		// ouch :(
		ouch = new Text(ouchText, circleMid[0] + 60, circleMid[1], canvas);
		ouch.setBold(true);
		ouch.hide();

		// Sign post
		double postX = rect.getX() + rect.getWidth() / 2 - Math.floor(postWidth / 2);
		double postY = rect.getHeight() + rect.getY() + 1;
		new FilledRect(postX, postY, postWidth, postHeight, canvas);

		// Sign base
		double baseX = rect.getX();
		double baseY = postY + postHeight;
		new Line(baseX, baseY, baseX + baseWidth, baseY, canvas);
	}

	public void onMouseClick(Location point) {
		// No one here but us chickens...
	}

	public void onMouseEnter(Location point) {
		insideWindow = true;
		// Must prevent colors from changing when
		// mouse is dragged in/out of the window.
		if (!mouseIsDown)
			rect.setColor(Color.yellow);
	}

	public void onMouseExit(Location point) {
		insideWindow = false;
		if (!mouseIsDown)
			rect.setColor(Color.green);
	}

	public void onMousePress(Location point) {
		rect.setColor(Color.red);
		circle.setColor(Color.yellow);

		text.hide();
		circleLine.hide();

		wtfL1.show();
		wtfL2.show();
		wtfR1.show();
		wtfR2.show();
		wtfMouth.show();
		ouch.show();

		mouseIsDown = true;
	}

	public void onMouseRelease(Location point) {
		// Check for insideWindow and choose the correct color.
		// The mouse button may be released outside the window,
		// which would mess up the rect color toggle.
		rect.setColor(insideWindow ? Color.yellow : Color.green);
		circle.setColor(Color.red);

		text.show();
		circleLine.show();

		wtfL1.hide();
		wtfL2.hide();
		wtfR1.hide();
		wtfR2.hide();
		wtfMouth.hide();
		ouch.hide();

		mouseIsDown = false;
	}

	// Window helper coordinates text
	String coordsString(Location point) {
		return String.format("(%.0f, %.0f)", point.getX(), point.getY());
	}

	public void onMouseMove(Location point) {
		// disply cursor's location
		coords.setText(coordsString(point));
	}

	public void onMouseDrag(Location point) {
		coords.setText(coordsString(point));
	}

	public static void main(String[] args) {
		(new NoClicking()).setVisible(true);
	}
}
