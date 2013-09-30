/*
 * The MainMenuPanel class is a JPanel for the Main Menu screen.
 * @author Lloyd Jones for ITEC-220
 */

import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

	private ImageIcon imageLoad;
	private Image backgroundImage;
	
	//Constructor that sets the background image.
	public MainMenuPanel(){
		
		imageLoad = new ImageIcon("Images/PegMainScreen.png");
		backgroundImage = imageLoad.getImage();
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
	
	
}
