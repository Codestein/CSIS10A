import objectdraw.*;
import java.awt.*;

/**
 * LoopDrills.java
 * 
 * Jon Crenshaw
 */

// This program is a place to experiment with data types and other topics
//  if you miss lecture, instructions can be found by watching the online video 

public class LoopDrills extends WindowController{
  
  // declare instance variables here
  private RandomIntGenerator rand = new RandomIntGenerator(1,6); // makes random nums
  private int dice1, dice2;
  
  private void log(String str) { System.out.println(str); }
  
  private int problem = 0;
  
  public LoopDrills() {
    startController(300, 300);
  }

  public void begin() {
    // set up variables for demonstrating type concepts
    log("\f");  // erase console output screen
  }
  
  public void onMouseClick(Location point){
    String str = "";
    int w,h,m,r,c;
    problem++;
    
    switch (problem) {
    
    /**************  Problem 1 Simple Loop ******/
    // write a while loop that displays "hello" on the console 10 times
    case 1:
      log("\n***Problem 1 ");
      str = "";
      int count = 0;
      while (count++ < 10) {
        str += "hello"+(count<10?", ":"");
      }
      log(str);
      break;
    
    //**************END Problem 1 *****************/   
    /******************Problem 2 Count Controlled Loop *******/
    case 2:
      log("\n***Problem 2 ");
      // write a loop that prints the number 0,1,2,3...10 on the console
      str = "";
      for (int i=0; i<=10; i++) str += i+(i<10?", ":"");
      log(str);
      break;
    
    //**************END Problem 2 *****************/
    /******************Problem 3 Count Controlled Loop *******/
    case 3:
      log("\n***Problem 3 ");
      // write a loop that prints the number 0,5,10,15,20,25,30 on the console
      str = "";
      for (int i=0; i<=6; i++) str += i*5+(i<6?", ":"");
      log(str);
      break;
    
    //**************END Problem 3 *****************/
    /******************Problem 4 Count Controlled Loop *******/
    case 4:
    log("\n***Problem 4 ");
      // write a loop that prints the number 10,9,8,7,...1 on the console
      str = "";
      for (int i=10; i>0; i--) str += i+(i>1?", ":"");
      log(str);
      break;
    
    //**************END Problem 4 *****************/
    /******************Problem 5 Task Controlled Loop *******/
    case 5:
      log("\n***Problem 5 ");
      // write a loop that shows a doubling of a number until the value exceeds 100
      //    (should show    1, 2, 4, 8, 16, 32, 64 )
      str = ""; double d = 0.5;
      while (true) {
        d*=2; if (d<=100) str += (int)d+", "; else break;
      }
      log(str.substring(0, str.length()-2));
      break;

    //**************END Problem 5 *****************/
    /******************Problem 6 Dice throwing *******/
    case 6:
      log("\n***Problem 6 ");
      // write a loop that shows a pair of random dice throws 5 times
      for (int i=0; i<5; i++) {
        dice1 = rand.nextValue();
        dice2 = rand.nextValue();
        log("dice1 = " + dice1 + " dice2 = " + dice2);
      }
      break;
    
    //**************END Problem 6 *****************/
    /******************Problem 7 Dice throwing until match *******/
    case 7:
      log("\n***Problem 7 ");
      // write a loop that keeps showing a pair of random dice throws 
      //   UNTIL the two dice values are the same  (while they are NOT the same)
      while(true) {
        dice1 = rand.nextValue();
        dice2 = rand.nextValue();
        log("dice1 = " + dice1 + " dice2 = " + dice2);
        if (dice1 == dice2) break;
      }
      break;
    
    //**************END Problem 7 *****************/        
    /******************Problem 8 Row of Bricks *******/
    case 8:
      log("\n***Problem 8 ");
      // write a loop that draws a row of 45x20 bricks across the bottom of the window
      //   separated by 5 pixels      (see handout for picture)
      canvas.clear();
      w = 45; h = 20; m = 5;
      for (int i=0; i<50; i++)
        new FilledRect(i*(w+m), canvas.getHeight()-h-m*2, w, h, canvas);
      log("done.");
      break;
    
    //**************END Problem 8 *****************/                          
    /******************Problem B8 Row of 4 Bricks *******/
    case 9:
			log("\n***Problem 8b ");
			// write a loop that draws a row of only 4 45x20 bricks across the bottom of the window
      //   separated by 5 pixels
      canvas.clear();
      w = 45; h = 20; m = 5;
      for (int i=0; i<4; i++)
        new FilledRect(i*(w+m), canvas.getHeight()-h-m*2, w, h, canvas);
      log("done.");
      break;
                
    //**************END Problem B8 *****************/   
    /******************Problem 9 Wall of Bricks *******/
    case 10:
      log("\n***Problem 9 ");
      // write a NESTED loop that draws a wall of 45x20 bricks across the whole window
      //   each row separeted by 5 pixels         (see handout for picture)
      canvas.clear();
      w = 45; h = 20; m = 5; r = 8; c = 10;
      for (int i=0; i<r; i++)
        for (int j=0; j<c; j++) {
          new FilledRect(j*(w+m), h+i*(h+m), w, h, canvas);
        }
      log("done.");
      break;

    //**************END Problem 9 *****************/ 
    /******************Problem 10 Diagonal Line of Circles *******/
    case 11:
      log("\n***Problem 10 ");
      // write a SINGLE loop (not nested) that draws a diagonal line of 10x10 circles 
      // from upper left to lower right part of screen  (see handout for picture)
      canvas.clear();
      for (int i=0; i<50; i++)
        new FramedOval(i*10, i*10, 10, 10, canvas);
      log("done.");
      break;
        
    //**************END Problem 10 *****************/    
    /******************Challenge (optional) Problem 11 Web of Lines *******/
    case 12:
      log("\n***Problem 11 ");
      // write a loop that draws a web of lines on the screen (see handout for picture)
      //   the lines all start at the left side of screen and end at the horizontal line
      //   at y=200.  the first line starts at (0,0)  and goes to (0,200)
      //    the next line starts at (0,10) and ends at (10,200)
      //    all other lines follow similar offsets

      //  This is NOT a nested loop. just a single loop
      canvas.clear();
      int start = 10, end = 210, spacing = 10;
      for (int i=0; i<=(end-start)/spacing; i++)
        new Line(start, start+i*spacing, start+i*spacing, end, canvas);
      log("done.");
      
      // start over...
      problem = 0;
      break;
    //**************END Problem 10 *****************/  
    }
  }
}



