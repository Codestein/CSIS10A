/* Jon Crenshaw
 * 10a - Scribble
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import objectdraw.*;

// A very simple drawing program.
public class Scribbler extends WindowController implements ActionListener {
	// user modes
	// using ints rather than boolean to allow for extension to
	// other modes
	private static final int DRAWING = 1;
	private static final int MOVING = 2;
	private static final int COLORING = 3;

	// the current scribble
	private Scribble currentScribble;
	private Scribble draggedScribble;

	// the collection of scribbles
	private ScribbleCollection scribbles;

	// stores last point for drawing or dragging
	private Location lastPoint;

	// whether the most recent scribble has been selected for moving
	private boolean draggingScribble;

	// buttons that allow user to select modes
	private JButton setDraw, setMove, setErase, setColor;

	// Choice JButton to select color
	private JComboBox chooseColor;

	// new color for scribble
	private Color newColor;

	// label indicating current mode
	private JLabel modeLabel;

	// the current mode -- drawing mode by default
	private int chosenAction = DRAWING;

	public Scribbler() {
		startController(700, 500);
	}

	// create and hook up the user interface components
	public void begin() {
		// background and lines
		new FilledRect(0, 0, 2000, 2000, canvas);
		for (int i = 0; i <= 400; i++) {
			(new Line(-1, i * 5 - 1, 2000, i * 5 - 1, canvas)).setColor(new Color(35, 35, 35));
			(new Line(i * 5 - 1, -1, i * 5 - 1, 2000, canvas)).setColor(new Color(35, 35, 35));
		}

		// i collect scribblz
		scribbles = new ScribbleCollection();

		setDraw = new JButton("Draw");
		setMove = new JButton("Move");
		setColor = new JButton("Color");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(setDraw);
		buttonPanel.add(setMove);
		buttonPanel.add(setColor);

		chooseColor = new JComboBox();
		chooseColor.addItem("red");
		chooseColor.addItem("blue");
		chooseColor.addItem("green");
		chooseColor.addItem("yellow");
		chooseColor.addItem("white");

		setErase = new JButton("Erase last");
		JPanel choicePanel = new JPanel();
		choicePanel.add(setErase);
		choicePanel.add(chooseColor);

		JPanel controlPanel = new JPanel(new GridLayout(3, 1));
		modeLabel = new JLabel("Current mode: drawing");
		controlPanel.add(modeLabel);
		controlPanel.add(buttonPanel);
		controlPanel.add(choicePanel);


		getContentPane().add(controlPanel, BorderLayout.SOUTH);

		// add listeners
		setDraw.addActionListener(this);
		setMove.addActionListener(this);
		setErase.addActionListener(this);
		setColor.addActionListener(this);
		chooseColor.addActionListener(this);

		// current scribble is empty
		currentScribble = null;

		validate();
	}


	// if in drawing mode then start with empty scribble
	// if in moving mode then prepare to move
	public void onMousePress(Location point) {
		if (chosenAction == DRAWING) {
			// start with an empty scribble for drawing
			currentScribble = new Scribble();
		}
		else if (chosenAction == MOVING) {
			// check if user clicked on current scribble
			if (scribbles.contains(point) != null) {
				draggingScribble = true;
				draggedScribble = scribbles.contains(point);
			}
		}
		else if (chosenAction == COLORING) {
			if (scribbles.contains(point) != null)
				scribbles.contains(point).setColor(newColor);
		}

		// remember point of press for drawing or moving
		lastPoint = point;
	}

	// if in drawing mode, add a new segment to scribble
	// if in moving mode then move it
	public void onMouseDrag(Location point) {
		if (chosenAction == DRAWING) {
			// add new line segment to current scribble
			Line newSegment = new Line(lastPoint, point, canvas);
			newSegment.setColor(getNewColor());

			currentScribble.addLine(newSegment);
		}
		else if (chosenAction == MOVING) {
			if (draggingScribble) { // move current scribble
				draggedScribble.move(point.getX() - lastPoint.getX(),
				                     point.getY() - lastPoint.getY());
			}
		}
		// update for next move or draw
		lastPoint = point;
	}

	public void onMouseRelease(Location point) {
		if (chosenAction == DRAWING)
			scribbles.addScribble(currentScribble);
		else if (chosenAction == MOVING)
			draggingScribble = false;
	}

	// Set mode according to JButton pressed.
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == setDraw) {
			chosenAction = DRAWING;
			modeLabel.setText("Current mode: drawing");
		}
		else if (e.getSource() == setMove) {
			chosenAction = MOVING;
			modeLabel.setText("Current mode: moving");
		}
		else if (e.getSource() == setColor || e.getSource() == chooseColor) {
			chosenAction = COLORING;
			modeLabel.setText("Current mode: coloring");
			newColor = getNewColor();
		}
		else if (e.getSource() == setErase) {
			scribbles.removeLast();
		}
	}

	private Color getNewColor() {
		String color = (String) chooseColor.getSelectedItem();
		return color.equals("red") ? Color.red :
		       color.equals("blue") ? Color.blue :
		       color.equals("green") ? Color.green :
		       color.equals("yellow") ? Color.yellow :
		       Color.white;
	}

	public static void main(String[] args) {
		(new Scribbler()).setVisible(true);
	}
}
