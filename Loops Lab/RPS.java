/* Jon Crenshaw, CSIS10a
 * Lab 7: Rock, Paper, Scissors
 */
import java.awt.*;
import objectdraw.*;

public class RPS {
	private static final int ROUNDS = 5;
	private static final String[] RPS = {"scissors", "paper", "rock"};
	private static final String ROUND_FMT = "%s beats %s. player %s wins round!",
	                            TIE_FMT = "%s. round tied.",
	                            FINAL_FMT = "-------------------\nplayer %s wins %s-%s";

	private int[] score = {0,0}; // scoreboard
	private RandomIntGenerator rand = new RandomIntGenerator(0, 2);
	private void log(String str) { System.out.println(str); } // laziness
	
	public RPS() {
		log("\f");
		
		for (int i=0; i<ROUNDS; i++) {
			int p1 = rand.nextValue(), p2 = rand.nextValue();
			
			if (p1 == p2) { // tie
				i--; // redo this round
				log(String.format(TIE_FMT, RPS[p1]));
			}
			else { // a player won
				boolean p1win = p1==p2-1 || (p1==RPS.length-1 && p2==0);
				score[p1win?0:1]++; // if p1win is true, player 1 won
				log(String.format(ROUND_FMT, RPS[p1win?p1:p2], RPS[p1win?p2:p1], p1win?1:2));
			}
		}
		
		boolean p1win = score[0] > score[1];
		log(String.format(FINAL_FMT, p1win?1:2, score[p1win?0:1], score[p1win?1:0]));
	}
}