/* Jon Crenshaw
 * CSIS 10A - Lab 11
 * October 23, 2010
 * Class: TextPlay
 */

import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TextPlay extends WindowController implements ActionListener, ChangeListener {
	private static final String DARK = "Dark", LIGHT = "Light",
	                            FSIZE_FORMAT = "Font size: %02dpt",
	                            RED_FORMAT = "Red: %s",
	                            GREEN_FORMAT = "Green: %s",
	                            BLUE_FORMAT = "Blue: %s",
	                            INIT_TEXT = "Lorem Ipsum",
	                            REMOVE_LAST = "Remove last text";
	
	private static final String[] fonts = { "Zapfino", "Courier", "Sand", "Times" };
	
	private JPanel panel, innerPanel1, innerPanel2, colorLabelPanel, colorPanel;
	private JTextField field;
	private JButton removeLast, darkButton;
	private JComboBox fontBox;
	private JSlider sizeSlider, redSlider, greenSlider, blueSlider;
	private JLabel sizeLabel, redLabel, greenLabel, blueLabel;
	
	private FilledRect background;
	private Text currentText;
	
	private boolean dark = true;
	
	public TextPlay() {
		startController(500, 500);
	}
	
	public void begin() {
		field = new JTextField(INIT_TEXT);
		
		removeLast = new JButton(REMOVE_LAST);
		removeLast.addActionListener(this);
		
		darkButton = new JButton(dark?LIGHT:DARK);
		darkButton.addActionListener(this);
		
		fontBox = new JComboBox();
		fontBox.addActionListener(this);
		for (String font : fonts) fontBox.addItem(font);
		
		sizeSlider = new JSlider(JSlider.HORIZONTAL, 4, 99, 24);
		sizeSlider.addChangeListener(this);
		
		sizeLabel = new JLabel(String.format(FSIZE_FORMAT, sizeSlider.getValue()));
		
		innerPanel1 = new JPanel();
		innerPanel1.add(removeLast);
		innerPanel1.add(darkButton);
		innerPanel1.add(fontBox);
		
		innerPanel2 = new JPanel();
		innerPanel2.add(sizeLabel);
		innerPanel2.add(sizeSlider);
		
		redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		redSlider.addChangeListener(this);
		redLabel = new JLabel(String.format(RED_FORMAT, redSlider.getValue()), SwingConstants.CENTER);
		redLabel.setForeground(Color.red);
		
		greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 20);
		greenSlider.addChangeListener(this);
		greenLabel = new JLabel(String.format(GREEN_FORMAT, greenSlider.getValue()), SwingConstants.CENTER);
		greenLabel.setForeground(new Color(0, 175, 0));
		
		blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 20);
		blueSlider.addChangeListener(this);
		blueLabel = new JLabel(String.format(BLUE_FORMAT, blueSlider.getValue()), SwingConstants.CENTER);
		blueLabel.setForeground(Color.blue);
		
		colorLabelPanel = new JPanel();
		colorLabelPanel.setLayout(new GridLayout(1, 3));
		colorLabelPanel.add(redLabel);
		colorLabelPanel.add(greenLabel);
		colorLabelPanel.add(blueLabel);
		
		colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(1, 3));
		colorPanel.add(redSlider);
		colorPanel.add(greenSlider);
		colorPanel.add(blueSlider);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		panel.add(field);
		panel.add(innerPanel1);
		panel.add(innerPanel2);
		panel.add(colorLabelPanel);
		panel.add(colorPanel);
		
		Container container = getContentPane();
		container.add(panel, BorderLayout.SOUTH);
		
		container.validate();
		
		background = new FilledRect(0, 0, 1000, 1000, canvas);
		background.setColor(dark?Color.black:Color.white);
	}
	
	public void updateFont(Text text) {
		if (text == null) return;
		text.setFont((String)fontBox.getSelectedItem());
		text.setFontSize(sizeSlider.getValue());
		text.setColor(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
	}
	
	// Swing event listeners
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == removeLast && currentText != null) {
			currentText.removeFromCanvas();
		}
		else if (e.getSource() == fontBox && currentText != null) {
			updateFont(currentText);
		}
		else if (e.getSource() == darkButton) {
			dark = !dark;
			background.setColor(dark?Color.black:Color.white);
			darkButton.setText(dark?LIGHT:DARK);
		}
	}
	
	public void stateChanged(ChangeEvent e) {
		sizeLabel.setText(String.format(FSIZE_FORMAT, sizeSlider.getValue()));
		redLabel.setText(String.format(RED_FORMAT, redSlider.getValue()));
		greenLabel.setText(String.format(GREEN_FORMAT, greenSlider.getValue()));
		blueLabel.setText(String.format(BLUE_FORMAT, blueSlider.getValue()));
		updateFont(currentText);
	}
	
	// Canvas mouse events
	public void onMouseRelease(Location mouse) {
		currentText = new Text(field.getText(), mouse, canvas);
		updateFont(currentText);
	}
}