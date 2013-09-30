/*
 * The Peg class contains attributes for each Peg on the board, such as whether the Peg is active or just an empty hole and whether
 * it is currently selected by the player. Each Peg is a JLabel with an image of a random colored peg.
 * @author Lloyd Jones for ITEC-220
 */

import java.awt.*;
import javax.swing.*;


public class Peg extends JLabel{
	
	//Instance variables
	private String color;
	private boolean active;
	private ImageIcon pegImage;
	private ImageIcon selectedImage;
	private int row;
	private int position;
	private boolean selected;
	
	//Constructor, by default it sets the peg to be active and not selected, then sets the peg color.
	public Peg(){
		
		active = true;
		selected = false;
		setPegColor();
		
	}

	/*
	 * Sets the peg's JLabel image to red, blue, white, or yellow (traditional Peg Game colors) using a random number generator.
	 * Uses a switch statement and finds the appropriate image to load under the Images folder.
	 */
public void setPegColor(){
		
		int i = (int)(Math.random()*8+1);
		switch(i){
		case 1: case 2: color = "red"; break;
		case 3: case 4: color = "blue"; break;
		case 5: case 6: color = "white"; break;
		case 7: case 8: color = "yellow";
		}
		
		pegImage = new ImageIcon("Images/" + color + "peg.png");
		setIcon(pegImage);
		selectedImage = new ImageIcon("Images/" + color + "pegselect.png");
		
		}

/*
 * Setter method for row.
 * @param an int for the row number (0-4)
 */
	public void setRow(int x){
		
		row = x;
	}
	
	/*
	 * Setter method for position.
	 * @param an int for the row number (0-4)
	 */
	public void setPosition(int x){
		
		position = x;
	}
	/*
	 * Getter method for row.
	 * @return row as an int.
	 */
	public int getRow(){
		
		return row;
	}
	
	/*
	 * Getter method for position.
	 * @return position as an int.
	 */
	public int getPosition(){
		
		return position;
	}
	
	/*
	 * Method to "select" a Peg by setting its JLabel image to a green highlighted version of the image.
	 * Also sets the Peg's selected attribute to true.
	 */
	public void select(){
		
			setIcon(selectedImage);
			selected = true;

	}
	
	/*
	 * Method to "deselect" a Peg by setting its JLabel image to what it originally was. Also sets the Peg's selected
	 * attribute to false
	 */
	public void deselect(){
		
		setIcon(pegImage);
		selected = false;

	}
	
	/*
	 * Getter method for whether a peg is selected.
	 * @return true if it is selected, false otherwise.
	 */
	public boolean getSelected(){
		
		return selected;
	}

	/*
	 * Removes the Peg from the game board by setting active to false and its image to that of an emptyHole.
	 */
	public void removePeg(){
		
		active = false;
		ImageIcon newImage = new ImageIcon("Images/emptyhole.png");
		setIcon(newImage);
		
	}
	
	/*
	 * Setter method for active.
	 * @param boolean, true to activate or false to deactivate.
	 */
	public void setActive(boolean y){
		
		active = y;
	}

	/*
	 * Getter method for active.
	 * @return active as a boolean.
	 */
	public boolean isActive(){
		
		return active;
	}
	
	/*
	 * Sets the image that shows when the Peg is "selected" by the user.
	 * @param the new image as an ImageIcon
	 */
	public void setSelectedImage(ImageIcon x){
		
		selectedImage = x;
	}
	
	/*
	 * Getter method for selectedImage
	 * @return the selected image as an ImageIcon.
	 */
	public ImageIcon getSelectedImage(){
		
		return selectedImage;
	}
	
	/*
	 * Getter method for the original pegImage of the Peg.
	 * @return the pegImage as an ImageIcon.
	 */
	public ImageIcon getPegImage(){
		
		return pegImage;
	}
	
	/*
	 * Setter method for pegImage.
	 * @param the new image as an ImageIcon.
	 */
	public void setPegImage(ImageIcon x){
		

		pegImage = x;
	}
	
		
	}



