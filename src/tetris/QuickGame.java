package tetris;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class QuickGame extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private static int interval; 
	private static Timer clock;
	
	/**
	 * QuickGame konstruktora
	 */
	QuickGame() {
		interval = 180; 
		Tetris.startinggame();
		int delay = 1000;
	    int period = 1000;
	    clock = new Timer();
	    clock.scheduleAtFixedRate(new TimerTask() {

	        public void run() {
	        	String seconds = String.valueOf(setInterval());
	            Tetris.setStatusBar(seconds);

	        }
	    }, delay, period);
	}
	
	/**
	 * Csokkenti az idot es ellenorzi, hogy lejart-e mar, ha igen kiirja hogy Game Over 
	 * @return a megmaradt masodpercek szamat adja vissza
	 */
	private static final int setInterval() {
	    if (interval == 1) {
	        Tetris.setStatusBar("Game Over.");
	        clock.cancel();
	    }
	    return --interval;
	    
	}
	
	

}
