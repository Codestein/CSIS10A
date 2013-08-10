/* Jon Crenshaw
 * CSIS 10a - Lab 8
 * Class: Boxball
 * Requires: Box, Ball
 */

import java.awt.*;

import objectdraw.*;

public class Boxball extends WindowController {
	// Object dimensions
	private static final int SCR_W = 400, SCR_H = 500;
	// playing field
	private static final int AREA_X = 50, AREA_Y = 30,
			AREA_W = 300, AREA_H = 300,
			LIMITS_H = 90;
	// text pos
	private static final int TITLE_CENTER_X = AREA_W / 2 + AREA_X,
			TITLE_Y = 5,
			STATUS_CENTER_X = TITLE_CENTER_X,
			STATUS_Y = AREA_Y + AREA_H + 5;
	// font sizes
	private static final int TITLE_FONT_SIZE = 16, STATUS_FONT_SIZE = 14,
			BUTTON_FONT_SIZE = 12;
	// buttons
	private static final int BUTTON_W = 75, BUTTON_H = 40,
			EASY_X = AREA_X + 15, EASY_Y = STATUS_Y + 25,
			NORMAL_Y = EASY_Y, HARD_Y = EASY_Y,
			NORMAL_X = AREA_X + AREA_W / 2 - BUTTON_W / 2,
			HARD_X = AREA_X + AREA_W - BUTTON_W - 15;
	// box and ball objects
	private static final int BOX_W = 75, BOX_H = 15,
			BALL_DIM = 20;

	// Difficulty object modifiers
	private static final double LIMITS_MOD = 0.9;

	// Object colors
	private static final Color BACKGROUND_COLOR = new Color(50, 50, 50),
			LIMITS_COLOR = new Color(175, 175, 175),
			BALL_COLOR = new Color(75, 75, 255),
			BOX_COLOR = new Color(50, 50, 50),
			TITLE_COLOR = Color.white,
			STATUS_COLOR = Color.white,
			BORDER_COLOR = Color.white,
			SEC_BORDER_COLOR = Color.black;

	private static final Color[] DIF_COLORS = { Color.green, Color.yellow, Color.red };

	// String constants
	private static final String TITLE = "Boxball",
			EASY = "Easy",
			NORMAL = "Normal",
			HARD = "Hard",
			LETS_PLAY = "Let's play!";

	// Game begins in normal mode. 0=Easy, 1=Normal, 2=Hard.
	private int dif = 1;
	private boolean buttonPress;

	// Declare objects
	private FilledRect field, easy, normal, hard, limits;
	private FramedRect limitsBorder;
	private Text easyText, normalText, hardText;
	private Ball ball;
	private Box box;

	public Boxball() {
		startController(SCR_W, SCR_H);
	}

