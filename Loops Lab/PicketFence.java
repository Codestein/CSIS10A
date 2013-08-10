/* Jon Crenshaw, CSIS10a
 * Lab 7: PicketFence
 */
import java.awt.*;
import objectdraw.*;

public class PicketFence extends WindowController {
  private static final int SCR_WIDTH = 525, SCR_HEIGHT = 200,
                           MIN_POSTS = 5,   MAX_POSTS = 20,
                           POST_WIDTH = 13, POST_HEIGHT = 75,
                           POST_MARGIN = 8, POST_X = 30, POST_Y = 20,
                           SUP_HEIGHT = 15, SUP_X = 12,  SUP_Y = 15,
                           NAIL_D = 3,      NAIL_X = 2,  NAIL_Y = 2;
  
  private static final Color POST_COLOR = new Color(139, 69, 19),
                             SUP_COLOR = new Color(160, 82, 45),
                             NAIL_COLOR = new Color(200, 200, 200);
													
  private FilledRect support;
  private int posts;
  
  public PicketFence() {
    startController(SCR_WIDTH, SCR_HEIGHT);
  }

  public void begin() {
    canvas.clear(); posts = 0;
    
    // Create support
    support = new FilledRect(POST_X-SUP_X, POST_Y+SUP_Y, 0, SUP_HEIGHT, canvas);
    support.setColor(SUP_COLOR);
    
    // Make default posts
    while (posts < MIN_POSTS) newPost();
  }
  
  private void newPost() {
    int i = posts++;
    
    // Enlengthen the support
    int supportWidth = SUP_X*2+(i+1)*(POST_WIDTH+POST_MARGIN)-POST_MARGIN;
    support.setSize(supportWidth, SUP_HEIGHT);
    
    // Create new post
    int postX = POST_X+i*(POST_WIDTH+POST_MARGIN);
    (new FilledRect(postX, POST_Y, POST_WIDTH, POST_HEIGHT, canvas)).setColor(POST_COLOR);
    
    // Nail the post to the support {x,y}
    Location[] nails = {
      new Location(postX+NAIL_X, POST_Y+SUP_Y+NAIL_Y),
      new Location(postX+POST_WIDTH-NAIL_X-NAIL_D-1, POST_Y+SUP_Y+SUP_HEIGHT-NAIL_Y-NAIL_D-1)
    };
    for (Location nail : nails)
      (new FilledOval(nail, NAIL_D, NAIL_D, canvas)).setColor(NAIL_COLOR);
  }
  
  public void onMouseRelease(Location pt) {
    // new post onclick, reset if posts reach max.
    if (posts < MAX_POSTS) newPost(); else begin();
  }
}