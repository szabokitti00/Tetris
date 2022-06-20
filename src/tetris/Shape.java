package tetris;

import java.util.Random;

public class Shape {

		protected enum Tetrominoe { 
			EmptyPiece, SPiece, ZPiece, TPiece, LPiece, JPiece, IPiece, OPiece 
		}
		
		private Tetrominoe piece;
		private int[][] coordinates;
		
		/**
		 * Shape konstruktora
		 * Beallitjuk az ures elemre az aktualis alakzatunkat es letrehozzuk a koordinatait
		 */
		public Shape() {
			coordinates = new int[4][2];
			setTetrominoes(Tetrominoe.EmptyPiece);
		}
		
		/**
		 * Setter
		 * @param shape az alakzatok (het darab plusz az ures) neveit tarolo enumeracioja
		 * Beallitjuk az alapkoordinatakat minden alakzatra
		 */
		void setTetrominoes(Tetrominoe shape) {
			int [][][] coordinatesTable = new int[][][] {
				{{0,0}, {0,0}, {0,0}, {0,0}}, //EmptyPiece
				{{0,-1}, {0,0}, {-1,0}, {-1,1}}, //SPiece
				{{0,-1}, {0,0}, {1,0}, {1,1}}, //ZPiece
				{{0,-1}, {0,0}, {-1,0}, {1,0}}, //TPiece
				{{1,-1}, {0,-1}, {0,0},{0,1}}, //LPiece
				{{-1,-1}, {0,-1}, {0,0}, {0,1}}, //JPiece
				{{0,-1}, {0,0}, {0,1}, {0,2}}, //IPiece
				{{1,0}, {0,0}, {0,1}, {1,1}}, //OPiece
			};
			
			for (int i = 0; i<4; i++ ) {
				System.arraycopy(coordinatesTable[shape.ordinal()], 0, coordinates, 0, 4);
			}
			
			piece = shape;
		}
		
		/**
		 * Setter
		 * @param index Megadja, hogy hanyadik pont X koordinatajat kell beallitani
		 * @param x Erre az x-ra allitja be az X koordinata erteket
		 */
		public void setX(int index, int x) {
			coordinates[index][0] = x;
		}
		
		/**
		 * Getter
		 * @return Visszaadja az X koordinatat
		 */
		public int getX(int index) {
			return coordinates[index][0];
		}
		
		/**
		 * Setter
		 * @param index Megadja, hogy hanyadik pont Y koordinatajat kell beallitani
		 * @param y Erre az y-ra allitja be az Y koordinata erteket
		 */
		public void setY(int index, int y) {
			coordinates[index][1] = y;
		}
		
		/**
		 * Getter
		 * @return Visszaadja az Y koordinatat
		 */
		public int getY(int index) {
			return coordinates[index][1];
		}
		
		/**
		 * Getter
		 * @return Visszaad egy alakzatot
		 */
		public Tetrominoe getPiece() {
			return piece;
		}
		
		/**
		 *Veletlenszeruen allitja be hogy melyik alakzat kovetkezzen a Random osztaly segisegevel
		 */
		public void setRandomPiece() {
			var r = new Random();
			int x = Math.abs(r.nextInt()) % 7 + 1;
			
			Tetrominoe[] values = Tetrominoe.values();
			setTetrominoes(values[x]);
		}
		
		/**
		 *@return Visszaadja az alakzat legkisebb X koordinatajat
		 */
		public int minX() {
	        int m = coordinates[0][0];
	        for (int i = 0; i < 4; i++) {
	            m = Math.min(m, coordinates[i][0]);
	        }
	        return m;
	    }

		/**
		 *@return Visszaadja az alakzat legkisebb Y koordinatajat
		 */
	    public int minY() {
	        int m = coordinates[0][1];
	        for (int i = 0; i < 4; i++) {
	            m = Math.min(m, coordinates[i][1]);
	        }
	        return m;
	    }
		
	    /**
		 * Balra forditja az alakzatot a koordinatai szerint
		 */
		public Shape RotateLeft() {
			if (piece == Tetrominoe.OPiece )
				return this;
			
			Shape rotatedLeft = new Shape();
			rotatedLeft.piece = piece;
			 for (int i = 0; i < 4; i++) {
				 rotatedLeft.setX(i, getY(i));
				 rotatedLeft.setY(i, -getX(i));
			 }
			 
			 return rotatedLeft;
		}
		
		/**
		 * Jobbra forditja az alakzatot a koordinatai szerint
		 */
		public Shape RotateRight() {
			if (piece == Tetrominoe.OPiece )
				return this;
			
			Shape rotatedRight = new Shape();
			rotatedRight.piece = piece;
			
			for (int i = 0; i < 4; i++) {
				 rotatedRight.setX(i, -getY(i));
				 rotatedRight.setY(i, getX(i));
			 }
			 
			 return rotatedRight;
		}
}
