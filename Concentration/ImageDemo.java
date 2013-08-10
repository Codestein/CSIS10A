import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;

/*
 * this is example of java program.  Note that it is NOT an applet or an 
 * objectdraw window controller.  It directly uses java awt and swing
 * to create a window,  see how ImageDemo extends from Frame and the code 
 * in the main method cretes an instance of ImageDemo.
 * 
 * this is typical of how java programs are written when not using objectdraw.
 * 
 * this program loads a jpeg file and then creates 2D arrays of RGB values.
 * 
 * -- Using javac and java commands
 * 
 * to compile and run java program without the use of IDE (BlueJ or Eclipse), 
 * the JDK (java development tooklkit) contains several commands.
 * 
 * javac is the compiler. To compile ImageDemo.java, enter the command
 *    javac ImageDemo.java 
 * from a command window.  
 * To execute ImageDemo enter the command
 *    java ImageDemo 
 * To execute the program with command line arguments.  The arguments are passed 
 * to the "main" method of your program as an array of Strings.
 *    java ImageDemo mountainGoat.jpg
 *    
 * If you get the error "javac cmd not found", you need to add the JDK into the 
 * PATH used by the operating system with the command
 *   set PATH=c:\Program Files\Java\jdk1.6.0_20\bin;%PATH%
 * This assumes that java jdk was installed into folder jdk1.6.0_20.
 */
public class ImageDemo extends Frame implements ActionListener {

  private JButton loadButton;       // load, blur and reverse buttons
  private JButton blurButton;
  private JButton reverseButton;
  private JTextField fileName;      // file name of jpg file
  private JLabel message;           // status message
  
  private BufferedImage image;      // the image in memory
  
  private int[][] red;              // rgb pixel values stored a 2D array
  private int[][] green;
  private int[][] blue;
  
  public ImageDemo() {            // constructor to create the GUI panels and buttons
      super("ImageDemo");
      loadButton = new JButton("Load");
      loadButton.addActionListener(this);
      blurButton = new JButton("Blur");
      blurButton.addActionListener(this);
      reverseButton = new JButton("Reverse");
      reverseButton.addActionListener(this);
      fileName = new JTextField("Enter file name of jpeg file.");
      message = new JLabel("Enter file name and press LOAD");
      JPanel south = new JPanel();
      south.setLayout( new GridLayout(3,1));
      south.add(fileName);
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(loadButton);
      buttonPanel.add(blurButton);
      buttonPanel.add(reverseButton);
      south.add(buttonPanel);
      south.add(message);
      this.setLayout( new BorderLayout());
      this.add(south, BorderLayout.SOUTH);
      this.validate();
    }
    
  //ActionListener method for button press events
  public void actionPerformed(ActionEvent e){          
      if (e.getSource() == loadButton) {
          loadImage();
          repaint();               // after image is loaded or changed, must do force repaint 
      } else if (e.getSource() == blurButton){
          doBlur();
          repaint();
      } else if (e.getSource() == reverseButton){
          doReverse();
          repaint();
      }
    }
    
  /*  to perform 2D graphics operations without using objectdraw, 
   *  you must have a paint method that performs the graphics operations.
   */
  public void paint (Graphics g) {
      if (image!=null) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0 ,this);    // draw the image
      }
      super.paint(g);    // this is necessary so that the Frame superclass can draw the buttons, fields, etc..
  }
  
  public void loadImage() {
     String fname = fileName.getText();
     if (fname.trim().equals("")){
         message.setText("Enter file name of JPEG file.");
         return;
        }
  
     try {
          image = ImageIO.read(new File(fname));
          message.setText("Image loaded.");
     } catch (IOException e) {
          message.setText("Unable to load file. Exception=" + e.getMessage());
          return;
     }     
     
     red = new int[image.getWidth()][ image.getHeight()];     // create the 2D arrays
     green = new int[image.getWidth()][ image.getHeight()];
     blue = new int[image.getWidth()][ image.getHeight()];
     
     /* retrieve the value of each pixel in the image
      */
     for (int i=0; i<image.getWidth(); i++){
        for (int j=0; j<image.getHeight(); j++) {
            /* the RGB values of a pixel in the image are retrieved with the getRGB method.
             * the method returns an integer whose binary values are x00RRGGBB
             * where RR, GG, BB is a binary value from 0-255 for read, green, blue.
             */
           int pixel = image.getRGB(i,j);               // get the rgb value for pixel row i, column j.
           red [i][j]= 0xff & (pixel>>16);              // extract the red value from the RGB value
           green [i][j] = 0xff & (pixel>>8);            // likewise for green and blue
           blue [i][j] = 0xff & pixel;
        }
     }
  }
    
  /* for each pixel, take the average value of 
   * 8 neighboring pixels and the pixel itself.
   * except for pixels on the edge.
   */
  public void doBlur(){  
      for (int i=1; i<image.getWidth()-1; i++){
         for (int j=1; j<image.getHeight()-1; j++){
             int avgred = (red[i-1][j-1]+red[i-1][j]+red[i-1][j+1]+
                          red[i][j-1]+  red[i][j]  +red[i][j+1] +
                          red[i+1][j-1]+red[i+1][j]+red[i+1][j+1])/9;
             int avggreen = (green[i-1][j-1]+green[i-1][j]+green[i-1][j+1]+
                          green[i][j-1]+  green[i][j]  +green[i][j+1] +
                          green[i+1][j-1]+green[i+1][j]+green[i+1][j+1])/9;
             int avgblue = (blue[i-1][j-1]+blue[i-1][j]+blue[i-1][j+1]+
                          blue[i][j-1]+  blue[i][j]  +blue[i][j+1] +
                          blue[i+1][j-1]+blue[i+1][j]+blue[i+1][j+1])/9;
             int avg = (avgblue&0xff) | ((avggreen&0xff) <<8) | ((avgred&0xff) <<16);
             image.setRGB(i,j, avg);
         }
     }
  }
  
  public void doReverse(){    // exchange the [i][j] pixel for the [width-1-i][j] pixel.
     int width = image.getWidth();
     for (int i=0; i<width; i++) {
         for (int j=0; j<image.getHeight(); j++) {
             int pixel =  (blue[i][j] & 0xff) | 
                  ((green[i][j] & 0xff) <<8) | ((red[i][j] & 0xff) <<16);
             image.setRGB(width-1-i, j, pixel);
         }
     }
 }

  public void setFileName(String fname) {
     fileName.setText(fname);
  }

   public static void main(String s[]) {
        ImageDemo f = new ImageDemo();            // create the frame window
        if (s.length >= 1){
            f.setFileName( s[0]);                   // if s[0] exists, then it will be file name.
            f.loadImage();
            f.repaint();
        }
        f.addWindowListener(new WindowAdapter() {   // this handles the window close event
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        f.setSize(new Dimension(500, 500));         // initial window size width=500, height=500
        f.setVisible(true);                         // must set window visible
    }
}