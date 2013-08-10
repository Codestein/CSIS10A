/* Jon Crenshaw, CSIS10a
 * Ex 3.12.2
 */

import java.awt.*;

import objectdraw.*;

public class GrowMan extends WindowController {
	// Incoming River of Constants...
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 700;

	// Min/max scales and scale "step" per pixel.
	private static final double SCALE_MODIFIER = 0.05;
	private static final double MIN_SCALE = 0.50;
	private static final double MAX_SCALE = 15.00;

	// Top Hat Man's head
	private static final int HEAD_RADIUS = 6;
	private static final int HEAD_DIAMETER = HEAD_RADIUS * 2;
	private static final double HEAD_X = SCREEN_WIDTH / 2 - HEAD_RADIUS;
	private static final int HEAD_Y = 50;

	// Top Hat Man's top hat!
	private static final double BRIM_X1 = HEAD_X;
	private static final double BRIM_Y1 = HEAD_Y + 0.5;
	private static final double BRIM_X2 = HEAD_X + HEAD_RADIUS * 2;
	private static final double BRIM_Y2 = HEAD_Y + 0.5;
	private static final double HAT_WIDTH = (BRIM_X2 - BRIM_X1) / 1.5;
	private static final double HAT_HEIGHT = 1.5;
	private static final double HAT_X = (BRIM_X2 - BRIM_X1) / 5.5 + BRIM_X1;
	private static final double HAT_Y = BRIM_Y1 - HAT_HEIGHT;

	// Man's torso length and position
	private static final double TORSO_LENGTH = HEAD_RADIUS * 2.5;
	private static final double TORSO_X = HEAD_X + HEAD_RADIUS;
	private static final double TORSO_Y = HEAD_Y + HEAD_RADIUS * 2;
	private static final double TORSO_END_X = TORSO_X;
	private static final double TORSO_END_Y = TORSO_Y + TORSO_LENGTH;

	// Limbs
	private static final int LIMB_SIZE = 8;
	private static final double PIT_X = TORSO_X;
	private static final double PIT_Y = TORSO_Y + TORSO_LENGTH / 2;
	private static final double ARM_LX = PIT_X - LIMB_SIZE;
	private static final double ARM_LY = PIT_Y - LIMB_SIZE;
	private static final double ARM_RX = PIT_X + LIMB_SIZE;
	private static final double ARM_RY = PIT_Y - LIMB_SIZE;
	private static final double LEG_LX = TORSO_END_X - LIMB_SIZE;
	private static final double LEG_LY = TORSO_END_Y + LIMB_SIZE;
	private static final double LEG_RX = TORSO_END_X + LIMB_SIZE;
	private static final double LEG_RY = TORSO_END_Y + LIMB_SIZE;

	// ObjectDraw objects
	private FramedOval head;
	private Line torso, armLeft, armRight, legLeft, legRight, hatBrim;
	private Text scaleText;
	private FilledRect hatTop;

	// A grid texture for the boring white window.
	private void drawBackground() {
		Color gray = new Color(240, 240, 240);
		for (int i = 0; i <= 400; i++) {
			Line background = new Line(0, i * 5, 2000, i * 5, canvas);
			background.setColor(gray);
			background.sendToBack();
			background = new Line(i * 5, 0, i * 5, 2000, canvas);
			background.setColor(gray);
			background.sendToBack();
		}
	}

