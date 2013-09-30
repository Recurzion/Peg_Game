/*
 * The GamePanel class is a JPanel that is "playing field" for the game. It loads a background image for the board and sets up the pegs.
 * It then takes input from the user to select and move pegs are validate moves. It also handles loading sound and setting/getting
 * information about the pegs on the board.
 * @author Lloyd Jones for ITEC-220
 */

import java.awt.*;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener{
	
	//Instance variables
	private int pegCount;
	private ImageIcon imageLoader;
	private Image background;
	private Peg[] pegArray;
	private Peg selectedItem;
	private Peg selectedPeg;
	private Peg selectedHole;
	private Clip clip;
	
	//Constructor that sets the background and calls various methods to set up and start the game.
	public GamePanel(){
		
		imageLoader = new ImageIcon("Images/background2.png");
		background = imageLoader.getImage();
		setLayout(null);
		addPegs();
		removeStartPeg();
		addSound();
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics gc){
		
        gc.drawImage(background, 0, 0, null);
		setOpaque(false);
		super.paintComponent(gc);
		setOpaque(true);
		
	}
	
	
	/*
	 * Method to initialize new Peg objects and add them the to JPanel.
	 */
	public void addPegs(){
		
		
		//Initialize the array of 15 Pegs
		pegArray = new Peg[15];
		for (int i = 0; i < pegArray.length; i++){
			
			pegArray[i] = new Peg();
		}
		//Initialize the amount of pegs on the board
		pegCount = 14;
		
		//Set bounds and positioning for the Pegs to be displayed on screen
				int row2x = 185;
				int row3x = 152;
				int row4x = 115;
				int row5x = 82;
				int hspace = 70;
				
				//Top Row
				pegArray[0].setBounds(217,87,50,50);
				pegArray[0].setRow(0);
				pegArray[0].setPosition(0);
				
				//Second Row
				for (int i = 1; i <= 2; i++){
					pegArray[i].setBounds(row2x, 147,50,50);
					row2x = row2x + hspace;
					pegArray[i].setRow(1);
					pegArray[i].setPosition(i-1);
				}
				
				//Third Row
				for (int i = 3; i <= 5; i++){
					
					pegArray[i].setBounds(row3x, 207, 50, 50);
					row3x = row3x + hspace;
					pegArray[i].setRow(2);
					pegArray[i].setPosition(i-3);
				}
				
				//Fourth Row
				for (int i = 6; i <= 9; i++){
					
					pegArray[i].setBounds(row4x, 267, 50, 50);
					row4x = row4x + hspace;
					pegArray[i].setRow(3);
					pegArray[i].setPosition(i-6);
				}
				
				//Fifth Row
				for (int i = 10; i <=14; i++){
					
					pegArray[i].setBounds(row5x, 327, 50, 50);
					row5x = row5x + hspace;
					pegArray[i].setRow(4);
					pegArray[i].setPosition(i-10);
				}
				
				//Add all the Pegs to the JPanel
				for (int i = 0; i < 15; i++){
					
					add(pegArray[i]);
					pegArray[i].addMouseListener(this);
				}
				
				
	}
	

	/*
	 * Method to remove a random peg for the initial setup of the board per the game rules
	 */
	public void removeStartPeg(){
		
		Random randomNum = new Random();
		int randomPeg = randomNum.nextInt(15);
		pegArray[randomPeg].removePeg();
		
	}

	/*
	 *Overrides the default mouseClicked method to provide selection of Pegs and empty Peg holes.
	 *@param MouseEvent (when the player clicks on an object)  
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		
		//Grab the item clicked on and cast it as a Peg, then set it to selectedItem.
		selectedItem = (Peg)(e.getSource());

		//If selected item is a Peg (not an empty hole), deselect any other selected pegs and select the clicked on peg.
		if (selectedItem.isActive()){
		
			deselectAll();
			
			selectedPeg = selectedItem;
			selectedPeg.select();
			}
				
		//If the selected item is not active (an empty hole), set it to selectedHole.
		else if (!selectedItem.isActive()){
			
			selectedHole = selectedItem;
			
			if (selectedPeg != null){
				
				//Check for a valid move if both pegs are in the same row
				if (selectedPeg.getRow() == selectedHole.getRow()){
					int middle;
					switch (selectedPeg.getPosition() - selectedHole.getPosition())
					{
					case 2: case -2: 
								 
					if (selectedPeg.getPosition() > selectedHole.getPosition()){
						middle = selectedPeg.getPosition() - 1;
					}
					else{
						
						middle = selectedHole.getPosition() - 1;
						
					}
					if (checkMove(selectedPeg.getRow(), middle)){
						
						validMove();
						removeHorizontalPeg(selectedPeg.getRow(), middle);
						selectedPeg = null;
						selectedHole = null;
					}
		
					break;
					
					}
				}
			
				//Else if pegs aren't in the same row, check for a valid move
				else {
					
					switch (selectedPeg.getRow() - selectedHole.getRow()){
					
					case 2: case -2:
					switch(selectedPeg.getPosition() - selectedHole.getPosition()){
					case 2: case -2: case 0: 
						int removeRow = Math.abs((selectedPeg.getRow() + selectedHole.getRow()) / 2);
						int removePosition;
						if (selectedPeg.getPosition() == selectedHole.getPosition()){
							removePosition = selectedPeg.getPosition();
						}
						else {
							removePosition = Math.max(selectedPeg.getPosition(),selectedHole.getPosition()) -1;
						}
						
						if (checkMove(removeRow, removePosition)){
						validMove();
						removeVerticalPeg(removeRow, removePosition);
						selectedPeg = null;
						selectedHole = null;
						}
						break;
						
					}break;

					}			
				}					
			}
		}
}


			
		
	/*
	 * Method to check if there is an active Peg in between the selected Peg and emptyHole
	 * @param row and position of the Peg in between the selected Peg and selected Hole
	 * @return True if move is valid, false otherwise.
	 */
	public boolean checkMove(int row, int position){
		boolean valid = false;
		Peg checkPeg = new Peg();
		for (int x = 0; x < pegArray.length; x++){
			
			if (pegArray[x].getRow() == row && pegArray[x].getPosition() == position){
				checkPeg = pegArray[x];
			}
			
		}
		
		if (checkPeg.isActive()){
			valid = true;
		}
		
		return valid;
		
	}
	
	/*
	 * Method called to complete a valid move. This method is only called if the selected Peg and emptyHole are in the correct
	 * locations. It plays a clicking noise, sets the emptyHole activate and swaps the image of it from the peg that jumed into
	 * it. It then selects the new peg location and changes the Peg's original location into an emptyHole and decrements pegCount;
	 */
	public void validMove(){
		
		playNoise();
		selectedHole.setActive(true);
		selectedHole.setPegImage(selectedPeg.getPegImage());
		selectedHole.setSelectedImage(selectedPeg.getSelectedImage());
		selectedHole.select();
		selectedPeg.removePeg();
		pegCount--;
	}
	
	/*
	 * After a valid move is complete and if the peg and hole are in the same row, this method is called.
	 * Essentially it searches our pegArray for the Peg that matches the row and column of the peg we want to
	 * remove and removes it from the board.
	 * @param int for the row and column of the peg we want to remove.
	 */
	public void removeHorizontalPeg(int row, int position){
		
		Peg removePeg = new Peg();
		for (int x = 0; x < pegArray.length; x++){
			
			if (pegArray[x].getRow() == row && pegArray[x].getPosition() == position){
				removePeg = pegArray[x];
			}
			
		}
		
		removePeg.removePeg();
		
	}
	
	/*
	 * After a valid move is complete and if the peg and hole are in different rows, this method is called.
	 * Essentially it searches our pegArray for the Peg that matches the row and column of the peg we want to
	 * remove and removes it from the board.
	 * @param int for the row and column of the peg we want to remove.
	 */
	public void removeVerticalPeg(int row, int position){
		
		Peg removePeg = new Peg();
		
			
			for (int x = 0; x < pegArray.length; x++){
				
				if (pegArray[x].getRow() == row && pegArray[x].getPosition() == position){
					removePeg = pegArray[x];
				}	
			}	
	
		
		removePeg.removePeg();
		
	}
	
	/*
	 * Method to "deselect" all pegs on the board. This removes the green highlight around every peg.
	 */
	public void deselectAll(){
		
		for (int i = 0; i < 15; i++){
			
			if (pegArray[i].isActive()){
			pegArray[i].deselect();
		}
			
	}
	
}
	/*
	 * Getter method for pegCount.
	 * @return pegCount as an int.
	 */
	public int getPegCount(){
		
		return pegCount;
	}
	
	/*
	 * Method to add the "peg move" sound to the game. Finds pegnoise.wav and imports it.
	 */
	public void addSound(){
		
		AudioInputStream sound;
		File soundFile = new File("Sounds/pegnoise.wav");
		try {
			sound = AudioSystem.getAudioInputStream(soundFile);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		}
	
	/*
	 * Method to play the pegnoise sound. Stops it if it's currently playing, rewinds it, then plays it.
	 */
	public void playNoise(){
		
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	
	/*
	 * Overridden Mouse methods that aren't used but must be implemented.
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
