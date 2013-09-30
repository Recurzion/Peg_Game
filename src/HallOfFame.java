/*
 * The HallOfFame class is a JPanel that shows the Wall of Fame background and imports the list of players who left only one peg
 * (stored in halloffame.txt).
 * @author Lloyd Jones for ITEC-220
 */

import java.awt.*;
import java.io.*;
import javax.swing.*;


public class HallOfFame extends JPanel {
	
	//Instance variables
	private ImageIcon imageLoad;
	private Image backgroundImage;
	
	//Constructor that sets the background to the correct image and adds the player names.
	public HallOfFame() throws IOException{
		
		imageLoad = new ImageIcon("Images/hall2.png");
		backgroundImage = imageLoad.getImage();
		setLayout(null);
		addPlayers();
		repaint();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics gc){
		
        gc.drawImage(backgroundImage, 0, 0, null);
		setOpaque(false);
		super.paintComponent(gc);
		setOpaque(true);

		
	}
	/*
	 * Adds up to ten player names from halloffame.txt to the display in two rows of five names. Sets up spacing and font also and uses a
	 * buffered reader to read in those names.
	 */
	public void addPlayers() throws IOException{
		
		int vertical = 60;
		int vertical2 = 60;
		int horizontal = 30;
		int horizontal2 = 290;
		int verticalSpacing = 50;
		JLabel[] playerNames = new JLabel[10];
		FileInputStream in = new FileInputStream("halloffame.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readString = "";

			
			for (int i = 0; i < 5; i ++){
				if ((readString = br.readLine()) != null) {
				playerNames[i] = new JLabel(readString);
				playerNames[i].setFont(new Font("Fipps-Regular", 1, 22));
				playerNames[i].setForeground(Color.black);
				playerNames[i].setBounds(horizontal, vertical, 400, 200);
				vertical = vertical + verticalSpacing;
				this.add(playerNames[i]);
				}
				
				
			}
			
			for (int i = 0; i < 5; i ++){
				if ((readString = br.readLine()) != null) {
				playerNames[i] = new JLabel(readString);
				playerNames[i].setFont(new Font("Fipps-Regular", 1, 22));
				playerNames[i].setForeground(Color.black);
				playerNames[i].setBounds(horizontal2, vertical2, 400, 200);
				vertical2 = vertical2 + verticalSpacing;
				this.add(playerNames[i]);
				}
				
				
			}
			
		
		}
	}


