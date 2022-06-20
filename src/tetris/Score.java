package tetris;

import java.io.Serializable;
import java.util.Comparator;

public class Score implements Serializable {
	private static final long serialVersionUID = 1L;
	private String Date;
	private int Points;
	
	/** 
	 * Eredmenyek konstruktora
	 *@param date az adott datum amikor el lett erve a pontszam
	 *@param points a jatekos elert pontszama
	 */
	public Score(String date, int points) {
		this.Date = date;
		this.Points = points;
	}
	
	 /** 
	  * Setter
	  *@param date az adott datum 
	  */
	public void setName(String date) {
		Date = date;
	}
	
	/** 
	 * Setter
	 *@param points a beallitani kivant pontszam
	 */
	public void setPoints(int points) {
		Points = points;
	}
	
	/** 
	 * Getter
	 *@return Visszaadja a datumot
	 */
	public String getDate() {
		return Date;
	}
	
	/** 
	 * Getter
	 *@return Visszaadja az elert pontszamot 
	 */
	
	public int getPoints() {
		return Points;
	}
	
	/** 
	 * A comparator segitsegevel rendezzuk az eredmenyeket a pontok alapjan
	 *@return Vissza adja a ket Score kozul nagyobbat, amelyiknek nagyobb a pontszama
	 */
	public static Comparator<Score> getCompByPoints() {
		Comparator<Score> comp = new Comparator<Score>() {
			@Override
			public int compare(Score s1, Score s2) {
				return Integer.compare(s2.getPoints(), s1.getPoints());
			}
		};
		return comp;
	}
}
