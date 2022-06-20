package tetris;
import tetris.Shape.Tetrominoe;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Board extends JPanel {
	 private static final long serialVersionUID = 1L;
	 private final int BOARD_WIDTH = 10;
	 private final int BOARD_HEIGHT = 20;
	 private final int SQUARE_SIZE = 25;
	 private final int WINDOW_WIDTH = 450;
	 private final int WINDOW_HEIGHT = 550;

	 public static Timer timer;
	 private boolean isFallingFinished = false;
	 private boolean gameover = false;
	 private int numLinesRemoved = 0;
	 private int currentX = 0;
	 private int currentY = 0;
	 private JLabel statusbar;
	 private Shape currentPiece;
	 private Tetrominoe[] board;
	 private Tetris par;
	 
	 /**
	  * A Borad konstruktora
	  * @param parent egy tetris objektum
	  */
	 public Board(Tetris parent) {
		 timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				oneLineDown();				
			}			 
		 });
		 timer.start();
		 par = parent;
		 initBoard(parent);
	 }

	 /**
	  * Inicializalja az jatek tablat
	  * @param parent egy tetris objektum
	  */
	 private void initBoard(Tetris parent) {
	        setFocusable(true);
	        statusbar = parent.getStatusBar();
	        addKeyListener((KeyListener) new MyKeyAdapter());
	 }

	 /**
	  * Meghatarozzuk az adott koordinatakon az alakzatot
	  * @param x koordinata
	  * @param y koordinata
	  * @return board tombben taroljuk
	  */
	 private Tetrominoe shapeAt(int x, int y) {
	        return board[(y * BOARD_WIDTH) + x];
	 }

	 /**
	  * Elinditja a jatekot
	  */
	 void start() {
	        currentPiece = new Shape();
	        board = new Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];
	        clearBoard();
	        newPiece();
	        timer = new Timer(1000, null);
	        timer.start();
	        
	 }

	 /**
	  * Megrajzolja a jatek tablat, kirajzolja a racsot, es elhelyezi a pontok kiirasat a tabla mellett
	  */
	 @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.setColor(Color.black);
		 g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		 g.setColor(Color.white);
		 
		 for (int row = 0; row < BOARD_HEIGHT; row++) {
		     g.drawLine (0, SQUARE_SIZE * row, SQUARE_SIZE * BOARD_WIDTH, SQUARE_SIZE * row) ; 
	 	}
	 
		 for(int col = 0; col < BOARD_WIDTH + 1; col++) {
		    g.drawLine (col * SQUARE_SIZE, 0, col * SQUARE_SIZE, SQUARE_SIZE* BOARD_HEIGHT);
		 }
		 
		 g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		 g.drawString("SCORE:", WINDOW_WIDTH - 190, WINDOW_HEIGHT - 400);
		 g.drawString(numLinesRemoved + "", WINDOW_WIDTH - 190, WINDOW_HEIGHT - 370);
		 doDrawing(g);
	 }

	 /**
	  * Kirajzolja a tablara az alakzatokat, majd ha vege jateknak, bezarja ajatek ablakat es fajlba ment
	  * @param g
	  */
	 private void doDrawing(Graphics g) {
		 var size = getSize();
		 int boardTop = (int) size.getHeight() - BOARD_HEIGHT * SQUARE_SIZE;
		 for (int i = 0; i < BOARD_HEIGHT; i++) {
			 for (int j = 0; j < BOARD_WIDTH; j++) {
				 Tetrominoe shape = shapeAt(j, BOARD_HEIGHT - i - 1);
	                if (shape != Tetrominoe.EmptyPiece) {
	                    drawSquare(g, j * SQUARE_SIZE,
	                    	boardTop + i * SQUARE_SIZE, shape);
	                }
			 }
		 }
		 if (currentPiece.getPiece() != Tetrominoe.EmptyPiece) {
			 for (int i = 0; i < 4; i++) {
				 int x = currentX + currentPiece.getX(i);
				 int y = currentY - currentPiece.getY(i);
				 drawSquare(g, x * SQUARE_SIZE,
					boardTop + (BOARD_HEIGHT - y - 1) * SQUARE_SIZE,
						currentPiece.getPiece());
	            }
	     }
		 if (gameover == true) {
		//	 System.out.println(MainMenu.maratonscores);
			 saveData("maraton.txt");
			//.out.println(MainMenu.maratonscores);
			 par.dispose();

		 
		 }
	 }
	 
	 /**
	  * A legalacsonyabb pozicioba helyezi az alakzatot a tablan, ahol meg nincs masik alakzat
	  */
	 private void dropDown() {
		 int newY = currentY;
		 while (newY > 0) {
			 if (!tryMove(currentPiece, currentX, newY - 1)) {
				 break;
			 }
			 newY--;
		 }
		 pieceDropped();
	 }
	 
	/**
	 * Egy sorral lejjebbi pozicioba helyezi az alakzatot, ahol meg nincs masik alakzat
	 */
	 private void oneLineDown() {
		 if (!tryMove(currentPiece, currentX, currentY - 1)) {
			 pieceDropped();
		 }
		 
	 }

	 /**
	  * Torli a tabla tartalmat, minden pontra bejarva
	  */
	 private void clearBoard() {
		 for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
			 board[i] = Tetrominoe.EmptyPiece;
		 }
	 }

	 /**
	  * Ellenorzi, hogy mikor leer egy alakzat lesz uj telitett sor, ha igen torli, es uj alakzatot ker
	  */
	 private void pieceDropped() {
		 for (int i = 0; i < 4; i++) {
			 int x = currentX + currentPiece.getX(i);
			 int y = currentY - currentPiece.getY(i);
			 board[(y * BOARD_WIDTH) + x] = currentPiece.getPiece();
		 }
		 removeFullLines();
		 if (!isFallingFinished) {
			 newPiece();
		 }
	 }

	 /**
	  * Letrehozza a veletlenszeru alakzatot es elhelyezi a tabla tetejen.
	  * Ellenorzi hogy betelt-e az uj alakzattol a tabla.
	  */
	 private void newPiece() {
		 currentPiece.setRandomPiece();
		 currentX = BOARD_WIDTH / 2 + 1;
		 currentY = BOARD_HEIGHT - 1 + currentPiece.minY();
		 if (!tryMove(currentPiece, currentX, currentY)) {
			 currentPiece.setTetrominoes(Tetrominoe.EmptyPiece);
			 timer.stop();
			 gameover = true;
			 var msg = String.format("Game over.");
			 statusbar.setText(msg);
		 }
	 }
	
	 /**
	  * Ellenorzi, hogy lehet-e mozgatni abba az iranyba az alakzatunkat, amerre szeretnenk mozditani, kiszamolva az uj koordinatakat.
	  * Ha lehetseges, akkor elmozditja az alakzatot. 
	  * @param newPiece az aktualis alakzatunk
	  * @param newX az uj X koordinata ahova szeretnenk mozgatni
	  * @param newY az uj Y koordinata ahova szeretnenk mozgatni
	  * @return Visszaadja hogy lehet-e mozgatni az aktualis alakzatott 
	  */
	 private boolean tryMove(Shape newPiece, int newX, int newY) {
		 for (int i = 0; i < 4; i++) {
			 int x = newX + newPiece.getX(i);
			 int y = newY - newPiece.getY(i);
			 if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
				 return false;
			 }
			 if (shapeAt(x, y) != Tetrominoe.EmptyPiece) {
				 return false;
			 }
		 }
		 currentPiece = newPiece;
		 currentX = newX;
		 currentY = newY;
		 repaint();
		 return true;
	 }

	 /**
	  * Vegigjarjuk a jatek tablat ellenorizve a sorokat, hogy teliettek-e, es ha igen toroljuk azokat.
	  * Torles kozben szamoljuk a torolt sorokat es ez fogja adni a pontszamot. 
	  */
	 private void removeFullLines() {
		 int numFullLines = 0;
		 for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
			 boolean lineIsFull = true;
			 for (int j = 0; j < BOARD_WIDTH; j++) {
				 if (shapeAt(j, i) == Tetrominoe.EmptyPiece) {
					 lineIsFull = false;
					 break;
				 }
			 }
			 if (lineIsFull) {
				 numFullLines++;
				 for (int k = i; k < BOARD_HEIGHT - 1; k++) {
					 for (int j = 0; j < BOARD_WIDTH; j++) {
						 board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
					 }
				 }
			 }
		 }
		 if (numFullLines > 0) {
			 numLinesRemoved += numFullLines;
			 isFallingFinished = true;
			 currentPiece.setTetrominoes(Tetrominoe.EmptyPiece);
			 doGameCycle();
		 }
	 }
	 
	 /**
	  * Beallitjuk az alakzatok szineit, majd kirajzoljuk az alakzatot, negyzetek kirajzolasaval
	  * @param g
	  * @param x koordinata
	  * @param y koordinata
	  * @param shape alakzatok enumeracioja
	  */
	 private void drawSquare(Graphics g, int x, int y, Tetrominoe shape) {
		 Color colors[] = {new Color(0, 0, 0), //EmptyPiece
				 new Color(124, 252, 0), //SPiece
				 new Color(255, 0, 0), //ZPiece
				 new Color(186, 85, 211), //TPiece
				 new Color(255, 140, 0), //LPiece
				 new Color(106, 90, 205), //JPiece
				 new Color(64, 224, 208), //IPiece
				 new Color(255, 255, 0) //OPiece
		 };
		 var color = colors[shape.ordinal()];
		 g.setColor(color);
		 g.fillRect(x + 1, y + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
		 g.setColor(color.brighter());
		 g.drawLine(x, y + SQUARE_SIZE - 1, x, y);
		 g.drawLine(x, y, x + SQUARE_SIZE - 1, y);
		 g.setColor(color.darker());
		 g.drawLine(x + 1, y + SQUARE_SIZE - 1,
				 	x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1);
		 g.drawLine(x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1,
	                x + SQUARE_SIZE - 1, y + 1);
	 }

	 /**
	  * Ciklusba rakja a jatekot, frissiti es ujrarajzolja
	  */
	 private void doGameCycle() {
		 update();
		 repaint();
	 } 

	 /**
	  * Frissiti a jatekot, ha befejzodott az alakzat elhelyezese ujat ker, kulonben eggyel lejjebb kerul az aktualis alakzat.
	  */
	 private void update() {
		 if (isFallingFinished) {
			 isFallingFinished = false;
			 newPiece();
		 } 
		 else {
			 oneLineDown();
		 }
		 
	 }

	 
	 /**
	  * Szerializalas fuggvenye, a pontszamot es az aktualis idopontot hozzaadja a listahoz, es kiirja egy fajlba
	  * a listat.
	  */
	public void saveData(String filename) {
		 try {
			 FileOutputStream fileOut = new FileOutputStream(filename);
			 ObjectOutputStream out = new ObjectOutputStream(fileOut);
			 DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	         java.util.Date today = Calendar.getInstance().getTime();        
	         String reportDate = df.format(today);
			 Score s = new Score(reportDate, numLinesRemoved);
			 System.out.println(MainMenu.maratonscores.size());
			 MainMenu.maratonscores.add(s);
			 System.out.println(MainMenu.maratonscores.size());
			 out.writeObject(MainMenu.maratonscores);
			 out.close();
			 fileOut.close();
		 } catch(IOException i) {
			 i.printStackTrace();
		 } 
	 }

	/**
	 * A MyKeyAdapter osztaly segitsegevel a billentyzetet hasznaljuk a jatek iranyitasahoz.
	 * A lenyomott billentyukre meghivjuk a fuggvenyeket amelyek iranyitjak a jatekot
	 */
	class MyKeyAdapter extends KeyAdapter {

		 @Override
		 public void keyPressed(KeyEvent e) {
			 if (currentPiece.getPiece() == Tetrominoe.EmptyPiece) {
				 return;
			 }
			 int keycode = e.getKeyCode();
			 // Java 12 switch expressions
			 switch (keycode) {
			 case KeyEvent.VK_LEFT -> tryMove(currentPiece, currentX - 1, currentY);
			 case KeyEvent.VK_RIGHT -> tryMove(currentPiece, currentX + 1, currentY);
			 case KeyEvent.VK_DOWN -> tryMove(currentPiece.RotateRight(), currentX, currentY);
			 case KeyEvent.VK_UP -> tryMove(currentPiece.RotateLeft(), currentX, currentY);
			 case KeyEvent.VK_SPACE -> dropDown();
			 case KeyEvent.VK_D -> oneLineDown();
			 }
		 }
	 } 
	 
	 
}