	public void begin() {
		(new FilledRect(0, 0, 1000, 1000, canvas)).setColor(BACKGROUND_COLOR);

		// Create field
		field = new FilledRect(AREA_X, AREA_Y, AREA_W, AREA_H, canvas);
		field.setColor(DIF_COLORS[dif]);

		// Field off-limits
		double limitsHeight = LIMITS_H * LIMITS_MOD * (dif + 1);
		double limitsY = AREA_Y + AREA_H - limitsHeight;
		limits = new FilledRect(AREA_X, limitsY, AREA_W, limitsHeight, canvas);
		limits.setColor(LIMITS_COLOR);
		limitsBorder = new FramedRect(AREA_X, limitsY, AREA_W, limitsHeight, canvas);
		limitsBorder.setColor(SEC_BORDER_COLOR);

		// Field borders
		(new FramedRect(AREA_X, AREA_Y, AREA_W, AREA_H, canvas)).setColor(BORDER_COLOR);
		(new FramedRect(AREA_X + 1, AREA_Y + 1, AREA_W - 2, AREA_H - 2, canvas)).setColor(SEC_BORDER_COLOR);

		// Title
		Text titleText = new Text(TITLE, 0, 0, canvas);
		titleText.setColor(TITLE_COLOR);
		titleText.setBold(true);
		titleText.setFontSize(TITLE_FONT_SIZE);
		titleText.moveTo(TITLE_CENTER_X - titleText.getWidth() / 2, TITLE_Y);

		// Status
		Text statusText = new Text(LETS_PLAY, 0, 0, canvas);
		statusText.setColor(STATUS_COLOR);
		statusText.setFontSize(STATUS_FONT_SIZE);
		statusText.moveTo(STATUS_CENTER_X - statusText.getWidth() / 2, STATUS_Y);

		// Easy mode button
		easy = new FilledRect(EASY_X, EASY_Y, BUTTON_W, BUTTON_H, canvas);
		easy.setColor(DIF_COLORS[0]);
		easyText = new Text(EASY, 0, 0, canvas);
		easyText.setFontSize(BUTTON_FONT_SIZE);
		easyText.setBold(dif == 0);
		easyText.moveTo(EASY_X + BUTTON_W / 2 - easyText.getWidth() / 2, EASY_Y + BUTTON_H / 2 - easyText.getHeight() / 2);
		(new FramedRect(EASY_X, EASY_Y, BUTTON_W, BUTTON_H, canvas)).setColor(BORDER_COLOR);
		(new FramedRect(EASY_X + 1, EASY_Y + 1, BUTTON_W - 2, BUTTON_H - 2, canvas)).setColor(SEC_BORDER_COLOR);

		// Normal mode button
		normal = new FilledRect(NORMAL_X, NORMAL_Y, BUTTON_W, BUTTON_H, canvas);
		normal.setColor(DIF_COLORS[1]);
		normalText = new Text(NORMAL, 0, 0, canvas);
		normalText.setFontSize(BUTTON_FONT_SIZE);
		normalText.setBold(dif == 1);
		normalText.moveTo(NORMAL_X + BUTTON_W / 2 - normalText.getWidth() / 2, NORMAL_Y + BUTTON_H / 2 - normalText.getHeight() / 2);
		(new FramedRect(NORMAL_X, NORMAL_Y, BUTTON_W, BUTTON_H, canvas)).setColor(BORDER_COLOR);
		(new FramedRect(NORMAL_X + 1, NORMAL_Y + 1, BUTTON_W - 2, BUTTON_H - 2, canvas)).setColor(SEC_BORDER_COLOR);

		// Hard mode button
		hard = new FilledRect(HARD_X, HARD_Y, BUTTON_W, BUTTON_H, canvas);
		hard.setColor(DIF_COLORS[2]);
		hardText = new Text(HARD, 0, 0, canvas);
		hardText.setFontSize(BUTTON_FONT_SIZE);
		hardText.setBold(dif == 2);
		hardText.moveTo(HARD_X + BUTTON_W / 2 - hardText.getWidth() / 2, HARD_Y + BUTTON_H / 2 - hardText.getHeight() / 2);
		(new FramedRect(HARD_X, HARD_Y, BUTTON_W, BUTTON_H, canvas)).setColor(BORDER_COLOR);
		(new FramedRect(HARD_X + 1, HARD_Y + 1, BUTTON_W - 2, BUTTON_H - 2, canvas)).setColor(SEC_BORDER_COLOR);

		// Basket and ball, only need to create them once.
		box = new Box(BOX_W, BOX_H, BOX_COLOR, dif, field, canvas);
		ball = new Ball(BALL_DIM, BALL_COLOR, AREA_Y + AREA_H - BALL_DIM - 1, statusText, box, canvas);
	}

	public void onMousePress(Location mouse) {
		buttonPress = easy.contains(mouse) || normal.contains(mouse) || hard.contains(mouse);
	}

	public void onMouseRelease(Location mouse) {
		// Toggle dif setting.
		if (buttonPress) {
			boolean e = easy.contains(mouse), n = normal.contains(mouse), h = hard.contains(mouse);
			dif = e ? 0 : n ? 1 : h ? 2 : dif;
			easyText.setBold(e);
			normalText.setBold(n);
			hardText.setBold(h);
			changeDifficulty();
		}
		// Drop the ball
		else if (field.contains(mouse) && !limits.contains(mouse)) {
			if (ball.inPlay)
				return;

			// restrict ball to inside the right wall
			if (mouse.getX() > AREA_X + AREA_W - BALL_DIM)
				mouse = new Location(AREA_X + AREA_W - BALL_DIM, mouse.getY());

			ball.drop(mouse);
		}
	}

	// Modify the field for a new dif
	private void changeDifficulty() {
		double limitsHeight = LIMITS_H * LIMITS_MOD * (dif + 1);
		double limitsY = AREA_Y + AREA_H - limitsHeight;
		limits.setSize(AREA_W, limitsHeight);
		limits.moveTo(AREA_X, limitsY);
		limitsBorder.setSize(AREA_W, limitsHeight);
		limitsBorder.moveTo(AREA_X, limitsY);

		field.setColor(DIF_COLORS[dif]);
		box.setDifficulty(dif);
		box.running = true; // Resume the box movement.

		ball.inPlay = false;
		ball.ball.hide();
	}

	// launch as application
	public static void main(String[] args) {
		(new Boxball()).setVisible(true);
	}
}
