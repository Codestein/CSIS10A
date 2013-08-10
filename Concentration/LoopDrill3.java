import objectdraw.*;
import java.awt.*;
/**
 * LoopDrill3.java
 * 
 * Practice using for loops and arrays
 * 
 * Your Name
 */
public class LoopDrill3 extends WindowController{  

// declare instance variables here

    private int clickNumber = 0;
// you need to define an array of int, array of String, and array of FramedRect here. 
    private int[] data = new int[1000];
    private int dataLength =0;


   
    public LoopDrill3() {
      startController(200,200);
    }

    public void begin() {
        System.out.println('\f');  // erase console output screen
    }
    
    /*
     * print the data array on the java console
     */
    public void print() {
        if (dataLength==0) {
            System.out.println("data is empty");
        } else {
            for (int i=0; i<dataLength; i++){
                System.out.print("data[" + i + "]=" + data[i] + "  ");
            }
            System.out.println();
        }
    }
    /*
     * find an element in the array.  return the element index
     * if not found, return -1
     */
    public int find(int a){
       for (int i=0; i<dataLength; i++){
            if (data[i]==a){
                return i;
            }
        }
        return -1;
    }
    /*
     * add an element in array storing it at the end of the array
     */
    public void addLast(int a){
        if (dataLength==1000){
            System.out.println("ARRAY IS FULL");
        } else {
            data[dataLength]=a;
            dataLength++;
        }
    }
    /*
     * expand the array creating an empty element at index 
     */
    public void expand(int index){
			for (int i=dataLength; i>index; i--)
				data[i] = data[i-1];
			data[index] = 0;
			dataLength++;
    }
    /* 
     * add element at index i
     */
    public void add(int value, int index){
			expand(index);
			data[index] = value;
    }
    /*
     * remove the element at index
     * shrink the array by 
     * moving elements [index+1 .. dataLength-1] to 
     * [index .. dataLength-2]
     */
    public void remove(int index){
			for (int i=index; i<dataLength; i++)
				data[i] = data[i+1];
			data[dataLength--] = 0;
    }
    /*
     * addSorted -- assuming the data array is in
     *  ascending order.  find the correct index to add
     *  a new element and expand the array to add the
     *  element at the correct index
     */
    public void addSorted(int value){
         for (int i=0; i<dataLength; i++){
            if (value <= data[i]){
                add(value,i);                
                return;
            }
        }
        addLast(value);
    }
        
    
    
    public void onMouseClick(Location point){

        clickNumber = clickNumber +1;
        switch (clickNumber) {
        /**************  Problem 1  ******
         * print out the empty array
         **************END Problem 1 *****************/ 
        case 1:
            System.out.println("Problem 1.  print empty array");
            print();
            break;
        /******************Problem 2 *******
         * add values  3, 5, 1 to the array using addLast
         **************END Problem 2 *****************/
        case 2:
            System.out.println("Problem 2.  add values");
            addLast(3);
            addLast(5);
            addLast(1);
            print();
            break;
        /******************Problem 3  *******
         * find element 5 in the array and print out its index
         * find element 7 which is not in array
         **************END Problem 3 *****************/
        case 3:
            System.out.println("Problem 3.  find 5, find 7");
            int index = find(5);
            System.out.println("element 5 is located at "+index);
            index = find(7);
            System.out.println("element 7 is located at "+index);
            break;
        /******************Problem 4 *******
         * remove element 5
         **************END Problem 4 *****************/
        case 4:
            System.out.println("Problem 4.  remove 5");
            index = find(5);
            remove(index);
            print();
            break;
        /******************Problem 5 *******
         *  add value 99 into array at index 2 
         **************END Problem 5 *****************/
        case 5:
            System.out.println("Problem 5.  add 99 into index 1");
            add(99, 1);
            print();
            break;
        /******************Problem 6  *******
         * reset the array to empty and create array with sorted values
         **************END Problem 6 *****************/
        case 6:
            System.out.println("Problem 6.  reset array");
            dataLength=0;
            data = new int[100];
            addLast(2);
						addLast(3);
            addLast(4);
            addLast(6);
            addLast(8);
            print();
            break;
        /******************Problem 7 *******
         * add 7 into array keeping the array in sorted sequence
         **************END Problem 7 *****************/  
        case 7:
            System.out.println("Problem 7.  add 7 ");
            addSorted(7);
            print();
            break;
            
        /******************Problem 8 *******
         * remove values 2, 3(does not exist), and 8 from array
         **************END Problem 8 *****************/    
        case 8:
            System.out.println("Problem 8.  remove 2, 3 and 6");
            index = find(2);
            if (index >= 0) remove(index);
            index = find(3);
            if (index >=0) remove(index);
            index = find(6);
            if (index >=0) remove(index);
            print();
            break;

        }// end of switch
    }

    

}

