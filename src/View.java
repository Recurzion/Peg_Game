/*
 * View class
 * This class starts the GUI which includes a JFrame and JButtons and their corresponding ActionListeners.
 * The first JPanel shown on the GUI is the MainMenu, which includes options to play the game, view the rules, and view the Hall of Fame.
 * @author Lloyd Jones for ITEC-220
 */
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class View extends JFrame {
	
	//Create instance variables
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private MainMenuPanel menuPanel;
	private RulesPanel rules;
	private GamePanel game;
	private HallOfFame hall;
	private String name;
	private Clip clickNoise, winNoise, loseNoise;

	//View constructor, calls multiple methods to set up Main Menu.
	public View(){
		
		setUpWindow();
		addRulesButton();
		addPlayButton();
		addHallButton();
		addSound();
		
	}
	/*
	 * Sets the layout, size, and visibility and adds the MainMenuPanel to the JFrame. 
	 */
	public void setUpWindow(){
		
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		getContentPane().setLayout(new CardLayout(0, 0));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Peg Game");
		
		menuPanel = new MainMenuPanel();
		getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		setVisible(true);
	}
	/*
	 * Creates a new JButton, loads the image for it, and creates an ActionListener for the Rules button.
	 */
	public void addRulesButton(){
		
		ImageIcon rulesButtonLoader = new ImageIcon("Images/rulesbutton.png");
		ImageIcon rulesButtonLoader2 = new ImageIcon("Images/rulesbutton2.png");
		JButton rulesButton = new JButton("");
		rulesButton.setBounds(177, 300, 150, 35);
		rulesButton.setIcon(new ImageIcon(rulesButtonLoader.getImage()));
		rulesButton.setBorder(null);
		rulesButton.setRolloverIcon(new ImageIcon(rulesButtonLoader2.getImage()));
		rulesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				playClick();
				addRulesListener();
				
			}
		});
		menuPanel.add(rulesButton);
	}
	/*
	 * Creates a new JButton, loads the image for it, and creates an ActionListener for the Play button.
	 */
	public void addPlayButton(){
		
		ImageIcon playButtonLoader = new ImageIcon("Images/playbutton.png");
		ImageIcon playButtonLoader2 = new ImageIcon("Images/playbutton2.png");
		JButton playButton = new JButton("");
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				playClick();
				addPlayListener();
				
			}
		});
		playButton.setBounds(177, 250, 150, 35);
		playButton.setIcon(new ImageIcon(playButtonLoader.getImage()));
		playButton.setBorder(null);
		playButton.setRolloverIcon(new ImageIcon(playButtonLoader2.getImage()));
		menuPanel.add(playButton);
		
		menuPanel.repaint();
	}
	
	/*
	 * Creates a new JButton, loads the image for it, and creates an ActionListener for the Hall of Fame button.
	 */
	public void addHallButton(){
		
		ImageIcon hallButtonLoader = new ImageIcon("Images/hallbutton.png");
		ImageIcon hallButtonLoader2 = new ImageIcon("Images/hallbutton2.png");
		JButton hallButton = new JButton("");
		hallButton.setBounds(127, 350, 250, 35);
		hallButton.setIcon(new ImageIcon(hallButtonLoader.getImage()));
		hallButton.setBorder(null);
		hallButton.setRolloverIcon(new ImageIcon(hallButtonLoader2.getImage()));
		hallButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				playClick();
				try {
					addHallListener();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		menuPanel.add(hallButton);
		menuPanel.repaint();
		
	}
/*
 * Creates a new JButton for the Give Up button when the game is loaded (GamePanel is the active JPanel). This allows us to
 * repaint the view after the Give Up button is clicked to go back to the Main Menu.
 */
	public void addGiveUp(){
		
		ImageIcon giveUpButtonLoader = new ImageIcon("Images/giveup.png");
		ImageIcon giveUpButtonLoader2 = new ImageIcon("Images/giveup2.png");
		JButton giveUpButton = new JButton("");
		giveUpButton.setRolloverIcon(new ImageIcon(giveUpButtonLoader2.getImage()));
		giveUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				playClick();
				try {
					addGiveUpListener();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		giveUpButton.setBounds(177, 400, 150, 35);
		giveUpButton.setIcon(new ImageIcon(giveUpButtonLoader.getImage()));
		giveUpButton.setBorder(null);
		game.add(giveUpButton);
	}
	
	/*
	 * Creates a new JButton, loads the image for it, and creates an ActionListener for the Rules button.
	 */
	public void addRulesListener(){
		
		remove(menuPanel);
		rules = new RulesPanel();
		add(rules);
		ImageIcon backButtonLoader = new ImageIcon("Images/back.png");
		ImageIcon backButtonLoader2 = new ImageIcon("Images/back2.png");
		JButton backButton = new JButton("");
		backButton.setBounds(300, 392, 150, 35);
		backButton.setIcon(new ImageIcon(backButtonLoader.getImage()));
		backButton.setBorder(null);
		backButton.setRolloverIcon(new ImageIcon(backButtonLoader2.getImage()));
		rules.add(backButton);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				playClick();
				remove(rules);
				add(menuPanel);
				menuPanel.setVisible(true);
				revalidate();
				repaint();
				
			}
		});

		rules.setVisible(true);
		revalidate();
		repaint();
	}
	
	/*
	 * Contains the code for the MouseClicked event on the Action Listener for the Play Button.
	 */
	public void addPlayListener(){
		
		remove(menuPanel);
		game = new GamePanel();
		add(game);
		addGiveUp();
		game.setVisible(true);
		revalidate();
		repaint();
	}
	
	/*
	 * Contains the code for the MouseClicked event on the Action Listener for the Give Up Button. This method checks to see how many pegs
	 * the player left on the board and uses a switch statement to generate the appropriate response. The method then removes the GamePanel
	 * and adds the MenuPanel to the JFrame.
	 */
	public void addGiveUpListener() throws IOException{
		
		switch (game.getPegCount()){
		case 1:	JOptionPane.showMessageDialog(null, "You left one peg! You're a genius!"); nameEntry(); break;
		case 2:  playWin(); JOptionPane.showMessageDialog(null, "You left two pegs! You're purdy smart!"); break;		
		case 3: playLose(); JOptionPane.showMessageDialog(null, "You left three pegs! You're just plain dumb."); break;
		default: playLose(); JOptionPane.showMessageDialog(null, "You're just a plain ol' EG-NO-RA-MOOSE."); break;
		
		}
		
		remove(game);
		add(menuPanel);
		menuPanel.setVisible(true);
		revalidate();
		repaint();
	}
	
	/*
	 * Contains the code for the MouseClicked event on the Action Listener for the Hall of Fame Button and also adds the ActionListener
	 * for the back button that is shown in the Hall of Fame.
	 */
	public void addHallListener() throws IOException{
		
		remove(menuPanel);
		hall = new HallOfFame();
		add(hall);
		ImageIcon backButtonLoader = new ImageIcon("Images/back.png");
		ImageIcon backButtonLoader2 = new ImageIcon("Images/back2.png");
		JButton backButton = new JButton("");
		backButton.setBounds(300, 425, 150, 35);
		backButton.setIcon(new ImageIcon(backButtonLoader.getImage()));
		backButton.setBorder(null);
		backButton.setRolloverIcon(new ImageIcon(backButtonLoader2.getImage()));
		hall.add(backButton);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				playClick();
				remove(hall);
				add(menuPanel);
				menuPanel.setVisible(true);
				revalidate();
				repaint();
				
			}
		});
		
		hall.setVisible(true);
		revalidate();
		repaint();
		
	}
	
	/*
	 * Method to prompt the user to enter their name for the Hall of Fame if they leave only one peg on the board. This method opens the
	 * halloffame.txt files and uses a BufferedWriter the append the player's name to the text file.
	 */
	public void nameEntry() throws IOException{
		boolean valid = false;
		name = JOptionPane.showInputDialog("Enter your name for the Hall of Fame:");
		 while (name.length() == 0 || name.length() > 10){
				 
			name = JOptionPane.showInputDialog("Invalid name. Enter your name for the Hall of Fame:");
		
		 }
		 
		 FileWriter fstream = new FileWriter("halloffame.txt", true);
		 BufferedWriter out = new BufferedWriter(fstream);
		 out.write(name);
		 out.newLine();
		 out.close();
}
	/*
	 * Imports the .wav files needed to play the click noise when any JButton is clicked.
	 */
	public void addSound(){
		
		AudioInputStream sound;
		File clickFile = new File("Sounds/click.wav");
		File winFile = new File("Sounds/win.wav");
		File loseFile = new File("Sounds/lose.wav");
		try {
			sound = AudioSystem.getAudioInputStream(clickFile);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clickNoise = (Clip) AudioSystem.getLine(info);
			clickNoise.open(sound);
			
			sound = AudioSystem.getAudioInputStream(winFile);
			DataLine.Info info2 = new DataLine.Info(Clip.class, sound.getFormat());
			winNoise = (Clip) AudioSystem.getLine(info2);
			winNoise.open(sound);
			
			sound = AudioSystem.getAudioInputStream(loseFile);
			DataLine.Info info3 = new DataLine.Info(Clip.class, sound.getFormat());
			loseNoise = (Clip) AudioSystem.getLine(info3);
			loseNoise.open(sound);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Plays the click sound by stopping it, rewinding it, then playing it.
	 */
	public void playClick(){
		
		clickNoise.stop();
		clickNoise.setFramePosition(0);
		clickNoise.start();
		
			
	}
	/*
	 * Plays the win noise by stopping it, rewinding it, then playing it.
	 */
	public void playWin(){
		
		winNoise.stop();
		winNoise.setFramePosition(0);
		winNoise.start();
	}
	
	/*
	 * Plays the lose noise by stopping it, rewinding it, then playing it.
	 */
	public void playLose(){
		
		loseNoise.stop();
		loseNoise.setFramePosition(0);
		loseNoise.start();
	}
	
}

		

