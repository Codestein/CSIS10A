/* Jon Crenshaw
 * CSIS 10a - 11/5/10
 * Class: Concentration
 */

import objectdraw.*;
import java.awt.*;
import javax.swing.*;

public class Concentration extends WindowController {
	private static final int NColor = 64;
	private static final int TILE_SIZE = 60;
	private Image imgGoat;
	private VisibleImage visibleGoat;
	private JLabel lblScore;
	private int score = 0, gray;
	private FilledRect[] tile = new FilledRect[64];
	private FramedRect[] edge = new FramedRect[64];
	private RandomIntGenerator randColor = new RandomIntGenerator(0,NColor-1);
	private RandomIntGenerator randCell = new RandomIntGenerator(0,63);
	
	private Boolean[] taken = new Boolean[64];
	
	int firstPick = -1, secondPick;
	public Concentration() {
		startController(496,556);  
	}
	
	public void begin() {
		// Setup GUI
		imgGoat = getImage("mountainGoat.jpg");
		visibleGoat = new VisibleImage(imgGoat, new Location(0,0), canvas);
		lblScore = new JLabel("Score: ");
		Container contentPane = getContentPane();
		contentPane.add(lblScore, BorderLayout.SOUTH);
		contentPane.validate();
		
		// Setup tile objects
		int k = 0;
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				tile[k] = new FilledRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE, canvas);
				edge[k] = new FramedRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE, canvas);
				
				int r = randColor.nextValue()*256/NColor;
				int g = randColor.nextValue()*256/NColor;
				int b = randColor.nextValue()*256/NColor;
				tile[k].setColor(new Color(r,g,b));
				k++;
			}
		}
		
		// Run through tiles and ensure that they're paired
		for (int i=0; i<taken.length; i++)
			taken[i] = false;
		
		for (int i=0; i<32; i++) {
			// First pick
			do firstPick = randCell.nextValue();
			while (taken[firstPick]);
			taken[firstPick] = true;
			
			// Second pick
			do secondPick = randCell.nextValue();
			while (taken[secondPick] || firstPick == secondPick);
			taken[secondPick] = true;
			
			// Set the color
			tile[secondPick].setColor(tile[firstPick].getColor());
		}
		
		firstPick = -1;
	}

	public void onMousePress(Location point) {
		if (firstPick == -1) {
			for (int k=0; k<tile.length; k++) {
				if (tile[k].contains(point) && tile[k].getCanvas() == canvas) {
					edge[k].setColor(Color.red);
					edge[k].sendToFront();
					firstPick = k;
				}
			}
		}
		else {
			for (int k=0; k<tile.length; k++) {
				if (tile[k].contains(point) && tile[k].getCanvas() == canvas) {
					secondPick = k;
					
					if (firstPick == secondPick) {
						edge[k].setColor(Color.black);
						firstPick = -1;
					}
					else {
						if (firstPick != -1 && tile[firstPick].getColor() == tile[secondPick].getColor()) {
							tile[firstPick].removeFromCanvas();
							tile[secondPick].removeFromCanvas();
							edge[firstPick].removeFromCanvas();
							edge[secondPick].removeFromCanvas();
							
							taken[firstPick] = false;
							taken[secondPick] = false;
						}
						else {
							edge[firstPick].setColor(Color.black);
						}
						firstPick = -1;
					}
				}
			}
		}
		
		score++;
		Boolean finished = true;
		for (int i=0; i<taken.length; i++)
			if (taken[i]) finished = false;
		
		lblScore.setText("Score: "+score+(finished?" -- GRATZ!":""));
	}
}


