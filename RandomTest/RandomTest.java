import java.util.*;

public class RandomTest {
	Random generator = new Random();
	
	public RandomTest() {
		System.out.println("\f");
		
		double num[] = {0,0,0,0,0};
		int numbers = 100000000;
		
		for (int i=0; i<num.length; i++) {
			for (int j=0; j<numbers; j++) {
				num[i] += generator.nextDouble() * 100;
			}
			System.out.println(num[i] / numbers);
		}
		
	}
}
