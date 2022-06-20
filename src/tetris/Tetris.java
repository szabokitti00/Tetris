package tetris;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.EventQueue;

public class Tetris extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static JLabel statusbar;

	/**
	 * Tetris konstrutora
	 */
    public Tetris() {

        initUI();
    }
    
    /**
	 * Inicializalja a jatek ablakot
	 */
    private void initUI() {

        statusbar = new JLabel(" ");
        add(statusbar, BorderLayout.SOUTH);

        var board = new Board(this);
        add(board);
        board.start();

        setTitle("Tetris");
        setSize(450, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    /**
	 * Getter
	 * @return Visszaadja a a tetris osztay statusbar erteket
	 */
    JLabel getStatusBar() {

        return statusbar;
    }
    
    /**
     * Setter
     * @param text hatralevo masodpercek szama string-kent
     */
    public static void setStatusBar(String text) {
    	statusbar.setText(text);
    }

    /**
	 * Elinditja a jatekot, amelyet egy uj ablakban nyit meg
	 */
    public static void startinggame() {

        EventQueue.invokeLater(() -> {

            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }
}
