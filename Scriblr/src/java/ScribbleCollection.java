/* Jon Crenshaw
 * 10a - Scribble
 */

import objectdraw.Location;

// this class contains an array of Scribbles
public class ScribbleCollection {

	private int count = 0;
	private Scribble[] scribbles = new Scribble[1];

	// resizes scribble array... INFINITE SCRIBBLES (as memory permits...)
	public Scribble[] resize(Scribble[] array) {
		Scribble[] newArray = new Scribble[array.length * 2];
		System.arraycopy(array, 0, newArray, 0, array.length);

		System.out.println("\nScribble[] resized to " + newArray.length);
		return newArray;
	}

	/* add a scribble to the array
	 */
	public void addScribble(Scribble s) {
		if (count >= scribbles.length) {
			scribbles = resize(scribbles);
		}
		scribbles[count] = s;
		count++;
	}

	/* return the scribble that contains the input location
   * return null if no scribble contains the location
   */
	public Scribble contains(Location pt) {
		for (int i = 0; i < count; i++) {
			if (scribbles[i].contains(pt))
				return scribbles[i];
		}
		return null;
	}

	/* remove the last scribble from the canvas
	 * remove the scribble from the collection array
	 * if there are no scribbles, then do nothing.
	 */
	public void removeLast() {
		if (count > 0) {
			count--;
			scribbles[count].erase();
			scribbles[count] = null;
		}
	}
}
