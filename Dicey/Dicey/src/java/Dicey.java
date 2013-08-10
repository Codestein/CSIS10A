/* Jon Crenshaw, CSIS10a
 * Lab 4, Ex 4.9.2: "Dicey"
 */

import java.awt.*;

import objectdraw.*;

public class Dicey extends WindowController {
	private static final int SCR_WIDTH = 350;
	private static final int SCR_HEIGHT = 200;

	private static final int DICE_DIMS = 31;
	private static final int DICE_MARGIN = 15;
	private static final int DOT_DIA = 7;

	private static final int DICE_NUM = 3; // Can be changed, but scoring is only implemented for 3 die.
	private static final int ROLL_MIN = 1;
	private static final int ROLL_MAX = 6;

	private static final String CLICK_TO_ROLL = "Click to roll!";
	private static final String STATS_FMT = "No Kind: %.0f (%.1f%%) | Two Kind: %.0f (%.1f%%) | Three Kind: %.0f (%.1f%%)";
	private static final String THREE_KIND_FMT = "You rolled a three of a kind of %ss!";
	private static final String TWO_KIND_FMT = "You rolled a two of a kind of %ss!";

	private static final Color DICE_COLOR = Color.white;
	private static final Color MATCH_COLOR = Color.green;
	private static final Color STATS_COLOR = Color.gray;
	private static final Color FEEDB_COLOR = Color.red;

	private static final int[][] dotPoints = { { 4, 3 }, { 20, 3 }, { 4, 21 }, { 20, 21 }, { 12, 12 }, { 4, 12 }, { 20, 12 } };
	private static final int dotsShown[][] = { { 4 }, { 0, 3 }, { 0, 4, 3 }, { 0, 1, 2, 3 }, { 0, 1, 2, 3, 4 }, { 0, 1, 2, 3, 5, 6 } };

	private int[] rolls = new int[DICE_NUM];
	private float noKind = 0;
	private float twoKind = 0;
	private float threeKind = 0;
	private float totalRolls = 0;

	private Text feedback, stats;
	private FilledRect[] diceBg = new FilledRect[DICE_NUM];
	private FramedRect[] dice = new FramedRect[DICE_NUM];
	private FilledOval[][] dots = new FilledOval[DICE_NUM][7];
	private RandomIntGenerator random = new RandomIntGenerator(ROLL_MIN, ROLL_MAX);

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

	private void hideDots() {
		for (int i = 0; i < DICE_NUM; i++) {
			for (int j = 0; j < 7; j++)
				dots[i][j].hide();
		}
	}

	public Dicey() {
		startController(SCR_WIDTH, SCR_HEIGHT);
	}

	public void begin() {
		drawBackground();

		// create and position all die elements
		for (int i = 0; i < DICE_NUM; i++) {
			diceBg[i] = new FilledRect(DICE_MARGIN * (i + 1) + DICE_DIMS * i, 15, DICE_DIMS, DICE_DIMS, canvas);
			dice[i] = new FramedRect(diceBg[i].getLocation(), DICE_DIMS, DICE_DIMS, canvas);

			for (int j = 0; j < 7; j++) {
				dots[i][j] = new FilledOval(dice[i].getLocation(), DOT_DIA, DOT_DIA, canvas);
				dots[i][j].move(dotPoints[j][0], dotPoints[j][1]); // dot point offset
			}
		}

		feedback = new Text(CLICK_TO_ROLL, DICE_MARGIN, 15, canvas);
		stats = new Text(String.format(STATS_FMT, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), DICE_MARGIN, 15 + DICE_DIMS + 60, canvas);
		stats.setColor(STATS_COLOR);
		stats.setFontSize(10);

		// hide before the first roll
		for (int i = 0; i < DICE_NUM; i++) {
			diceBg[i].hide();
			dice[i].hide();
		}
		hideDots();
	}

	public void onMouseClick(Location point) {
		// get some new rolls
		totalRolls++;
		for (int i = 0; i < DICE_NUM; i++)
			rolls[i] = random.nextValue();

		// re-configure die
		if (totalRolls == 1) {
			for (int i = 0; i < DICE_NUM; i++) {
				diceBg[i].show();
				dice[i].show();
			}
			feedback.moveTo(DICE_MARGIN, DICE_MARGIN + DICE_DIMS + 10);
			feedback.setColor(FEEDB_COLOR);
		}

		hideDots();
		for (FilledRect bg : diceBg)
			bg.setColor(DICE_COLOR);

		// check two or three of a kind, color green
		if (rolls[0] == rolls[1] || rolls[0] == rolls[2]) {
			diceBg[0].setColor(MATCH_COLOR);
			if (rolls[0] == rolls[1])
				diceBg[1].setColor(MATCH_COLOR);
			if (rolls[0] == rolls[2])
				diceBg[2].setColor(MATCH_COLOR);
		}
		if (rolls[1] == rolls[2]) {
			diceBg[1].setColor(MATCH_COLOR);
			diceBg[2].setColor(MATCH_COLOR);
		}

		// give feedback for the roll
		if (rolls[0] == rolls[1] && rolls[0] == rolls[2]) {
			feedback.setText(String.format(THREE_KIND_FMT, rolls[0]));
			threeKind++;
		}
		else if (rolls[0] == rolls[1] || rolls[0] == rolls[2] || rolls[1] == rolls[2]) {
			feedback.setText(String.format(TWO_KIND_FMT, rolls[rolls[1] == rolls[2] ? 1 : 0]));
			twoKind++;
		}
		else {
			feedback.setText("");
			noKind++;
		}

		// show some roll stats
		stats.setText(String.format(STATS_FMT, noKind, noKind / totalRolls * 100,
		                            twoKind, twoKind / totalRolls * 100, threeKind, threeKind / totalRolls * 100));

		// arrange dots!
		for (int i = 0; i < DICE_NUM; i++) {
			for (int shown : dotsShown[rolls[i] - 1])
				dots[i][shown].show();
		}
	}

	public static void main(String[] args) {
		(new Dicey()).setVisible(true);
	}
}
