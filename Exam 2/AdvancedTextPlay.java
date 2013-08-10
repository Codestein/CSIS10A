import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//   NAME:   Jon Crenshaw

public class AdvancedTextPlay extends WindowController implements ActionListener, ChangeListener {
	private static final int MIN_FONT_SIZE = 10;
	private static final int MAX_FONT_SIZE = 48;

	private Text selectedText;
	private JTextField textField; 
	private JButton eraseButton, textAdd;
	private JComboBox combo;
	private JSlider slider;
	private JLabel label;
	private int font_size=11;

	// define array variable here to hold Text objects 
	// define variable for number of Text object in the array
	private static final int MTEXTS = 100;
	private Text texts[] = new Text[MTEXTS];

	private int currentLength = 0;

	private Boolean dragging = false;

	public AdvancedTextPlay() {
		startController( 600, 600 );
	}
   
	public void begin() {
		Container contentPane = getContentPane();
		JPanel panel = new JPanel();
		panel.setLayout( new GridLayout(4,1));
		contentPane.add( panel, BorderLayout.SOUTH);
		textField = new JTextField();
		textField.setText("Hello World!");
		textField.addActionListener(this);
		panel.add(textField);

		JPanel subpanel = new JPanel();
		textAdd = new JButton("Change Text");
		textAdd.addActionListener(this);
		subpanel.add(textAdd);
		eraseButton = new JButton("Remove text");
		eraseButton.addActionListener(this);
		subpanel.add(eraseButton);
		combo = new JComboBox();
		// add values to JComboBox
		combo.addItem("Arial");
		combo.addItem("Courier");
		combo.addItem("Times New Roman");
		combo.setSelectedItem("Arial");
		combo.addActionListener(this);
		subpanel.add(combo);
		panel.add(subpanel);
		slider = new JSlider(JSlider.HORIZONTAL, MIN_FONT_SIZE, MAX_FONT_SIZE, font_size);
		slider.addChangeListener(this);
		panel.add(slider);
		label = new JLabel();
		label.setText("Current Font Size: "+font_size);
		panel.add(label);
		contentPane.validate();
	}
  
	public void printArray() {
		System.out.println("\f");
		for (int i=0; i<currentLength; i++) {
			if (texts[i] != null)
				System.out.println("texts["+i+"] = "+texts[i].getText());
		}
	}
  
	// combo box and button press listener methods
	public void actionPerformed( ActionEvent e){
		if (selectedText!=null){
			if (e.getSource()==eraseButton) {
				// remove SELECTED text from canvas
				// remove text object from array
				
				Boolean found = false;
				for (int i=0; i<currentLength; i++) {
					if (texts[i] == selectedText) {
						selectedText.removeFromCanvas();
						found = true;
					}
					if (found && i+1<MTEXTS) {
						texts[i] = texts[i+1];
						texts[i+1] = null;
					}
				}
				if (found) currentLength--;
				
				printArray();
			}
			else if (e.getSource()==combo){
				// change font of SELECTED text
				selectedText.setFont( (String)combo.getSelectedItem());
			}
			else if (e.getSource()==textAdd || e.getSource()==textField) {
				selectedText.setText(textField.getText());
				printArray();
			}
		}
	}
	
	// slider change listener methods
	public void stateChanged( ChangeEvent e){
		// get value from slider and update message in label
		font_size = slider.getValue();
		label.setText("Current Font Size: "+font_size);
		if (selectedText!=null) {
			// change font size of lastText
			selectedText.setFontSize( font_size);
		}
	}
	
	public void onMousePress( Location point) {
		// does the mouse location point to a text object in the array
		//  if yes, change its color to RED.
		dragging = true;
		Boolean selected = false;
		for (int i=0; i<currentLength; i++) {
			if (texts[i].contains(point)) {
				texts[i].setColor(Color.red);
				selectedText = texts[i];
				
				Font f = selectedText.getFont();
				String fontName = f.getFontName();
				combo.setSelectedItem(fontName.equals("Monospaced.plain") ? "Courier" : fontName);
				
				slider.setValue(f.getSize());
				
				selected = true;
			}
			else {
				texts[i].setColor(Color.black);
			}
		}
		if (selected) return;
		
		// otherwise, create a new Text Object with text string
		// from JTextField at cursor position using font and font_size
		if (currentLength < MTEXTS) {
			selectedText = new Text(textField.getText(), point, canvas);
			selectedText.setFontSize( font_size);
			selectedText.setFont( (String)combo.getSelectedItem());
			selectedText.setColor(Color.red);
		
			// add new Text object to the array of Text Objects
			texts[currentLength++] = selectedText;
			printArray();
		}
		else {
			System.out.println("MAX OBJECTS ALLOWED REACHED");
		}
	}
	
	
	public void onMouseRelease(Location point) {
		dragging = false;
	}
	
	public void onMouseDrag(Location point) {
		if (dragging)
			selectedText.moveTo(point);
	}
}
