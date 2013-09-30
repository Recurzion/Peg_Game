/*
 * The RulesPanel class is a JPanel that uses an image as the background to show the player how to play the game.
 * @author Lloyd Jones for ITEC-220
 */

import java.awt.*;
import javax.swing.*;


public class RulesPanel extends JPanel {
	
	private ImageIcon imageLoad;
	private Image backgroundImage;
	
	//RulesPanel Constructor, sets the background image.
	public RulesPanel(){
		
		imageLoad = new ImageIcon("Images/rules.png");
		backgroundImage = imageLoad.getImage();
		setLayout(null);	

		
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
