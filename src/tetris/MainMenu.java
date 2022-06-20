package tetris;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainMenu extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private static JFrame window;
	public Container cont;
	
	private static JPanel TitlePanel;
	private static JPanel ButtonPanel;
	
	private static JPanel GameOptionTitlePanel;
	private static JPanel GameOptionButtonPanel;
	
	private static JPanel LeaderboardOptionTitlePanel;
	private static JPanel LeaderboardOptionButtonPanel;
	
	private static JPanel HowToPlayTitlePanel;
	private static JPanel HowToPlayTextPanel;
	private static JPanel HowToPlayButtonPanel;
	
	private static JPanel LeaderboardListPanel;
	private static JPanel LeaderboardButtonPanel;
	
	Font titleFont = new Font("Arial", Font.BOLD, 90);
	Font subtitleFont = new Font("Times New Roman", Font.PLAIN, 20);
	Font buttonfont = new Font("Times New Roman", Font.PLAIN, 16);
	
	private JButton Button1 = new JButton("Játék indítása");
	private JButton Button2 = new JButton("Ranglista");
	private JButton Button3 = new JButton("Hogyan kell játszani?");
	private JButton Button4 = new JButton("Kilépés");
	
	private String quickplayleaderboard = "quickplay.txt";
	private String maratonleaderboard = "maraton.txt";
	
	public ArrayList<Score> quickplayscores;
	public static ArrayList<Score> maratonscores;
	
	private QuickGame play;
	
	public static void main(String [] args) {
		MainMenu screen = new MainMenu();
		screen.setVisible(true);
	}
	/**
	 * MainMneu konstruktora
	 * Hozzaad ket Jpanel-t, egy a Tetris feliratot  rajzolja ki, a masik a negy gombot 
	 */
	MainMenu () {
		window = new JFrame(); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Tetris!");
		setSize(450, 550);
		getContentPane().setBackground(Color.black);
		setLayout(null);
		cont =  window.getContentPane();
		
		TitlePanel = new JPanel();
		TitlePanel.setBounds(50,100,340,100);
		
		TitlePanel.setBackground(Color.black);
		JLabel TitlePanelLabel = new JLabel("TETRIS");
		TitlePanelLabel.setForeground(Color.white);
		TitlePanelLabel.setFont(titleFont);	
		
		TitlePanel.add(TitlePanelLabel);
		
		ButtonPanel = new JPanel();
		ButtonPanel.setBounds(125,250,200,220);
		ButtonPanel.setBackground(Color.black);
		
		Button1.addActionListener(new PlayButtonActionListener());
		Button1.setBackground(Color.darkGray);
		Button1.setForeground(Color.white);
		Button1.setFont(buttonfont);
		Button2.addActionListener(new LeaderboardButtonActionListener());
		Button2.setBackground(Color.darkGray);
		Button2.setForeground(Color.white);
		Button2.setFont(buttonfont);
		Button3.addActionListener(new HowToPlayButtonActionListener());
		Button3.setBackground(Color.darkGray);
		Button3.setForeground(Color.white);
		Button3.setFont(buttonfont);
		Button4.addActionListener(new ExitButtonActionListener() );
		Button4.setBackground(Color.darkGray);
		Button4.setForeground(Color.white);
		Button4.setFont(buttonfont);
        
		ButtonPanel.add(Button1);
        ButtonPanel.add(Button2);
        ButtonPanel.add(Button3);
        ButtonPanel.add(Button4);
        
		add(TitlePanel);
		add(ButtonPanel);
		loadScores(maratonleaderboard);
		System.out.println(maratonscores.size());
	}
	
	/**
	 * A Jatek inditasa gombhoz tartozo handler-t kesziti el
	 */
	final class PlayButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			TitlePanel.setVisible(false);
			ButtonPanel.setVisible(false);
			CreateGameMenu();
		}	
	}
	
	/**
	 * A Ranglista gombhoz tartozo handler-t kesziti el
	 */
	final class LeaderboardButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			TitlePanel.setVisible(false);
			ButtonPanel.setVisible(false);
			CreateLeaderboardMenu();
		}	
	}
	
	/**
	 * A Hogyan kell jatszani gombhoz tartozo handler-t kesziti el
	 */
	final class HowToPlayButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			TitlePanel.setVisible(false);
			ButtonPanel.setVisible(false);
			CreateHowToPlayMenu();
		}	
	}
	
	/**
	 * A Kilespes gombhoz tartozo handler-t kesziti el
	 */
	final class ExitButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
	
	/**
	 * Kirajzolja a jatek mod kivalasztasahoz tartozo kepernyot
	 * Hozzaad ket Jpanel-t, egy a szoveget rajzolja ki, a masik a harom gombot 
	 */
	public void CreateGameMenu() {
		getContentPane().setBackground(Color.black);
		setLayout(null);
		cont =  window.getContentPane();
		
		GameOptionTitlePanel = new JPanel();
		GameOptionTitlePanel.setBounds(50,100,340,100);
		
		GameOptionTitlePanel.setBackground(Color.black);
		JLabel SubTitlePanelLabel = new JLabel("Válasszon egy lehetõséget!");
		SubTitlePanelLabel.setForeground(Color.white);
		SubTitlePanelLabel.setFont(subtitleFont);		
		GameOptionTitlePanel.add(SubTitlePanelLabel);
		
		GameOptionButtonPanel = new JPanel();
		GameOptionButtonPanel.setBounds(125,250,200,220);
		       
		GameOptionButtonPanel.setBackground(Color.black);
		JButton button1 = new JButton("Gyors Játék");
		button1.addActionListener(new QuickGameActionListener());
		button1.setBackground(Color.darkGray);
		button1.setForeground(Color.white);
		button1.setFont(buttonfont);
		JButton button2 = new JButton("Maraton");
		button2.addActionListener(new MaratonGameActionListener());
		button2.setBackground(Color.darkGray);
		button2.setForeground(Color.white);
		button2.setFont(buttonfont);
		JButton button3 = new JButton("Vissza a menübe");
		button3.addActionListener(new BackToMenuGameActionListener());
		button3.setBackground(Color.darkGray);
		button3.setForeground(Color.white);		
		button3.setFont(buttonfont);
		
        GameOptionButtonPanel.add(button1);
        GameOptionButtonPanel.add(button2);
        GameOptionButtonPanel.add(button3);
		
		add(GameOptionTitlePanel);
		add(GameOptionButtonPanel);
		setVisible(true);
	}
	
	/**
	 * A Gyors jatek gombhoz tartozo jatek elindito handler-t kesziti el
	 */
	final class QuickGameActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			play = new QuickGame();
		}
	}
	
	/**
	 * A Maraton gombhoz tartozo jatek elindito handler-t kesziti el
	 */
	final class MaratonGameActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Tetris.startinggame();
		}
	}
	
	/**
	 * A Vissza a menube gombhoz tartozo handler-t kesziti el
	 */
	final class BackToMenuGameActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GameOptionTitlePanel.setVisible(false);
			GameOptionButtonPanel.setVisible(false);
			MainMenu.TitlePanel.setVisible(true);
			MainMenu.ButtonPanel.setVisible(true);
		}
	}
	
	/**
	 * Kirajzolja a ranglista kivalasztasahoz tartozo kepernyot
	 * Hozzaad ket Jpanel-t, egy a szoveget rajzolja ki, a masik a harom gombot 
	 */
	public void CreateLeaderboardMenu() {
		getContentPane().setBackground(Color.black);
		setLayout(null);
		cont =  window.getContentPane();
		
		LeaderboardOptionTitlePanel = new JPanel();
		LeaderboardOptionTitlePanel.setBounds(50,100,340,100);
		
		LeaderboardOptionTitlePanel.setBackground(Color.black);
		JLabel SubTitlePanelLabel = new JLabel("Válasszon egy lehetõséget!");
		SubTitlePanelLabel.setForeground(Color.white);
		SubTitlePanelLabel.setFont(subtitleFont);		
		LeaderboardOptionTitlePanel.add(SubTitlePanelLabel);
		
		LeaderboardOptionButtonPanel = new JPanel();
		LeaderboardOptionButtonPanel.setBounds(125,250,200,220);
		
		LeaderboardOptionButtonPanel.setBackground(Color.black);
		JButton button1 = new JButton("Gyors Játék");
		button1.addActionListener(new QuickGameLeaderboardActionListener());
		button1.setBackground(Color.darkGray);
		button1.setForeground(Color.white);
		button1.setFont(buttonfont);
		JButton button2 = new JButton("Maraton");
		button2.addActionListener(new MaratonGameLeaderboardActionListener());
		button2.setBackground(Color.darkGray);
		button2.setForeground(Color.white);
		button2.setFont(buttonfont);
		JButton button3 = new JButton("Vissza a menübe");
		button3.addActionListener(new BackToMenuLeaderboardActionListener());
		button3.setBackground(Color.darkGray);
		button3.setForeground(Color.white);		
		button3.setFont(buttonfont);
		
		LeaderboardOptionButtonPanel.add(button1);
		LeaderboardOptionButtonPanel.add(button2);
		LeaderboardOptionButtonPanel.add(button3);
		
		add(LeaderboardOptionTitlePanel);
		add(LeaderboardOptionButtonPanel);
	}
	
	/**
	 * A Gyors jatek gombhoz tartozo ranglista megjelenito handler-t kesziti el
	 */
	final class QuickGameLeaderboardActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			loadScores(quickplayleaderboard);
			LeaderboardOptionTitlePanel.setVisible(false);
			LeaderboardOptionButtonPanel.setVisible(false);
			createLeaderboard(quickplayscores);
		}
	}
	
	/**
	 * A Maraton gombhoz tartozo ranglista megjelenito handler-t kesziti el
	 */
	final class MaratonGameLeaderboardActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			loadScores(maratonleaderboard);
			LeaderboardOptionTitlePanel.setVisible(false);
			LeaderboardOptionButtonPanel.setVisible(false);
			createLeaderboard(maratonscores);
			
		}
	}
	
	/**
	 * A Vissza a menube gombhoz tartozo handler-t kesziti el
	 */
	final class BackToMenuLeaderboardActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			LeaderboardOptionTitlePanel.setVisible(false);
			LeaderboardOptionButtonPanel.setVisible(false);
			MainMenu.TitlePanel.setVisible(true);
			MainMenu.ButtonPanel.setVisible(true);
		}
	}
	
	/**
	 * Kirajzolja a felhasznaloi utasitasokat tartozo kepernyot
	 * Hozzaad harom Jpanel-t, elso a cimet, masodik a szoveget, harmadik a gombot rajzolja ki 
	 */
	public void CreateHowToPlayMenu() {
		getContentPane().setBackground(Color.black);
		setLayout(null);
		cont =  window.getContentPane();
		
		HowToPlayTitlePanel = new JPanel();
		HowToPlayTitlePanel.setBounds(50,30,340,30);
		
		HowToPlayTitlePanel.setBackground(Color.black);
		JLabel SubTitlePanelLabel = new JLabel("Hogyan kell játszani?");
		SubTitlePanelLabel.setForeground(Color.white);
		SubTitlePanelLabel.setFont(subtitleFont);		
		HowToPlayTitlePanel.add(SubTitlePanelLabel);
		
		HowToPlayTextPanel = new JPanel();
		HowToPlayTextPanel.setBounds(25,70,400,350);
		
		HowToPlayTextPanel.setBackground(Color.black);
		text.setBackground(Color.black);
		text.setForeground(Color.white);
		text.setFont(buttonfont);	
		text.setEditable(true);
		HowToPlayTextPanel.add(text);
		
		HowToPlayButtonPanel = new JPanel();
		HowToPlayButtonPanel.setBounds(125,430,200,40);
		       
		HowToPlayButtonPanel.setBackground(Color.black);
		JButton button = new JButton("Vissza a menübe");
		button.addActionListener(new BackToMenuHToPActionListener());
		button.setBackground(Color.darkGray);
		button.setForeground(Color.white);
		button.setFont(buttonfont);
		
		HowToPlayButtonPanel.add(button);
		
		add(HowToPlayTitlePanel);
		add(HowToPlayTextPanel);
		add(HowToPlayButtonPanel);
	}
	
	/**
	 * A Vissza a menube gombhoz tartozo handler-t kesziti el
	 */
	final class BackToMenuHToPActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			HowToPlayTitlePanel.setVisible(false);
			HowToPlayTextPanel.setVisible(false);
			HowToPlayButtonPanel.setVisible(false);
			MainMenu.TitlePanel.setVisible(true);
			MainMenu.ButtonPanel.setVisible(true);
		}
	}
	
	/**
	 * Szerializacio : beolvassa a fajlbol az eredmenyeket tartalmazo listat.
	 */
	@SuppressWarnings("unchecked")
	public void loadScores(String file) {
		ObjectInputStream objectinputstream = null;
		try {
		    FileInputStream streamIn = new FileInputStream(file);
		    objectinputstream  = new ObjectInputStream(streamIn);
		    maratonscores = (ArrayList<Score>) objectinputstream.readObject();
		    Collections.sort(maratonscores, Score.getCompByPoints());
		} catch (Exception e) {
		    e.printStackTrace();
		} 
	}
	
	/**
	 * Kirajzolja a megfelelo jatekmodhoz tartozo ranglistat
	 * @param list atadja azt a listat amelyikbe a jatek modhoz tartozo pontok lettek elmentve
	 */
	public void createLeaderboard(ArrayList<Score> list) {
		getContentPane().setBackground(Color.black);
		setLayout(null);
		cont =  window.getContentPane();
		
		LeaderboardListPanel = new JPanel();
		LeaderboardListPanel.setBounds(50,20,340,400);
		
		LeaderboardListPanel.setBackground(Color.black);
		LeaderboardListPanel.setForeground(Color.white);
		
		if(list != null) {
			for(int i = 0; i < list.size(); i++) {
				String index = Integer.toString(i+1);
				String date = list.get(i).getDate();
				String points = Integer.toString(list.get(i).getPoints());
				
				JTextArea current = new JTextArea(index + "   " + date + "   " + points + "\n");
				LeaderboardListPanel.add(current);
			}
		}
		else {
			JTextArea empty = new JTextArea("A ranglista üres!\n");
			empty.setBackground(Color.black);
			empty.setForeground(Color.white);
			empty.setFont(subtitleFont);
			LeaderboardListPanel.add(empty);

		}
				
		LeaderboardButtonPanel = new JPanel();
		LeaderboardButtonPanel.setBounds(125,430,200,40);
		       
		LeaderboardButtonPanel.setBackground(Color.black);
		
		JButton button = new JButton("Vissza a menübe");
		button.addActionListener(new BackToMenuTableActionListener());
		button.setBackground(Color.darkGray);
		button.setForeground(Color.white);		
		button.setFont(buttonfont);
		
        LeaderboardButtonPanel.add(button);
        
        add(LeaderboardListPanel);
		add(LeaderboardButtonPanel);
		
		setVisible(true);
		
	}
	
	/**
	 * A Vissza a menube gombhoz tartozo handler-t kesziti el
	 */
	final class BackToMenuTableActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			LeaderboardListPanel.setVisible(false);
			LeaderboardButtonPanel.setVisible(false);
			MainMenu.TitlePanel.setVisible(true);
			MainMenu.ButtonPanel.setVisible(true);
		}
	}
	
	/**
	 * A felhasznaloi utasitahoz kesziti el a szoveget
	 */
	JTextArea text = new JTextArea("A tetris játékot a játékos a billentyûzet bizonyos gombjaival\n"
									+"játszhatja, és célja minál több sor megtöltése és ezáltali törlése.\n" 
									+"Használható billentyûk:\n"
									+">FEL és LE: az alakzatokat forgatják jobbra és balra;\n"
									+">BAL és JOBB: az alakzatot forgatják jobbra és balra;\n"
									+">D: az alakzatot egy sorral lejjebb helyezi a táblán;\n"
									+">SPACE: az alakzatot a tábla aljára helyezi a táblán;\n");
}