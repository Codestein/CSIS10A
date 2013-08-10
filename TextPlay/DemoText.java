import objectdraw.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class DemoText extends WindowController implements ActionListener, ChangeListener {

	private JPanel southPanel;
	private JTextField text;
	private JButton clearButton;
	private JComboBox combo;
	private JSlider slider;
	private JLabel label;
	
	public DemoText() {
		startController(600, 600);
	}
	
	public void begin() {
		text = new JTextField("Enter text here");
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		
		combo = new JComboBox();
		combo.addActionListener(this);
		combo.addItem("");
		combo.addItem("batman");
		combo.addItem("superman");
		combo.addItem("aquaman");
		combo.addItem("wonder woman");
		
		slider = new JSlider(JSlider.HORIZONTAL, -5000, 5000, 0);
		slider.addChangeListener(this);
		
		label = new JLabel("Slider value "+slider.getValue());
		
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,2));
		southPanel.add(clearButton);
		southPanel.add(combo);
		southPanel.add(label);
		southPanel.add(slider);
		
		Container container = getContentPane();
		container.add(text, BorderLayout.NORTH);
		container.add(southPanel, BorderLayout.SOUTH);
		
		container.validate();
	}
	
	public void onMouseClick(Location mouse) {
		new Text(text.getText(), mouse, canvas);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearButton)
			canvas.clear();
		else if (e.getSource() == combo) {
			text.setText((String)combo.getSelectedItem());
			text.setEditable(true);
		}
	}
	
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == slider) {
			label.setText("Slider value "+(double)slider.getValue()/100);
		}
	}
}