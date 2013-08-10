/* Jon Crenshaw
 * CSIS 10a - Exam 1
 * Class: Calculator
 */

import objectdraw.*;
import java.awt.*;

public class Calculator {

	// define CONSTANTS here
	private static final int DISPLAY_HEIGHT = 20;
	// define instance variables here
	private FramedRect frame;
	private FilledRect back;
	private FramedRect r1, r2, r3, r4, r5, r6, r7, r8, r9, r0, plus, equal;  // calculator keys
	private Text k1, k2, k3, k4, k5, k6, k7, k8, k9, k0, kplus, kequal;
	private int num;                   // the current number being entered
	private int sum;                   // sum of numbers entered so far
	private String output="";          // string to show as calculator display
	private Text display;
	
	private boolean clearNext;
	private boolean error;
	
	public Calculator(double x, double y, double bsize, DrawingCanvas canvas){
		back = new FilledRect(x, y, bsize*3, bsize*4+DISPLAY_HEIGHT, canvas);
		frame = new FramedRect(x, y, bsize*3, bsize*4+DISPLAY_HEIGHT, canvas);
		back.setColor(new Color(200, 200, 200));
		display = new Text(output, x+5, y, canvas);
		y = y+DISPLAY_HEIGHT;
		double center = (bsize-12)/2;
		r1 = new FramedRect(x, y, bsize, bsize, canvas);
		k1 = new Text("1", x+center, y+center, canvas);
		r2 = new FramedRect(x+bsize, y, bsize, bsize, canvas);
		k2 = new Text("2", x+center+bsize, y+center, canvas);
		r3 = new FramedRect(x+2*bsize, y, bsize, bsize, canvas);
		k3 = new Text("3", x+center+2*bsize, y+center, canvas);
		r4 = new FramedRect(x, y+bsize, bsize, bsize, canvas);
		k4 = new Text("4", x+center, y+center+bsize, canvas);
		r5 = new FramedRect(x+bsize, y+bsize, bsize, bsize, canvas);
		k5 = new Text("5", x+center+bsize, y+center+bsize, canvas);
		r6 = new FramedRect(x+2*bsize, y+bsize, bsize, bsize, canvas);
		k6 = new Text("6", x+center+2*bsize, y+center+bsize, canvas);
		r7 = new FramedRect(x, y+2*bsize, bsize, bsize, canvas);
		k7 = new Text("7", x+center, y+center+2*bsize, canvas);
		r8 = new FramedRect(x+bsize, y+2*bsize, bsize, bsize, canvas);
		k8 = new Text("8", x+center+bsize, y+center+2*bsize, canvas);
		r9 = new FramedRect(x+2*bsize, y+2*bsize, bsize, bsize, canvas);
		k9 = new Text("9", x+center+2*bsize, y+center+2*bsize, canvas);
		r0 = new FramedRect(x, y+3*bsize, bsize, bsize, canvas);
		k0 = new Text("0", x+center, y+center+3*bsize, canvas);
		plus = new FramedRect(x+bsize, y+3*bsize, bsize, bsize, canvas);
		kplus = new Text("+",x+center+bsize, y+center+3*bsize,canvas);
		equal = new FramedRect(x+2*bsize, y+3*bsize, bsize, bsize, canvas);
		kequal = new Text("=", x+center+2*bsize, y+center+3*bsize, canvas);
		num=0;
		sum=0;
	}

	/*
	 * key method takes a mouse position as input and return the text to display for the calculator.
	 *     from the mouse position, this method determines which button (if any) has been pressed.
	 *     
	 *     if the button is a number button (0-9), then the current number is multiplied by 10 and the 
	 *                       number of the key is added to the current number.  The number key pressed 
	 *                       is added to the calculator display output.
	 *     if the button is +, then the current number is added to the sum and the current number
	 *                       is reset to zero.  The plus sign is added to the calculator display output.
	 *     if the button is =, then the current number is added to the sum and the current number
	 *                       is reset to zero.  The equal sign AND the value of sum is added to the 
	 *                       display output.
	 */
	public void key(Location point){
		
		if (clearNext) {
			clear();
			clearNext = false;
		}
		if (r1.contains(point)){
			num=num*10+1;
			output=output+"1";
		} else if (r2.contains(point)){
			num=num*10+2;
			output=output+"2";
		} else if (r3.contains(point)){
			num=num*10+3;
			output=output+"3";
		} else if (r4.contains(point)){
			num=num*10+4;
			output=output+"4";
		} else if (r5.contains(point)){
			num=num*10+5;
			output=output+"5";
		} else if (r6.contains(point)){
			num=num*10+6;
			output=output+"6";
		} else if (r7.contains(point)){
			num=num*10+7;
			output=output+"7";
		} else if (r8.contains(point)){
			num=num*10+8;
			output=output+"8";
		} else if (r9.contains(point)){
			num=num*10+9;
			output=output+"9";
		} else if (r0.contains(point)) {
			num=num*10+0;
			output=output+"0";
		} else if (plus.contains(point)){
			if (output == "") {
				output = "ERROR";
				display.setColor(Color.red);
				clearNext = true;
			}
			else {
				sum=sum+num;
				num=0;
				output=output+"+";
			}
		} else if (equal.contains(point)){
			if (output == "") {
				output = "ERROR";
				display.setColor(Color.red);
				clearNext = true;
			}
			else {
				sum=sum+num;
				output=output+"="+sum;
				num=0;
				sum=0;
				clearNext = true;
			}
		} 
		display.setText(output);
	}
    
	/*
	 * clear method will reset number and sum to zero and will reset display text to empty String (i.e. "")
	 */
	public void clear() {
		// clear the calculator by setting variables num, sum to zero 
		// set output to empty string and update the display text
		output = "";
		display.setText(output);
		display.setColor(Color.black);
	}
    
	public boolean contains(Location point) {
		// return true if the location point is inside the calculator frame
		// return false otherwise
		return frame.contains(point);
	}
    
	public void move(double dx, double dy) {
		// move the frame and all parts of the calculator
		frame.move(dx, dy);
		back.move(dx, dy);
		display.move(dx, dy);
		r1.move(dx, dy); k1.move(dx, dy);
		r2.move(dx, dy); k2.move(dx, dy);
		r3.move(dx, dy); k3.move(dx, dy);
		r4.move(dx, dy); k4.move(dx, dy);
		r5.move(dx, dy); k5.move(dx, dy);
		r6.move(dx, dy); k6.move(dx, dy);
		r7.move(dx, dy); k7.move(dx, dy);
		r8.move(dx, dy); k8.move(dx, dy);
		r9.move(dx, dy); k9.move(dx, dy);
		r0.move(dx, dy); k0.move(dx, dy);
		plus.move(dx, dy);
		kplus.move(dx, dy);
		equal.move(dx, dy);
		kequal.move(dx, dy);
	}
	
	public Location getLocation() {
		return frame.getLocation();
	}
}