	// setScale(scale), scales all objects except for text
	private void setScale(double scale) {
		// Force a minimum and maximum scale, we definitely don't want a negative scale.
		// Please don't hurt Top Hat Man.
		scale = scale < MIN_SCALE ? MIN_SCALE : scale > MAX_SCALE ? MAX_SCALE : scale;

		// Here we go again...
		// Scale up head...
		double headRadius = HEAD_RADIUS * scale;
		double headX = HEAD_X - headRadius + HEAD_RADIUS;
		double headY = HEAD_Y;

		head.setSize(headRadius * 2, headRadius * 2);
		head.moveTo(headX, headY);

		// ... Top hat...
		double brimX1 = headX;
		double brimY1 = headY + 0.5 * scale;
		double brimX2 = headX + headRadius * 2;
		double brimY2 = headY + 0.5 * scale;
		double hatWidth = (brimX2 - brimX1) / 1.5;
		double hatHeight = Math.ceil(HAT_HEIGHT * scale);
		double hatX = (brimX2 - brimX1) / 6.5 + brimX1;
		double hatY = brimY1 - hatHeight;

		hatBrim.setStart(brimX1, brimY1);
		hatBrim.setEnd(brimX2, brimY2);
		hatTop.setSize(hatWidth, hatHeight);
		hatTop.moveTo(hatX, hatY);

		// ... Torso ...
		double torsoLength = TORSO_LENGTH * scale;
		double torsoX = headX + headRadius;
		double torsoY = HEAD_Y + headRadius * 2;
		double torsoEndX = torsoX;
		double torsoEndY = torsoY + torsoLength;

		torso.setStart(torsoX, torsoY);
		torso.setEnd(torsoX, torsoY + torsoLength);

		// ... and limbs.
		double pitX = torsoX;
		double pitY = torsoY + torsoLength / 2;
		double armLeftX = pitX - LIMB_SIZE * scale;
		double armLeftY = pitY - LIMB_SIZE * scale;
		double armRightX = pitX + LIMB_SIZE * scale;
		double armRightY = pitY - LIMB_SIZE * scale;
		double legLeftX = torsoEndX - LIMB_SIZE * scale;
		double legLeftY = torsoEndY + LIMB_SIZE * scale;
		double legRightX = torsoEndX + LIMB_SIZE * scale;
		double legRightY = torsoEndY + LIMB_SIZE * scale;

		armLeft.setStart(pitX, pitY);
		armLeft.setEnd(armLeftX, armLeftY);
		armRight.setStart(pitX, pitY);
		armRight.setEnd(armRightX, armRightY);
		legLeft.setStart(torsoEndX, torsoEndY);
		legLeft.setEnd(legLeftX, legLeftY);
		legRight.setStart(torsoEndX, torsoEndY);
		legRight.setEnd(legRightX, legRightY);

		// "Scale x%" text
		scaleText.setText(String.format("Scale: %.0f%%", scale * 100));
		scaleText.setColor(scale == MIN_SCALE || scale == MAX_SCALE ? Color.red : Color.black);
	}

	// Open window
	public GrowMan() {
		startController(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	public void begin() {
		drawBackground();

		// Create all the objects n' stuff
		head = new FramedOval(HEAD_X, HEAD_Y, HEAD_RADIUS * 2, HEAD_RADIUS * 2, canvas);
		hatBrim = new Line(BRIM_X1, BRIM_Y1, BRIM_X2, BRIM_Y2, canvas);
		hatTop = new FilledRect(HAT_X, HAT_Y, HAT_WIDTH, HAT_HEIGHT, canvas);
		torso = new Line(TORSO_X, TORSO_Y, TORSO_END_X, TORSO_END_Y, canvas);
		armLeft = new Line(PIT_X, PIT_Y, ARM_LX, ARM_LY, canvas);
		armRight = new Line(PIT_X, PIT_Y, ARM_RX, ARM_RY, canvas);
		legLeft = new Line(TORSO_END_X, TORSO_END_Y, LEG_LX, LEG_LY, canvas);
		legRight = new Line(TORSO_END_X, TORSO_END_Y, LEG_RX, LEG_RY, canvas);
		scaleText = new Text("Scale: 100%", 5, 5, canvas);
	}

	// Events
	public void onMouseDrag(Location point) {
		setScale((point.getY() - HEAD_Y + 10) * SCALE_MODIFIER);
	}

	public void onMousePress(Location point) {
		onMouseDrag(point);
	}

	public static void main(String[] args) {
		(new GrowMan()).setVisible(true);
	}
}

